package dev.xdark.blw.util;

public final class BootClassLoader {
	private static final ClassLoader INSTANCE = new Impl();

	private BootClassLoader() {
	}

	public static ClassLoader instance() {
		return INSTANCE;
	}

	private static final class Impl extends ClassLoader {

		Impl() {
			super(null);
		}
	}
}
