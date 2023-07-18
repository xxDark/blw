package dev.xdark.blw.type;

import dev.xdark.blw.constant.Constant;
import dev.xdark.blw.type.generic.ConstantDynamic;
import dev.xdark.blw.type.generic.MethodHandle;
import dev.xdark.blw.type.generic.InvokeDynamic;

import java.util.List;

public interface TypeFactory {

	MethodHandle methodHandle(int kind, ObjectType owner, String name, Type descriptor, boolean itf);

	ConstantDynamic constantDynamic(String name, ClassType descriptor, MethodHandle bootstrapHandle, List<Constant> args);

	InvokeDynamic invokeDynamic(String name, MethodType descriptor, MethodHandle bootstrapHandle, List<Constant> args);
}
