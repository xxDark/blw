package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Label;

import java.util.stream.Stream;

public final class ConditionalJumpInstruction implements BranchInstruction {

	private final int opcode;
	private final Label target;

	public ConditionalJumpInstruction(int opcode, Label target) {
		this.opcode = opcode;
		this.target = target;
	}

	public Label target() {
		return target;
	}

	@Override
	public int opcode() {
		return opcode;
	}

	@Override
	public Stream<Label> allTargets() {
		return Stream.of(target);
	}
}
