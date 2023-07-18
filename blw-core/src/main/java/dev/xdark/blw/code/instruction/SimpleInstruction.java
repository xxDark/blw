package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Instruction;

public final class SimpleInstruction implements Instruction {

	private final int opcode;

	public SimpleInstruction(int opcode) {
		this.opcode = opcode;
	}

	@Override
	public int opcode() {
		return opcode;
	}
}
