package dev.xdark.blw.resolution;

public interface Resolution<M extends MemberInfo> {

	ClassInfo owner();

	M member();

	boolean isForced();
}
