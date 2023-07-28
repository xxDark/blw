package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Instruction;
import dev.xdark.blw.code.JavaOpcodes;
import dev.xdark.blw.type.ObjectType;

public final class InstanceofInstruction implements Instruction {
	private final ObjectType type;

	public InstanceofInstruction(ObjectType type) {
		this.type = type;
	}

	public ObjectType type() {
		return type;
	}

	@Override
	public int opcode() {
		return JavaOpcodes.INSTANCEOF;
	}
}
