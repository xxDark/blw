package dev.xdark.blw.type;

public final class InstanceType implements ObjectType {

	private final String descriptor;
	private final String internalName;

	InstanceType(String descriptor, String internalName) {
		this.descriptor = descriptor;
		this.internalName = internalName;
	}

	@Override
	public String descriptor() {
		return descriptor;
	}

	@Override
	public String internalName() {
		return internalName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof InstanceType that)) return false;

		return descriptor.equals(that.descriptor);
	}

	@Override
	public int hashCode() {
		return descriptor.hashCode();
	}

	@Override
	public String toString() {
		return descriptor;
	}
}
