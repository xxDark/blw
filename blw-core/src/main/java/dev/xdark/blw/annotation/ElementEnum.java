package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Element;
import dev.xdark.blw.type.InstanceType;

public final class ElementEnum implements Element {
	private final InstanceType type;
	private final String name;

	public ElementEnum(InstanceType type, String name) {
		this.type = type;
		this.name = name;
	}

	public InstanceType type() {
		return type;
	}

	public String name() {
		return name;
	}
}
