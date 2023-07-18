package dev.xdark.blw.constantpool;

import dev.xdark.blw.constant.OfLong;

public final class EntryLong implements Entry {
	private final OfLong value;

	public EntryLong(OfLong value) {
		this.value = value;
	}

	public OfLong value() {
		return value;
	}

	@Override
	public int tag() {
		return Tag.Long;
	}
}
