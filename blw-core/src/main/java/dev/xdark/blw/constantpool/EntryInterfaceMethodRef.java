package dev.xdark.blw.constantpool;

public final class EntryInterfaceMethodRef extends EntryMemberRef {

	public EntryInterfaceMethodRef(int classIndex, int nameAndTypeIndex) {
		super(classIndex, nameAndTypeIndex);
	}

	@Override
	public int tag() {
		return Tag.InterfaceMethodref;
	}
}
