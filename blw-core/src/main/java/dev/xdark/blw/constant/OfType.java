package dev.xdark.blw.constant;

import dev.xdark.blw.type.Type;

public final class OfType implements ReferenceConstant<Type> {
	private final Type value;

	public OfType(Type value) {
		this.value = value;
	}

	@Override
	public Type value() {
		return value;
	}

	@Override
	public void accept(ConstantSink sink) {
		sink.acceptType(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof OfType that)) return false;
		return value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
