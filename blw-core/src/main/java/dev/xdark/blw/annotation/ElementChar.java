package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Element;

public final class ElementChar implements Element {
	private final char value;

	public ElementChar(char value) {
		this.value = value;
	}

	public char value() {
		return value;
	}
}
