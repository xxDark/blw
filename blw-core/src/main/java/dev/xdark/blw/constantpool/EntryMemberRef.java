package dev.xdark.blw.constantpool;

public abstract class EntryMemberRef implements Entry {
	private final int classIndex;
	private final int nameAndTypeIndex;

	protected EntryMemberRef(int classIndex, int nameAndTypeIndex) {
		this.classIndex = classIndex;
		this.nameAndTypeIndex = nameAndTypeIndex;
	}

	public final int classIndex() {
		return classIndex;
	}

	public final int nameAndTypeIndex() {
		return nameAndTypeIndex;
	}
}
