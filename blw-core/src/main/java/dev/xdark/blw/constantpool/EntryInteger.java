package dev.xdark.blw.constantpool;

import dev.xdark.blw.constant.OfInt;

public final class EntryInteger implements Entry {
	private final OfInt value;

	public EntryInteger(OfInt value) {
		this.value = value;
	}

	public OfInt value() {
		return value;
	}

	@Override
	public int tag() {
		return Tag.Integer;
	}
}
