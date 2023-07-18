package dev.xdark.blw.constantpool;

public final class EntryMethodRef extends EntryMemberRef {

	public EntryMethodRef(int classIndex, int nameAndTypeIndex) {
		super(classIndex, nameAndTypeIndex);
	}

	@Override
	public int tag() {
		return Tag.Methodref;
	}
}
