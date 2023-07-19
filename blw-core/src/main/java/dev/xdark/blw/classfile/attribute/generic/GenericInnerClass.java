package dev.xdark.blw.classfile.attribute.generic;

import dev.xdark.blw.classfile.attribute.InnerClass;
import dev.xdark.blw.type.InstanceType;
import org.jetbrains.annotations.Nullable;

public final class GenericInnerClass implements InnerClass {
	private final int accessFlags;
	private final InstanceType type;
	private final InstanceType outerType;
	private final String innerName;

	public GenericInnerClass(int accessFlags, InstanceType type, InstanceType outerType, String innerName) {
		this.accessFlags = accessFlags;
		this.type = type;
		this.outerType = outerType;
		this.innerName = innerName;
	}

	@Override
	public int accessFlags() {
		return accessFlags;
	}

	@Override
	public InstanceType type() {
		return type;
	}

	@Override
	public @Nullable InstanceType outerType() {
		return outerType;
	}

	@Override
	public @Nullable String innerName() {
		return innerName;
	}
}
