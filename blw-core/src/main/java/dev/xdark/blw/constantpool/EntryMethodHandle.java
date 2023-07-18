package dev.xdark.blw.constantpool;

public final class EntryMethodHandle implements Entry {
	private final int referenceKind;
	private final int referenceIndex;

	public EntryMethodHandle(int referenceKind, int referenceIndex) {
		this.referenceKind = referenceKind;
		this.referenceIndex = referenceIndex;
	}

	public int referenceKind() {
		return referenceKind;
	}

	public int referenceIndex() {
		return referenceIndex;
	}

	@Override
	public int tag() {
		return Tag.MethodHandle;
	}
}
