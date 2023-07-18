package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Element;

public final class ElementByte implements Element {
	private final byte value;

	public ElementByte(byte value) {
		this.value = value;
	}

	public byte value() {
		return value;
	}
}
