package dev.xdark.blw.type.generic;

import dev.xdark.blw.constant.Constant;
import dev.xdark.blw.type.DynamicAlike;
import dev.xdark.blw.type.MethodType;

import java.util.List;

public final class InvokeDynamic implements DynamicAlike {
	private final String name;
	private final MethodType descriptor;
	private final MethodHandle methodHandle;
	private final List<Constant> args;

	public InvokeDynamic(String name, MethodType descriptor, MethodHandle methodHandle, List<Constant> args) {
		this.name = name;
		this.descriptor = descriptor;
		this.methodHandle = methodHandle;
		this.args = args;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public MethodType type() {
		return descriptor;
	}

	@Override
	public MethodHandle methodHandle() {
		return methodHandle;
	}

	@Override
	public List<Constant> args() {
		return args;
	}
}
