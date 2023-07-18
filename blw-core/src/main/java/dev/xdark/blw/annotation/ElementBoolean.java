package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Element;

public final class ElementBoolean implements Element {
	private final boolean value;

	public ElementBoolean(boolean value) {
		this.value = value;
	}

	public boolean value() {
		return value;
	}
}
