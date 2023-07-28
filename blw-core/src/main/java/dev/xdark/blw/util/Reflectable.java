package dev.xdark.blw.util;

public interface Reflectable<T> {

	T reflectAs();

	static <T> Reflectable<T> wrap(T value) {
		return () -> value;
	}
}
