package dev.xdark.blw.code;

public non-sealed interface Label extends CodeElement {
	int UNSET = -1;

	int index();

	void index(int index);

	int lineNumber();

	void lineNumber(int lineNumber);
}
