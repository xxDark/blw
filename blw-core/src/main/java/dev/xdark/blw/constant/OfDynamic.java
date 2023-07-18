package dev.xdark.blw.constant;

import dev.xdark.blw.type.ConstantDynamic;

public final class OfDynamic implements ReferenceConstant<ConstantDynamic> {
	private final ConstantDynamic value;

	public OfDynamic(ConstantDynamic value) {
		this.value = value;
	}

	@Override
	public ConstantDynamic value() {
		return value;
	}

	@Override
	public void accept(ConstantSink sink) {
		sink.acceptDynamic(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof OfDynamic that)) return false;
		return value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
