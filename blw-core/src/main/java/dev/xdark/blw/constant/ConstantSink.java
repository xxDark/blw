package dev.xdark.blw.constant;

public interface ConstantSink {

	void acceptString(OfString value);

	void acceptMethodHandle(OfMethodHandle value);

	void acceptType(OfType value);

	void acceptDynamic(OfDynamic value);

	void acceptLong(OfLong value);

	void acceptDouble(OfDouble value);

	void acceptInt(OfInt value);

	void acceptFloat(OfFloat value);
}
