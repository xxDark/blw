package dev.xdark.blw.util;

import java.io.Closeable;

/**
 * Because Java does not have destructors
 * and does not support manual stack allocation,
 * here we are.
 */
public interface StackObj extends Closeable {

	@Override
	void close();
}
