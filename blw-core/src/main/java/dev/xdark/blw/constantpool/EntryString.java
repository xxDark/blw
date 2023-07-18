package dev.xdark.blw.constantpool;

public final class EntryString implements Entry {
	private final int utf8Index;

	public EntryString(int utf8Index) {
		this.utf8Index = utf8Index;
	}

	public int utf8Index() {
		return utf8Index;
	}

	@Override
	public int tag() {
		return Tag.String;
	}
}
