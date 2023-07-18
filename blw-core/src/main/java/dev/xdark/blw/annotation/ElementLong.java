package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Element;

public final class ElementLong implements Element {
	private final long value;

	public ElementLong(long value) {
		this.value = value;
	}

	public long value() {
		return value;
	}
}
