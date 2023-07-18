package dev.xdark.blw.constant;

public final class OfString implements ReferenceConstant<String> {
	private final String value;

	public OfString(String value) {
		this.value = value;
	}

	@Override
	public String value() {
		return value;
	}

	@Override
	public void accept(ConstantSink sink) {
		sink.acceptString(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof OfString that)) return false;
		return value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
