package dev.xdark.blw.constantpool;

public final class EntryMethodType implements Entry {
	private final int descriptorIndex;

	public EntryMethodType(int descriptorIndex) {
		this.descriptorIndex = descriptorIndex;
	}

	public int descriptorIndex() {
		return descriptorIndex;
	}

	@Override
	public int tag() {
		return Tag.MethodType;
	}
}
