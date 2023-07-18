package dev.xdark.blw.resolution;

public sealed interface MemberInfo permits MethodInfo, FieldInfo {

	int accessFlags();
}
