package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Instruction;
import dev.xdark.blw.code.Opcodes;
import dev.xdark.blw.type.ObjectType;

public final class CheckCastInstruction implements Instruction {
	private final ObjectType type;

	public CheckCastInstruction(ObjectType type) {
		this.type = type;
	}

	@Override
	public int opcode() {
		return Opcodes.CHECKCAST;
	}

	public ObjectType type() {
		return type;
	}
}
