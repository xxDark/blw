package dev.xdark.blw.type.generic;

import dev.xdark.blw.constant.Constant;
import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.MethodType;
import dev.xdark.blw.type.ObjectType;
import dev.xdark.blw.type.Type;
import dev.xdark.blw.type.TypeFactory;

import java.util.List;

public final class GenericTypeFactory implements TypeFactory {

	@Override
	public MethodHandle methodHandle(int kind, ObjectType owner, String name, Type descriptor, boolean itf) {
		return new MethodHandle(kind, owner, name, descriptor, itf);
	}

	@Override
	public ConstantDynamic constantDynamic(String name, ClassType descriptor, MethodHandle bootstrapHandle, List<Constant> args) {
		return new ConstantDynamic(name, descriptor, bootstrapHandle, args);
	}

	@Override
	public InvokeDynamic invokeDynamic(String name, MethodType descriptor, MethodHandle bootstrapHandle, List<Constant> args) {
		return new InvokeDynamic(name, descriptor, bootstrapHandle, args);
	}

}
