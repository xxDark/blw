package dev.xdark.blw.constantpool;

import dev.xdark.blw.constant.OfDouble;

public final class EntryDouble implements Entry {
	private final OfDouble value;

	public EntryDouble(OfDouble value) {
		this.value = value;
	}

	public OfDouble value() {
		return value;
	}

	@Override
	public int tag() {
		return Tag.Double;
	}
}
