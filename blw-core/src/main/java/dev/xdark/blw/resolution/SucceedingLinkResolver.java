package dev.xdark.blw.resolution;

import dev.xdark.blw.classfile.Accessible;
import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.MethodType;

/**
 * Always succeeding resolver.
 * Exception must be thrown.
 *
 * @author xDark
 */
public interface SucceedingLinkResolver<M extends Accessible, F extends Accessible> extends LinkResolver<M, F> {

	@Override
	ResolutionSuccess<M> resolveVirtualMethod(ClassInfo<M, F> info, String name, MethodType type);

	@Override
	ResolutionSuccess<M> resolveSpecialMethod(ClassInfo<M, F> info, String name, MethodType type, boolean itf);

	@Override
	ResolutionSuccess<M> resolveStaticMethod(ClassInfo<M, F> info, String name, MethodType type, boolean itf);

	@Override
	ResolutionSuccess<M> resolveInterfaceMethod(ClassInfo<M, F> info, String name, MethodType type);

	@Override
	ResolutionSuccess<F> resolveStaticField(ClassInfo<M, F> info, String name, ClassType type);

	@Override
	ResolutionSuccess<F> resolveVirtualField(ClassInfo<M, F> info, String name, ClassType type);
}
