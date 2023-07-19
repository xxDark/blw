package dev.xdark.blw.classfile;

import dev.xdark.blw.code.attribute.Local;
import org.jetbrains.annotations.Nullable;

public sealed interface Signed permits ClassFileView, Local, Member {

	@Nullable
	String signature();
}
