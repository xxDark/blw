package dev.xdark.blw.constantpool;

public final class EntryFieldRef extends EntryMemberRef {

	public EntryFieldRef(int classIndex, int nameAndTypeIndex) {
		super(classIndex, nameAndTypeIndex);
	}

	@Override
	public int tag() {
		return Tag.Fieldref;
	}
}
