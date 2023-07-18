package dev.xdark.blw.code.generic;

import dev.xdark.blw.code.Label;

public final class GenericLabel implements Label {
	private int lineNumber = UNSET;
	private int index = UNSET;

	@Override
	public int index() {
		return index;
	}

	@Override
	public void index(int index) {
		this.index = index;
	}

	@Override
	public int lineNumber() {
		return lineNumber;
	}

	@Override
	public void lineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}
