package dev.xdark.blw.resolution.jvm;

import dev.xdark.blw.classfile.Accessible;
import dev.xdark.blw.resolution.ClassInfo;
import dev.xdark.blw.resolution.ResolutionError;
import dev.xdark.blw.resolution.ResolutionResult;
import dev.xdark.blw.resolution.ResolutionSuccess;
import dev.xdark.blw.resolution.RuntimeResolver;
import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.MethodType;

import java.lang.reflect.Modifier;

@SuppressWarnings("unchecked")
public final class JVMRuntimeResolver<M extends Accessible, F extends Accessible> implements RuntimeResolver<M, F> {
	private final JVMLinkResolver<M, F> linkResolver;

	public JVMRuntimeResolver(JVMLinkResolver<M, F> linkResolver) {
		this.linkResolver = linkResolver;
	}

	@Override
	public ResolutionResult<M> resolveVirtualMethod(ClassInfo<M, F> info, String name, MethodType type) {
		var result = linkResolver.resolveVirtualMethod(info, name, type);
		if (result instanceof ResolutionSuccess<M> success) {
			if (Modifier.isAbstract(success.member().accessFlags())) {
				return ResolutionError.ACC_ABSTRACT_SET;
			}
		}
		return result;
	}

	@Override
	public ResolutionResult<M> resolveSpecialMethod(ClassInfo<M, F> info, String name, MethodType type, boolean itf) {
		var result = linkResolver.resolveSpecialMethod(info, name, type, itf);
		if (result instanceof ResolutionSuccess<M> success) {
			if (Modifier.isAbstract(success.member().accessFlags())) {
				return ResolutionError.ACC_ABSTRACT_SET;
			}
		}
		return result;
	}

	@Override
	public ResolutionResult<M> resolveStaticMethod(ClassInfo<M, F> info, String name, MethodType type, boolean itf) {
		return linkResolver.resolveStaticMethod(info, name, type, itf);
	}

	@Override
	public ResolutionResult<M> resolveInterfaceMethod(ClassInfo<M, F> info, String name, MethodType type) {
		var resolution = linkResolver.uncachedLookupMethod(info, name, type);
		if (resolution == null) {
			resolution = linkResolver.uncachedInterfaceMethod(info, name, type);
		}
		if (resolution != null) {
			int accessFlags = resolution.member().accessFlags();
			if (Modifier.isStatic(accessFlags)) {
				return ResolutionError.ACC_STATIC_SET;
			}
			if (Modifier.isAbstract(accessFlags)) {
				return ResolutionError.ACC_ABSTRACT_SET;
			}
			return resolution;
		}
		return ResolutionError.NO_SUCH_METHOD;
	}

	@Override
	public ResolutionResult<F> resolveStaticField(ClassInfo<M, F> info, String name, ClassType type) {
		return linkResolver.resolveStaticField(info, name, type);
	}

	@Override
	public ResolutionResult<F> resolveVirtualField(ClassInfo<M, F> info, String name, ClassType type) {
		return linkResolver.resolveVirtualField(info, name, type);
	}
}
