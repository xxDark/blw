package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Element;
import dev.xdark.blw.type.ObjectType;

public final class ElementType implements Element {
	private final ObjectType value;

	public ElementType(ObjectType value) {
		this.value = value;
	}

	public ObjectType value() {
		return value;
	}
}
