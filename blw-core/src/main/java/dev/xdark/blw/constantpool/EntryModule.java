package dev.xdark.blw.constantpool;

public final class EntryModule implements Entry {
	private final int nameIndex;

	public EntryModule(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public int nameIndex() {
		return nameIndex;
	}

	@Override
	public int tag() {
		return Tag.Module;
	}
}
