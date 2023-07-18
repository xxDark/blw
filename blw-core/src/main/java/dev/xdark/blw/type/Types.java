package dev.xdark.blw.type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static dev.xdark.blw.type.PrimitiveKind.T_BOOLEAN;
import static dev.xdark.blw.type.PrimitiveKind.T_BYTE;
import static dev.xdark.blw.type.PrimitiveKind.T_CHAR;
import static dev.xdark.blw.type.PrimitiveKind.T_DOUBLE;
import static dev.xdark.blw.type.PrimitiveKind.T_FLOAT;
import static dev.xdark.blw.type.PrimitiveKind.T_INT;
import static dev.xdark.blw.type.PrimitiveKind.T_LONG;
import static dev.xdark.blw.type.PrimitiveKind.T_SHORT;
import static dev.xdark.blw.type.PrimitiveKind.T_VOID;

public final class Types {
	public static final PrimitiveType
			VOID = createPrimitiveType("V", T_VOID, "void"),
			LONG = createPrimitiveType("J", T_LONG, "long"),
			DOUBLE = createPrimitiveType("D", T_DOUBLE, "double"),
			INT = createPrimitiveType("I", T_INT, "int"),
			FLOAT = createPrimitiveType("F", T_FLOAT, "float"),
			CHAR = createPrimitiveType("C", T_CHAR, "char"),
			SHORT = createPrimitiveType("S", T_SHORT, "short"),
			BYTE = createPrimitiveType("B", T_BYTE, "byte"),
			BOOLEAN = createPrimitiveType("Z", T_BOOLEAN, "boolean");
	public static final int CATEGORY2 = 2;
	public static final int CATEGORY1 = 1;

	private Types() {
	}

	public static InstanceType instanceTypeFromDescriptor(String descriptor) {
		return new InstanceType(descriptor, descriptor.substring(1, descriptor.length() - 1));
	}

	public static ObjectType objectTypeFromInternalName(String internalName) {
		if (internalName.charAt(0) == '[') {
			return arrayTypeFromDescriptor(internalName);
		} else {
			return instanceTypeFromInternalName(internalName);
		}
	}

	public static InstanceType instanceTypeFromInternalName(String internalName) {
		return new InstanceType('L' + internalName + ';', internalName);
	}

	public static ArrayType arrayTypeFromDescriptor(String descriptor) {
		TypeReader typeReader = new TypeReader(descriptor);
		Type type = typeReader.read();
		if (!(type instanceof ArrayType at)) {
			throw new IllegalStateException("Expected ArrayType");
		}
		return at;
	}

	public static ArrayType arrayType(ClassType component) {
		return new ArrayType(component);
	}

	public static MethodType methodType(String descriptor) {
		TypeReader typeReader = new TypeReader(descriptor);
		Type type = typeReader.read();
		if (!(type instanceof MethodType mt)) {
			throw new IllegalStateException("Expected MethodType");
		}
		return mt;
	}

	public static MethodType methodType(ClassType returnType, List<ClassType> parameterTypes) {
		return new MethodType(returnType, parameterTypes);
	}

	public static MethodType methodType(ClassType returnType, ClassType... parameterTypes) {
		return methodType(returnType, List.of(parameterTypes));
	}

	public static MethodType methodType(Class<?> returnType, Stream<Class<?>> parameterTypes) {
		return methodType(type(returnType), parameterTypes.map(Types::type).toList());
	}

	public static MethodType methodType(Class<?> returnType, List<Class<?>> parameterTypes) {
		return methodType(returnType, parameterTypes.stream());
	}

	public static MethodType methodType(Class<?> returnType, Class<?>... parameterTypes) {
		return methodType(returnType, Arrays.stream(parameterTypes));
	}

	public static InstanceType instanceType(Class<?> klass) {
		if (klass.isPrimitive() || klass.isArray()) {
			throw new IllegalArgumentException("Must be an instance " + klass);
		}
		return instanceTypeFromInternalName(Types.internalName(klass.getName()));
	}

