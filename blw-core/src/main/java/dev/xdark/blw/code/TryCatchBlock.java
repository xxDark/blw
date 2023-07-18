package dev.xdark.blw.code;

import dev.xdark.blw.type.InstanceType;
import org.jetbrains.annotations.Nullable;

public final class TryCatchBlock {
	private final Label start, end, handler;
	private final InstanceType type;

	public TryCatchBlock(Label start, Label end, Label handler, InstanceType type) {
		this.start = start;
		this.end = end;
		this.handler = handler;
		this.type = type;
	}

	public Label start() {
		return start;
	}

	public Label end() {
		return end;
	}

	public Label handler() {
		return handler;
	}

	@Nullable
	public InstanceType type() {
		return type;
	}
}
