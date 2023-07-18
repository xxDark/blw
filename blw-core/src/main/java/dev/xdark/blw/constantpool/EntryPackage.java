package dev.xdark.blw.constantpool;

public final class EntryPackage implements Entry {
	private final int nameIndex;

	public EntryPackage(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public int nameIndex() {
		return nameIndex;
	}

	@Override
	public int tag() {
		return Tag.Package;
	}
}
