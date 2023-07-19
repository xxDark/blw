package dev.xdark.blw.classfile.attribute.generic;

import dev.xdark.blw.classfile.attribute.Parameter;

public final class GenericParameter implements Parameter {
	private final int accessFlags;
	private final String name;

	public GenericParameter(int accessFlags, String name) {
		this.accessFlags = accessFlags;
		this.name = name;
	}

	@Override
	public int accessFlags() {
		return accessFlags;
	}

	@Override
	public String name() {
		return name;
	}
}
