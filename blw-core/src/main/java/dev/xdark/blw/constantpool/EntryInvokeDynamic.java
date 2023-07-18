package dev.xdark.blw.constantpool;

import dev.xdark.blw.type.InvokeDynamic;

public final class EntryInvokeDynamic implements Entry {
	private final InvokeDynamic value;

	public EntryInvokeDynamic(InvokeDynamic value) {
		this.value = value;
	}

	public InvokeDynamic value() {
		return value;
	}

	@Override
	public int tag() {
		return Tag.InvokeDynamic;
	}
}
