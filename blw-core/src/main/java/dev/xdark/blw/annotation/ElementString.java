package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Element;

public final class ElementString implements Element {
	private final String value;

	public ElementString(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
