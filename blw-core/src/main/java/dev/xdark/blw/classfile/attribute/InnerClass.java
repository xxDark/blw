package dev.xdark.blw.classfile.attribute;

import dev.xdark.blw.classfile.Accessible;
import dev.xdark.blw.type.InstanceType;
import org.jetbrains.annotations.Nullable;

public interface InnerClass extends Accessible {

	InstanceType type();

	@Nullable
	InstanceType outerType();

	@Nullable
	String innerName();
}
