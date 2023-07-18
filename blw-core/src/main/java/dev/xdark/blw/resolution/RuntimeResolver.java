package dev.xdark.blw.resolution;

import dev.xdark.blw.classfile.Accessible;
import dev.xdark.blw.resolution.jvm.JVMLinkResolver;
import dev.xdark.blw.resolution.jvm.JVMRuntimeResolver;
import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.MethodType;

public interface RuntimeResolver<M extends Accessible, F extends Accessible> {

	ResolutionResult<M> resolveVirtualMethod(ClassInfo<M, F> info, String name, MethodType type);

	ResolutionResult<M> resolveSpecialMethod(ClassInfo<M, F> info, String name, MethodType type, boolean itf);

	ResolutionResult<M> resolveStaticMethod(ClassInfo<M, F> info, String name, MethodType type, boolean itf);

	ResolutionResult<M> resolveInterfaceMethod(ClassInfo<M, F> info, String name, MethodType type);

	ResolutionResult<F> resolveStaticField(ClassInfo<M, F> info, String name, ClassType type);

	ResolutionResult<F> resolveVirtualField(ClassInfo<M, F> info, String name, ClassType type);

	static <M extends Accessible, F extends Accessible> RuntimeResolver<M, F> jvm(LinkResolver<M, F> linkResolver) {
		if (!(linkResolver instanceof JVMLinkResolver<M, F> jlr)) {
			throw new IllegalArgumentException("linkResolver must be JVMLinkResolver");
		}
		return new JVMRuntimeResolver<>(jlr);
	}

	static <M extends Accessible, F extends Accessible> RuntimeResolver<M, F> jvm() {
		return jvm(LinkResolver.jvm());
	}
}
