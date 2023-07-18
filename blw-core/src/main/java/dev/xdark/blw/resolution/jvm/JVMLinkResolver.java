package dev.xdark.blw.resolution.jvm;

import dev.xdark.blw.classfile.Accessible;
import dev.xdark.blw.resolution.ClassInfo;
import dev.xdark.blw.resolution.LinkResolver;
import dev.xdark.blw.resolution.ResolutionError;
import dev.xdark.blw.resolution.ResolutionResult;
import dev.xdark.blw.resolution.ResolutionSuccess;
import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.MethodType;
import dev.xdark.blw.type.Type;
import dev.xdark.blw.util.arena.Arena;
import dev.xdark.blw.util.arena.ArenaAllocator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Modifier;

@SuppressWarnings("unchecked")
public final class JVMLinkResolver<M extends Accessible, F extends Accessible> implements LinkResolver<M, F> {
	private final ArenaAllocator<ClassInfo<M, F>> classArenaAllocator;

	public JVMLinkResolver(ArenaAllocator<?> classArenaAllocator) {
		//noinspection unchecked
		this.classArenaAllocator = (ArenaAllocator<ClassInfo<M, F>>) classArenaAllocator;
	}

	@Override
	public ResolutionResult<M> resolveVirtualMethod(ClassInfo<M, F> info, String name, MethodType type) {
		if (Modifier.isInterface(info.accessFlags())) {
			return ResolutionError.ACC_INTERFACE_SET;
		}
		var method = uncachedLookupMethod(info, name, type);
		if (method == null) {
			method = uncachedInterfaceMethod(info, name, type);
		}
		return checkVirtualMethod(method);
	}

	@Override
	public ResolutionResult<M> resolveStaticMethod(ClassInfo<M, F> info, String name, MethodType type, boolean itf) {
		ResolutionSuccess<M> method;
		if (itf) {
			if (!Modifier.isInterface(info.accessFlags())) {
				return ResolutionError.ACC_INTERFACE_UNSET;
			}
			method = uncachedInterfaceMethod(info, name, type);
		} else {
			method = uncachedLookupMethod(info, name, type);
		}
		if (method != null) {
			if (!Modifier.isStatic(method.member().accessFlags())) {
				return ResolutionError.ACC_STATIC_UNSET;
			}
			if (itf != Modifier.isInterface(method.owner().accessFlags())) {
				return ResolutionError.ACC_INTERFACE_UNSET;
			}
			return method;
		}
		return ResolutionError.NO_SUCH_METHOD;
	}

	@Override
	public ResolutionResult<M> resolveSpecialMethod(ClassInfo<M, F> info, String name, MethodType type, boolean itf) {
		if (Modifier.isInterface(info.accessFlags()) != itf) {
			return itf ? ResolutionError.ACC_INTERFACE_UNSET : ResolutionError.ACC_INTERFACE_SET;
		}
		var method = itf ? uncachedInterfaceMethod(info, name, type) : uncachedLookupMethod(info, name, type);
		return checkVirtualMethod(method);
	}

	@Override
	public ResolutionResult<M> resolveInterfaceMethod(ClassInfo<M, F> info, String name, MethodType type) {
		if (!Modifier.isInterface(info.accessFlags())) {
			return ResolutionError.ACC_INTERFACE_UNSET;
		}
		var resolution = uncachedInterfaceMethod(info, name, type);
		if (resolution == null) {
			return ResolutionError.NO_SUCH_METHOD;
		}
		if (Modifier.isStatic(resolution.member().accessFlags())) {
			return ResolutionError.ACC_STATIC_SET;
		}
		return resolution;
	}

	@Override
	public ResolutionResult<F> resolveStaticField(ClassInfo<M, F> owner, String name, ClassType type) {
		ClassInfo<M, F> info = owner;
		F field = null;
		while (owner != null) {
			field = owner.getField(name, type);
			if (field != null) {
				info = owner;
				break;
			}
			owner = owner.superClass();
		}
		if (field == null) {
			var resolution = this.<F, ClassType>uncachedInterfaceLookup(info, name, type, false, ClassInfo::getField);
			if (resolution != null) {
				if (!Modifier.isStatic(resolution.member().accessFlags())) {
					return ResolutionError.ACC_STATIC_UNSET;
				}
				return resolution;
			}
		}
		if (field == null) {
			return ResolutionError.NO_SUCH_FIELD;
		}
		if (!Modifier.isStatic(field.accessFlags())) {
			return ResolutionError.ACC_STATIC_UNSET;
		}
		return new ResolutionSuccess<>(info, field, false);
	}

