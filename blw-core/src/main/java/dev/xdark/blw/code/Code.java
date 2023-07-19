package dev.xdark.blw.code;

import dev.xdark.blw.code.attribute.Local;
import dev.xdark.blw.util.Mutable;
import dev.xdark.blw.util.Reflectable;

import java.util.List;

public interface Code extends Reflectable<Code> {

	int maxStack();

	int maxLocals();

	CodeWalker walker();

	@Mutable
	List<CodeElement> elements();

	List<TryCatchBlock> tryCatchBlocks();

	List<Local> localVariables();

	default Label start() {
		return (Label) elements().stream().filter(x -> x instanceof Label).findFirst().orElseThrow();
	}

	@Override
	default Code reflectAs() {
		return this;
	}
}
