package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Element;

public final class ElementInt implements Element {
	private final int value;

	public ElementInt(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
