package dev.xdark.blw.constant;

public final class OfInt implements Constant {
	private final int value;

	public OfInt(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

	@Override
	public void accept(ConstantSink sink) {
		sink.acceptInt(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof OfInt that)) return false;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(value);
	}
}
