package dev.xdark.blw.resolution;

import dev.xdark.blw.classfile.Accessible;
import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.MethodType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface ClassInfo<M extends Accessible, F extends Accessible> {

	int accessFlags();

	@Nullable
	ClassInfo<M, F> superClass();

	@NotNull Stream<@NotNull ClassInfo<M, F>> interfaces();

	@Nullable M getMethod(String name, MethodType type);

	@Nullable F getField(String name, ClassType type);
}
