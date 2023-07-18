package dev.xdark.blw.code.instruction;

import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.InstanceType;

public final class FieldInstruction implements MemberInstruction {
	private final int opcode;
	private final InstanceType owner;
	private final String name;
	private final ClassType descriptor;

	public FieldInstruction(int opcode, InstanceType owner, String name, ClassType descriptor) {
		this.opcode = opcode;
		this.owner = owner;
		this.name = name;
		this.descriptor = descriptor;
	}

	@Override
	public int opcode() {
		return opcode;
	}

	@Override
	public InstanceType owner() {
		return owner;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ClassType type() {
		return descriptor;
	}
}
