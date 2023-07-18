package dev.xdark.blw.type;

import java.util.List;

public final class 	MethodType implements Type {

	private final ClassType returnType;
	private final List<ClassType> parameterTypes;
	private String descriptor;

	MethodType(ClassType returnType, List<ClassType> parameterTypes) {
		this.returnType = returnType;
		this.parameterTypes = parameterTypes;
	}

	public ClassType returnType() {
		return returnType;
	}

	public List<ClassType> parameterTypes() {
		return parameterTypes;
	}

	@Override
	public String descriptor() {
		String descriptor = this.descriptor;
		if (descriptor == null) {
			StringBuilder builder = new StringBuilder();
			builder.append('(');
			for (ClassType type : parameterTypes) {
				builder.append(type.descriptor());
			}
			descriptor = builder.append(')').append(returnType.descriptor()).toString();
			this.descriptor = descriptor;
		}
		return descriptor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MethodType that)) return false;

		if (!returnType.equals(that.returnType)) return false;
		return parameterTypes.equals(that.parameterTypes);
	}

	@Override
	public int hashCode() {
		int result = returnType.hashCode();
		result = 31 * result + parameterTypes.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return descriptor();
	}
}
