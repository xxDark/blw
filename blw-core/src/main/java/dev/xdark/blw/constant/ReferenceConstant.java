package dev.xdark.blw.constant;

public sealed interface ReferenceConstant<T> extends Constant permits OfType, OfDynamic, OfMethodHandle, OfString {

	T value();
}
