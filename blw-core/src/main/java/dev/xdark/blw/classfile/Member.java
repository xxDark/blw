package dev.xdark.blw.classfile;

import dev.xdark.blw.type.Type;

public non-sealed interface Member extends Accessible, Annotated, Signed {

	String name();

	Type type();
}
