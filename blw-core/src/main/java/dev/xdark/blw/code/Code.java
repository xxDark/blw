package dev.xdark.blw.code;

import dev.xdark.blw.util.Reflectable;

import java.util.List;

public interface Code extends Reflectable<Code> {

	int maxStack();

	int maxLocals();

	CodeWalker walker();

	CodeList codeList();

	List<TryCatchBlock> tryCatchBlocks();

	default Label start() {
		return (Label) codeList().stream().filter(x -> x instanceof Label).findFirst().orElseThrow();
	}

	@Override
	default Code reflectAs() {
		return this;
	}
}
