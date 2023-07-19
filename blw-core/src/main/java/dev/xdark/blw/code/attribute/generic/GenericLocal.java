package dev.xdark.blw.code.attribute.generic;

import dev.xdark.blw.code.attribute.Local;
import dev.xdark.blw.code.Label;
import dev.xdark.blw.type.ClassType;
import org.jetbrains.annotations.Nullable;

public final class GenericLocal implements Local {

	private final Label start, end;
	private final int index;
	private final String name;
	private final ClassType type;
	private final String signature;

	public GenericLocal(Label start, Label end, int index, String name, ClassType type, String signature) {
		this.start = start;
		this.end = end;
		this.index = index;
		this.name = name;
		this.type = type;
		this.signature = signature;
	}

	@Override
	public Label start() {
		return start;
	}

	@Override
	public Label end() {
		return end;
	}

	@Override
	public int index() {
		return index;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ClassType type() {
		return type;
	}

	@Override
	public @Nullable String signature() {
		return signature;
	}
}
