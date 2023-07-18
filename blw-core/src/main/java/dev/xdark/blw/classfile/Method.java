package dev.xdark.blw.classfile;

import dev.xdark.blw.code.Code;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.type.MethodType;
import dev.xdark.blw.util.Reflectable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Method extends Member, Reflectable<Method> {

	@Nullable
	Code code();

	@Override
	MethodType type();

	List<InstanceType> exceptionTypes();

	@Override
	default Method reflectAs() {
		return this;
	}
}
