package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Instruction;

public final class VarInstruction implements Instruction {
	private final int opcode;
	private final int variableIndex;

	public VarInstruction(int opcode, int variableIndex) {
		this.opcode = opcode;
		this.variableIndex = variableIndex;
	}

	@Override
	public int opcode() {
		return opcode;
	}

	public int variableIndex() {
		return variableIndex;
	}
}
