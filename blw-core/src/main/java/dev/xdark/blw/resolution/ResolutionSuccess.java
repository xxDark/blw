package dev.xdark.blw.resolution;

import dev.xdark.blw.classfile.Accessible;

public final class ResolutionSuccess<M extends Accessible> implements ResolutionResult<M> {
	private final ClassInfo<?, ?> owner;
	private final M member;
	private final boolean isForced;

	public ResolutionSuccess(ClassInfo<?, ?> owner, M member, boolean isForced) {
		this.owner = owner;
		this.member = member;
		this.isForced = isForced;
	}

	public ClassInfo<?, ?> owner() {
		return owner;
	}

	public M member() {
		return member;
	}

	public boolean isForced() {
		return isForced;
	}
}
