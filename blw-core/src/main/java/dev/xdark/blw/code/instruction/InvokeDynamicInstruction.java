package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Instruction;
import dev.xdark.blw.code.JavaOpcodes;
import dev.xdark.blw.constant.Constant;
import dev.xdark.blw.type.MethodHandle;
import dev.xdark.blw.type.Type;

import java.util.List;

public final class InvokeDynamicInstruction implements Instruction {
	private final String name;
	private final Type descriptor;
	private final MethodHandle methodHandle;
	private final List<Constant> args;

	public InvokeDynamicInstruction(String name, Type descriptor, MethodHandle methodHandle, List<Constant> args) {
		this.name = name;
		this.descriptor = descriptor;
		this.methodHandle = methodHandle;
		this.args = args;
	}

	public String name() {
		return name;
	}

	public Type type() {
		return descriptor;
	}

	public MethodHandle bootstrapHandle() {
		return methodHandle;
	}

	public List<Constant> args() {
		return args;
	}

	@Override
	public int opcode() {
		return JavaOpcodes.INVOKEDYNAMIC;
	}
}
