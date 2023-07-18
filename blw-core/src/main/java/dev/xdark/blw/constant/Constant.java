package dev.xdark.blw.constant;

public sealed interface Constant permits ReferenceConstant, OfFloat, OfDouble, OfInt, OfLong {

	void accept(ConstantSink sink);
}
