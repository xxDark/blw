package dev.xdark.blw.classfile;

import org.jetbrains.annotations.Nullable;

public sealed interface Signed permits ClassFileView, Member {

	@Nullable
	String signature();
}
