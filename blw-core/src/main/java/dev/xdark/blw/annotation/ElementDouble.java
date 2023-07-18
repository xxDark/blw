package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Element;

public final class ElementDouble implements Element {
	private final double value;

	public ElementDouble(double value) {
		this.value = value;
	}

	public double value() {
		return value;
	}
}
