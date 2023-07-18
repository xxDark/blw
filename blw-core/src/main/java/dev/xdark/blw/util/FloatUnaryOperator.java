package dev.xdark.blw.util;

import java.util.Objects;

@FunctionalInterface
public interface FloatUnaryOperator {

	float applyAsFloat(float operand);

	default FloatUnaryOperator compose(FloatUnaryOperator before) {
		Objects.requireNonNull(before, "before");
		return operand -> applyAsFloat(before.applyAsFloat(operand));
	}

	default FloatUnaryOperator andThen(FloatUnaryOperator after) {
		Objects.requireNonNull(after, "after");
		return operand -> after.applyAsFloat(applyAsFloat(operand));
	}

	static FloatUnaryOperator identity() {
		return x -> x;
	}
}
