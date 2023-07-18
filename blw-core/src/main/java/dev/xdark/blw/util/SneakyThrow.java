package dev.xdark.blw.util;

public final class SneakyThrow {

	private SneakyThrow() {
	}

	public static <X extends Throwable> void propagate(Throwable t) throws X {
		throw (X) t;
	}
}
