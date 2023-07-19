package dev.xdark.blw.code.attribute;

import dev.xdark.blw.classfile.Signed;
import dev.xdark.blw.code.Label;
import dev.xdark.blw.type.ClassType;

public non-sealed interface Local extends Signed {

	Label start();

	Label end();

	int index();

	String name();

	ClassType type();
}
