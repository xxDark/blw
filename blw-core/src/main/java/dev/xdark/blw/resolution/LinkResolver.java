package dev.xdark.blw.resolution;

import dev.xdark.blw.classfile.Accessible;
import dev.xdark.blw.resolution.jvm.JVMLinkResolver;
import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.MethodType;
import dev.xdark.blw.util.arena.ArenaAllocator;
import dev.xdark.blw.util.arena.FrameArenaAllocator;

public interface LinkResolver<M extends Accessible, F extends Accessible> {

	ResolutionResult<M> resolveVirtualMethod(ClassInfo<M, F> info, String name, MethodType type);

	ResolutionResult<M> resolveSpecialMethod(ClassInfo<M, F> info, String name, MethodType type, boolean itf);

	ResolutionResult<M> resolveStaticMethod(ClassInfo<M, F> info, String name, MethodType type, boolean itf);

	ResolutionResult<M> resolveInterfaceMethod(ClassInfo<M, F> info, String name, MethodType type);

	ResolutionResult<F> resolveStaticField(ClassInfo<M, F> info, String name, ClassType type);

	ResolutionResult<F> resolveVirtualField(ClassInfo<M, F> info, String name, ClassType type);

	static <M extends Accessible, F extends Accessible> LinkResolver<M, F> jvm(ArenaAllocator<?> allocator) {
		return new JVMLinkResolver<>(allocator);
	}

	static <M extends Accessible, F extends Accessible> LinkResolver<M, F> jvm() {
		return jvm(new FrameArenaAllocator<>());
	}
}