	@Override
	public ResolutionResult<F> resolveVirtualField(ClassInfo<M, F> info, String name, ClassType type) {
		while (info != null) {
			F field = info.getField(name, type);
			if (field != null) {
				if (!Modifier.isStatic(field.accessFlags())) {
					return new ResolutionSuccess<>(info, field, false);
				}
				return ResolutionError.ACC_STATIC_SET;
			}
			info = info.superClass();
		}
		return ResolutionError.NO_SUCH_FIELD;
	}

	@Nullable
	ResolutionSuccess<M> uncachedLookupMethod(ClassInfo<M, F> owner, String name, MethodType descriptor) {
		do {
			M member = owner.getMethod(name, descriptor);
			if (member != null) {
				return new ResolutionSuccess<>(owner, member, false);
			}
		} while ((owner = owner.superClass()) != null);
		return null;
	}

	ResolutionSuccess<M> uncachedInterfaceMethod(ClassInfo<M, F> owner, String name, MethodType descriptor) {
		ClassInfo<M, F> info = owner;
		ResolutionSuccess<M> resolution = uncachedInterfaceLookup(owner, name, descriptor, true, ClassInfo::getMethod);
		if (resolution != null) {
			return resolution;
		}
		// We have corner case when we have an interface
		// that looks like that:
		// interface Foo { int hashCode(); }
		// TODO optimize
		info = info.superClass();
		while (info != null) {
			ClassInfo<M, F> superClass = info.superClass();
			if (superClass == null) {
				break;
			}
			info = superClass;
		}
		// Null in case info is java/lang/Object or an annotation (?), apparently
		if (info == null) {
			return null;
		}
		M member = info.getMethod(name, descriptor);
		if (member != null) {
			int accessFlags = member.accessFlags();
			if (Modifier.isStatic(accessFlags) || !Modifier.isPublic(accessFlags)) {
				member = null;
			}
		}
		return member == null ? null : new ResolutionSuccess<>(info, member, true);
	}

	@Nullable
	private <V extends Accessible, T extends Type> ResolutionSuccess<V> uncachedInterfaceLookup(ClassInfo<M, F> info, String name, T desc, boolean guessAbstract, UncachedResolve<V, T> resolve) {
		ResolutionSuccess<V> guess = null;
		try (Arena<ClassInfo<M, F>> arena = classArenaAllocator.push()) {
			arena.push(info); // Push interface/class to the arena
			while ((info = arena.poll()) != null) {
				if (Modifier.isInterface(info.accessFlags())) {
					// Only check field/method if it's an interface.
					V value = resolve.find((ClassInfo<V, V>) info, name, desc);
					if (value != null) {
						ResolutionSuccess<V> resolution = new ResolutionSuccess<>(info, value, false);
						if (!guessAbstract || !Modifier.isAbstract(value.accessFlags())) {
							return resolution;
						}
						if (guess == null) {
							guess = resolution;
						}
					}
				} else {
					// Push super class for later check of it's interfaces too.
					ClassInfo<M, F> superClass = info.superClass();
					if (superClass != null) {
						arena.push(superClass);
					}
				}
				// Push sub-interfaces of the class
				arena.push(info.interfaces());
			}
		}
		return guess;
	}

	@NotNull
	private ResolutionResult checkVirtualMethod(ResolutionSuccess<M> resolution) {
		if (resolution != null) {
			int flags = resolution.member().accessFlags();
			if (Modifier.isStatic(flags)) {
				return ResolutionError.ACC_STATIC_SET;
			}
			if (Modifier.isAbstract(flags) && !Modifier.isAbstract(resolution.owner().accessFlags())) {
				return ResolutionError.ACC_ABSTRACT_UNSET;
			}
			return resolution;
		}
		return ResolutionError.NO_SUCH_METHOD;
	}

	private interface UncachedResolve<M extends Accessible, T extends Type> {
		M find(ClassInfo<M, M> info, String name, T desc);
	}
}
