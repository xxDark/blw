package dev.xdark.blw.type;

import dev.xdark.blw.constant.Constant;

import java.util.List;

public final class ConstantDynamic implements DynamicAlike {
	private final String name;
	private final ClassType descriptor;
	private final MethodHandle methodHandle;
	private final List<Constant> args;

	public ConstantDynamic(String name, ClassType descriptor, MethodHandle methodHandle, List<Constant> args) {
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
	public ClassType type() {
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
