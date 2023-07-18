package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Element;

public final class ElementFloat implements Element {
	private final float value;

	public ElementFloat(float value) {
		this.value = value;
	}

	public float value() {
		return value;
	}
}
