package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Element;

public final class ElementShort implements Element {
	private final short value;

	public ElementShort(short value) {
		this.value = value;
	}

	public short value() {
		return value;
	}
}
