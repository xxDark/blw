package dev.xdark.blw.constantpool;

import dev.xdark.blw.constant.OfFloat;

public final class EntryFloat implements Entry {
	private final OfFloat value;

	public EntryFloat(OfFloat value) {
		this.value = value;
	}

	public OfFloat value() {
		return value;
	}

	@Override
	public int tag() {
		return Tag.Float;
	}
}
