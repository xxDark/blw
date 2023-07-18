package dev.xdark.blw.constant;

public final class OfLong implements Constant {
	private final long value;

	public OfLong(long value) {
		this.value = value;
	}

	public long value() {
		return value;
	}

	@Override
	public void accept(ConstantSink sink) {
		sink.acceptLong(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof OfLong that)) return false;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(value);
	}
}
