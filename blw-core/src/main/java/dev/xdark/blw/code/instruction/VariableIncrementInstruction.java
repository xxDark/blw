package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Instruction;
import dev.xdark.blw.code.JavaOpcodes;

public final class VariableIncrementInstruction implements Instruction {
	private final int variableIndex, incrementBy;

	public VariableIncrementInstruction(int variableIndex, int incrementBy) {
		this.variableIndex = variableIndex;
		this.incrementBy = incrementBy;
	}

	public int variableIndex() {
		return variableIndex;
	}

	public int incrementBy() {
		return incrementBy;
	}

	@Override
	public int opcode() {
		return JavaOpcodes.IINC;
	}
}
