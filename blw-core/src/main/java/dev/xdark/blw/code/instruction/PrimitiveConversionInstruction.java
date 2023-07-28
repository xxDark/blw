package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.ExtensionOpcodes;
import dev.xdark.blw.code.Instruction;
import dev.xdark.blw.type.PrimitiveType;

import static dev.xdark.blw.type.PrimitiveKind.*;

public final class PrimitiveConversionInstruction implements Instruction {

	private final PrimitiveType from, to;

	public PrimitiveConversionInstruction(PrimitiveType from, PrimitiveType to) {
		this.from = from;
		this.to = to;
	}

	public PrimitiveType from() {
		return from;
	}

	public PrimitiveType to() {
		return to;
	}

	public void accept(PrimitiveConversion conversion) {
		int from = this.from.kind();
		int to = this.to.kind();
		err:
		{
			if (from == T_VOID || to == T_VOID) {
				break err;
			}
			if (from == to) return;
			switch (from) {
				case T_INT, T_BYTE, T_CHAR, T_SHORT, T_BOOLEAN -> {
					switch (to) {
						case T_LONG -> conversion.i2l();
						case T_FLOAT -> conversion.i2f();
						case T_DOUBLE -> conversion.i2d();
						case T_BYTE -> conversion.i2b();
						case T_CHAR -> conversion.i2c();
						case T_SHORT -> conversion.i2s();
						default -> {
							break err;
						}
					}
				}
				case T_LONG -> {
					switch (to) {
						case T_INT -> conversion.l2i();
						case T_FLOAT -> conversion.l2f();
						case T_DOUBLE -> conversion.l2d();
						case T_CHAR -> {
							conversion.l2i();
							conversion.i2c();
						}
						case T_SHORT -> {
							conversion.l2i();
							conversion.i2s();
						}
						case T_BYTE, T_BOOLEAN -> {
							conversion.l2i();
							conversion.i2b();
						}
						default -> {
							break err;
						}
					}
				}
				case T_FLOAT -> {
					switch (to) {
						case T_INT -> conversion.f2i();
						case T_LONG -> conversion.f2l();
						case T_DOUBLE -> conversion.f2d();
						case T_CHAR -> {
							conversion.f2i();
							conversion.i2c();
						}
						case T_SHORT -> {
							conversion.f2i();
							conversion.i2s();
						}
						case T_BYTE, T_BOOLEAN -> {
							conversion.f2i();
							conversion.i2b();
						}
						default -> {
							break err;
						}
					}
				}
				case T_DOUBLE -> {
					switch (to) {
						case T_INT -> conversion.d2i();
						case T_LONG -> conversion.d2l();
						case T_FLOAT -> conversion.d2f();
						case T_CHAR -> {
							conversion.d2i();
							conversion.i2c();
						}
						case T_SHORT -> {
							conversion.d2i();
							conversion.i2s();
						}
						case T_BYTE, T_BOOLEAN -> {
							conversion.d2i();
							conversion.i2b();
						}
						default -> {
							break err;
						}
					}
				}
			}
			return;
		}
		throw new IllegalStateException("Cannot convert %s to %s".formatted(this.from, this.to));
	}

	@Override
	public int opcode() {
		return ExtensionOpcodes.PRIMITIVE_CONVERSION;
	}
}
