package dev.xdark.blw.constantpool;

import dev.xdark.blw.constant.OfString;

public final class EntryUtf8 implements Entry {
	private final OfString value;

	public EntryUtf8(OfString value) {
		this.value = value;
	}

	public OfString value() {
		return value;
	}

	@Override
	public int tag() {
		return Tag.Utf8;
	}
}
