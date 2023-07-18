package dev.xdark.blw.type;

public sealed interface ClassType extends Type permits ObjectType, PrimitiveType {
}
