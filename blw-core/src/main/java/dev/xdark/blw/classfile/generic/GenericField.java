package dev.xdark.blw.classfile.generic;

import dev.xdark.blw.annotation.Annotation;
import dev.xdark.blw.classfile.Field;
import dev.xdark.blw.constant.Constant;
import dev.xdark.blw.type.ClassType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class GenericField extends GenericMember implements Field {
	private final ClassType type;
	private final Constant defaultValue;

	public GenericField(int accessFlags, String name, String signature, List<Annotation> visibleRuntimeAnnotations, List<Annotation> invisibleRuntimeAnnotations, ClassType type, Constant defaultValue) {
		super(accessFlags, name, signature, visibleRuntimeAnnotations, invisibleRuntimeAnnotations);
		this.type = type;
		this.defaultValue = defaultValue;
	}

	@Override
	public ClassType type() {
		return type;
	}

	@Override
	public @Nullable Constant defaultValue() {
		return defaultValue;
	}
}
