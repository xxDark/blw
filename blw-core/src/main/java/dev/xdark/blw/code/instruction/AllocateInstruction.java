package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Instruction;
import dev.xdark.blw.code.ExtensionOpcodes;
import dev.xdark.blw.type.ObjectType;

public final class AllocateInstruction implements Instruction {

	private final ObjectType type;

	public AllocateInstruction(ObjectType type) {
		this.type = type;
	}

	@Override
	public int opcode() {
		return ExtensionOpcodes.ALLOCATE;
	}

	public ObjectType type() {
		return type;
	}
}
