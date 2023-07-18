package dev.xdark.blw.type;

public final class MethodHandle {
	private final int kind;
	private final ObjectType owner;
	private final String name;
	private final Type descriptor;
	private final boolean itf;

	public MethodHandle(int kind, ObjectType owner, String name, Type descriptor, boolean itf) {
		this.kind = kind;
		this.owner = owner;
		this.name = name;
		this.descriptor = descriptor;
		this.itf = itf;
	}

	public int kind() {
		return kind;
	}

	public ObjectType owner() {
		return owner;
	}

	public String name() {
		return name;
	}

	public Type type() {
		return descriptor;
	}

	public boolean isInterface() {
		return itf;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MethodHandle that)) return false;

		if (kind != that.kind()) return false;
		if (itf != that.isInterface()) return false;
		if (!owner.equals(that.owner())) return false;
		if (!name.equals(that.name())) return false;
		return descriptor.equals(that.type());
	}

	@Override
	public int hashCode() {
		int result = kind;
		result = 31 * result + owner.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + descriptor.hashCode();
		result = 31 * result + (itf ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "%s.%s%s,%s".formatted(owner.internalName(), name, descriptor.descriptor(), itf);
	}
}
