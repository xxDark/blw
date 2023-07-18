package dev.xdark.blw.classfile.generic;

import dev.xdark.blw.annotation.Annotation;
import dev.xdark.blw.classfile.Method;
import dev.xdark.blw.code.Code;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.type.MethodType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class GenericMethod extends GenericMember implements Method {
	private final MethodType type;
	private final Code code;
	private final List<InstanceType> exceptionTypes;

	public GenericMethod(int accessFlags, String name, String signature, List<Annotation> visibleRuntimeAnnotations, List<Annotation> invisibleRuntimeAnnotations, MethodType type, Code code, List<InstanceType> exceptionTypes) {
		super(accessFlags, name, signature, visibleRuntimeAnnotations, invisibleRuntimeAnnotations);
		this.type = type;
		this.code = code;
		this.exceptionTypes = exceptionTypes;
	}

	@Override
	public @Nullable Code code() {
		return code;
	}

	@Override
	public MethodType type() {
		return type;
	}

	@Override
	public List<InstanceType> exceptionTypes() {
		return exceptionTypes;
	}
}
