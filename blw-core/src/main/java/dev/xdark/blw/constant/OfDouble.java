package dev.xdark.blw.constant;

public final class OfDouble implements Constant {
	private final double value;

	public OfDouble(double value) {
		this.value = value;
	}

	public double value() {
		return value;
	}

	@Override
	public void accept(ConstantSink sink) {
		sink.acceptDouble(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof OfDouble that)) return false;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(value);
	}
}