	public static ArrayType arrayType(Class<?> klass) {
		if (!klass.isArray()) {
			throw new IllegalArgumentException("Must be an array " + klass);
		}
		return arrayTypeFromDescriptor(Types.internalName(klass.getName()));
	}

	public static InstanceType instanceTypeFromExternalName(String externalName) {
		return instanceTypeFromInternalName(internalName(externalName));
	}

	public static ArrayType arrayTypeFromExternalName(String externalName) {
		return arrayTypeFromDescriptor(internalName(externalName));
	}

	public static PrimitiveType primitiveOfKind(int kind) {
		return switch (kind) {
			case T_BOOLEAN -> BOOLEAN;
			case T_CHAR -> CHAR;
			case T_FLOAT -> FLOAT;
			case T_DOUBLE -> DOUBLE;
			case T_BYTE -> BYTE;
			case T_SHORT -> SHORT;
			case T_INT -> INT;
			case T_LONG -> LONG;
			case T_VOID -> VOID;
			default -> throw new IllegalStateException("Unexpected value: " + kind);
		};
	}

	public static ClassType type(Class<?> type) {
		if (type.isArray()) {
			return arrayType(type);
		}
		if (!type.isPrimitive()) {
			return instanceType(type);
		}
		String name = type.getName();
		return switch (name.charAt(0)) {
			case 'v' -> VOID;
			case 'l' -> LONG;
			case 'd' -> DOUBLE;
			case 'i' -> INT;
			case 'f' -> FLOAT;
			case 'c' -> CHAR;
			case 's' -> SHORT;
			case 'b' -> name.charAt(1) == 'o' ? BOOLEAN : BYTE;
			default -> throw new IllegalStateException("Unexpected value: " + name);
		};
	}

	public static int category(ClassType type) {
		if (type instanceof PrimitiveType pt) {
			int kind = pt.kind();
			if (kind == PrimitiveKind.T_LONG || kind == PrimitiveKind.T_DOUBLE) {
				return CATEGORY2;
			}
		}
		return CATEGORY1;
	}

	public static int parametersSize(MethodType type) {
		return type.parameterTypes().stream().mapToInt(Types::category).sum();
	}

	public static int sizeof(PrimitiveType type) {
		return switch (type.kind()) {
			case T_BOOLEAN, T_BYTE -> Byte.BYTES;
			case T_CHAR -> Character.BYTES;
			case T_FLOAT -> Float.BYTES;
			case T_DOUBLE -> Double.BYTES;
			case T_SHORT -> Short.BYTES;
			case T_INT -> Integer.BYTES;
			case T_LONG -> Long.BYTES;
			default -> throw new IllegalStateException("Unexpected value: " + type.kind());
		};
	}

	public static String internalName(String externalName) {
		return externalName.replace('.', '/');
	}

	public static String externalName(String internalName) {
		return internalName.replace('/', '.');
	}

	public static Class<?> resolve(ClassType type, ClassLoader classLoader) throws ClassNotFoundException {
		int dimensions = 0;
		while (type instanceof ArrayType arrayType) {
			dimensions++;
			type = arrayType.componentType();
		}
		Class<?> c;
		if (type instanceof PrimitiveType pt) {
			c = switch (pt.kind()) {
				case T_BOOLEAN -> boolean.class;
				case T_CHAR -> char.class;
				case T_FLOAT -> float.class;
				case T_DOUBLE -> double.class;
				case T_BYTE -> byte.class;
				case T_SHORT -> short.class;
				case T_INT -> int.class;
				case T_LONG -> long.class;
				case T_VOID -> void.class;
				default -> throw new IllegalStateException("Unexpected value: " + pt.kind());
			};
		} else {
			String externalName = ((InstanceType) type).externalName();
			c = Class.forName(externalName, false, classLoader);
		}
		while (dimensions-- != 0) {
			c = c.arrayType();
		}
		return c;
	}

	private static PrimitiveType createPrimitiveType(String descriptor, int kind, String name) {
		return new PrimitiveType(descriptor, kind, name);
	}
}
