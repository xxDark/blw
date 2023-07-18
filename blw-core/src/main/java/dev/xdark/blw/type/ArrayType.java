package dev.xdark.blw.type;

public final class ArrayType implements ObjectType {

	private final int dimensions;
	private final ClassType componentType;
	private String descriptor;

	ArrayType(ClassType componentType, String descriptor) {
		this.dimensions = nextDimension(componentType);
		this.componentType = componentType;
		this.descriptor = descriptor;
	}

	ArrayType(ClassType componentType) {
		this(componentType, null);
	}

	public int dimensions() {
		return dimensions;
	}

	public ClassType componentType() {
		return componentType;
	}

	@Override
	public String descriptor() {
		String descriptor = this.descriptor;
		if (descriptor == null) {
			descriptor = '[' + componentType.descriptor();
			this.descriptor = descriptor;
		}
		return descriptor;
	}

	@Override
	public String internalName() {
		return descriptor();
	}

	private static int nextDimension(Type type) {
		return type instanceof ArrayType arrayType ? arrayType.dimensions() + 1 : 1;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ArrayType that)) return false;

		if (dimensions != that.dimensions) return false;
		return componentType.equals(that.componentType);
	}

	@Override
	public int hashCode() {
		int result = dimensions;
		result = 31 * result + componentType.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return descriptor();
	}
}
