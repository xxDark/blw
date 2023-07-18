package dev.xdark.blw.constant;

public final class OfFloat implements Constant {
	private final float value;

	public OfFloat(float value) {
		this.value = value;
	}

	public float value() {
		return value;
	}

	@Override
	public void accept(ConstantSink sink) {
		sink.acceptFloat(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof OfFloat that)) return false;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return Float.hashCode(value);
	}
}
