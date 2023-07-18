package dev.xdark.blw.constantpool;

import dev.xdark.blw.constant.OfDynamic;

public final class EntryDynamic implements Entry {
	private final OfDynamic value;

	public EntryDynamic(OfDynamic value) {
		this.value = value;
	}

	public OfDynamic value() {
		return value;
	}

	@Override
	public int tag() {
		return Tag.Dynamic;
	}
}
