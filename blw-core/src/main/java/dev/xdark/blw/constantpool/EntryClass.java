package dev.xdark.blw.constantpool;

public final class EntryClass implements Entry {
	private final int nameIndex;

	public EntryClass(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public int nameIndex() {
		return nameIndex;
	}

	@Override
	public int tag() {
		return Tag.Class;
	}
}
