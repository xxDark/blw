package dev.xdark.blw.type;

public final class PrimitiveType implements ClassType {
	private final String descriptor;
	private final int kind;
	private final String name;

	PrimitiveType(String descriptor, int kind, String name) {
		this.descriptor = descriptor;
		this.kind = kind;
		this.name = name;
	}

	public int kind() {
		return kind;
	}

	public String name() {
		return name;
	}

	@Override
	public String descriptor() {
		return descriptor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof PrimitiveType that)) return false;
		return kind == that.kind;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(kind);
	}

	@Override
	public String toString() {
		return descriptor;
	}
}
