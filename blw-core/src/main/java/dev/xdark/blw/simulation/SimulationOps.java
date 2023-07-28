package dev.xdark.blw.simulation;

import static dev.xdark.blw.code.JavaOpcodes.*;

@SuppressWarnings("DuplicatedCode")
public final class SimulationOps {

	private SimulationOps() {
	}

	public static int compare(double b, double a, int ifNaN) {
		if (Double.isNaN(a) || Double.isNaN(b)) {
			return ifNaN;
		}
		return Double.compare(a, b);
	}

	public static int compare(float b, float a, int ifNaN) {
		if (Float.isNaN(a) || Float.isNaN(b)) {
			return ifNaN;
		}
		return Float.compare(a, b);
	}

	public static int compare(long b, long a) {
		return Long.compare(a, b);
	}

	public static long execute(int opcode, long b, long a) {
		return switch (opcode) {
			case LADD -> a + b;
			case LSUB -> a - b;
			case LMUL -> a * b;
			case LDIV -> a / b;
			case LREM -> a % b;
			case LAND -> a & b;
			case LOR -> a | b;
			case LXOR -> a ^ b;
			default -> throw new IllegalStateException("Unexpected value: " + opcode);
		};
	}

	public static long execute(int opcode, int b, long a) {
		return switch (opcode) {
			case LSHL -> a << b;
			case LSHR -> a >> b;
			case LUSHR -> a >>> b;
			default -> throw new IllegalStateException("Unexpected value: " + opcode);
		};
	}

	public static double execute(int opcode, double b, double a) {
		return switch (opcode) {
			case DADD -> a + b;
			case DSUB -> a - b;
			case DMUL -> a * b;
			case DDIV -> a / b;
			case DREM -> a % b;
			default -> throw new IllegalStateException("Unexpected value: " + opcode);
		};
	}

	public static int execute(int opcode, int b, int a) {
		return switch (opcode) {
			case LADD -> a + b;
			case LSUB -> a - b;
			case LMUL -> a * b;
			case LDIV -> a / b;
			case LREM -> a % b;
			case ISHL -> a << b;
			case ISHR -> a >> b;
			case IUSHR -> a >>> b;
			case IAND -> a & b;
			case IOR -> a | b;
			case IXOR -> a ^ b;
			default -> throw new IllegalStateException("Unexpected value: " + opcode);
		};
	}

	public static float execute(int opcode, float b, float a) {
		return switch (opcode) {
			case FADD -> a + b;
			case FSUB -> a - b;
			case FMUL -> a * b;
			case FDIV -> a / b;
			case FREM -> a % b;
			default -> throw new IllegalStateException("Unexpected value: " + opcode);
		};
	}

	public static boolean test(int opcode, int value) {
		return switch (opcode) {
			case IFEQ -> value == 0;
			case IFNE -> value != 0;
			case IFLT -> value < 0;
			case IFGE -> value >= 0;
			case IFGT -> value > 0;
			case IFLE -> value <= 0;
			default -> throw new IllegalStateException("Unexpected value: " + opcode);
		};
	}

	public static boolean test(int opcode, int b, int a) {
		return switch (opcode) {
			case IF_ICMPEQ -> a == b;
			case IF_ICMPNE -> a != b;
			case IF_ICMPLT -> a < b;
			case IF_ICMPGE -> a >= b;
			case IF_ICMPGT -> a > b;
			case IF_ICMPLE -> a <= b;
			default -> throw new IllegalStateException("Unexpected value: " + opcode);
		};
	}
}
