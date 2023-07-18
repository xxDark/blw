package dev.xdark.blw.type;

public sealed interface ObjectType extends ClassType permits InstanceType, ArrayType {

	String internalName();

	default String externalName() {
		return Types.externalName(internalName());
	}
}
