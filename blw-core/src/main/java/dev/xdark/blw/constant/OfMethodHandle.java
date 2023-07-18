package dev.xdark.blw.constant;

import dev.xdark.blw.type.MethodHandle;

public final class OfMethodHandle implements ReferenceConstant<MethodHandle> {
	private final MethodHandle value;

	public OfMethodHandle(MethodHandle value) {
		this.value = value;
	}

	@Override
	public MethodHandle value() {
		return value;
	}

	@Override
	public void accept(ConstantSink sink) {
		sink.acceptMethodHandle(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof OfMethodHandle that)) return false;
		return value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
