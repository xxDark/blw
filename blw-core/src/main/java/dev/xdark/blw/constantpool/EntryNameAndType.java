package dev.xdark.blw.constantpool;

public final class EntryNameAndType implements Entry {
	private final int nameIndex;
	private final int typeIndex;

	public EntryNameAndType(int nameIndex, int typeIndex) {
		this.nameIndex = nameIndex;
		this.typeIndex = typeIndex;
	}

	public int nameIndex() {
		return nameIndex;
	}

	public int typeIndex() {
		return typeIndex;
	}

	@Override
	public int tag() {
		return Tag.NameAndType;
	}
}
