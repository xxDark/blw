package dev.xdark.blw.code.instruction;

import dev.xdark.blw.type.MethodType;
import dev.xdark.blw.type.ObjectType;

public final class MethodInstruction implements MemberInstruction {
	private final int opcode;
	private final ObjectType owner;
	private final String name;
	private final MethodType descriptor;
	private final boolean isInterface;

	public MethodInstruction(int opcode, ObjectType owner, String name, MethodType descriptor, boolean isInterface) {
		this.opcode = opcode;
		this.owner = owner;
		this.name = name;
		this.descriptor = descriptor;
		this.isInterface = isInterface;
	}

	@Override
	public int opcode() {
		return opcode;
	}

	@Override
	public ObjectType owner() {
		return owner;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public MethodType type() {
		return descriptor;
	}

	public boolean isInterface() {
		return isInterface;
	}
}
