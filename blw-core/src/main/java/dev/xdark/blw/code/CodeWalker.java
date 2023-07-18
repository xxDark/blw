package dev.xdark.blw.code;

import org.jetbrains.annotations.Nullable;

public interface CodeWalker {

	int index();

	@Nullable
	CodeElement element();

	void advance();

	void set(Label label);
}
