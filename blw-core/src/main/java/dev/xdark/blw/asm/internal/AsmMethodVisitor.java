package dev.xdark.blw.asm.internal;

import dev.xdark.blw.classfile.MethodBuilder;
import dev.xdark.blw.classfile.attribute.generic.GenericParameter;
import dev.xdark.blw.code.CodeBuilder;
import dev.xdark.blw.code.CodeListBuilder;
import dev.xdark.blw.code.TryCatchBlock;
import dev.xdark.blw.code.generic.GenericLabel;
import dev.xdark.blw.code.attribute.generic.GenericLocal;
import dev.xdark.blw.code.instruction.AllocateInstruction;
import dev.xdark.blw.code.instruction.CheckCastInstruction;
import dev.xdark.blw.code.instruction.ConditionalJumpInstruction;
import dev.xdark.blw.code.instruction.ConstantInstruction;
import dev.xdark.blw.code.instruction.FieldInstruction;
import dev.xdark.blw.code.instruction.ImmediateJumpInstruction;
import dev.xdark.blw.code.instruction.InstanceofInstruction;
import dev.xdark.blw.code.Instruction;
import dev.xdark.blw.code.instruction.InvokeDynamicInstruction;
import dev.xdark.blw.code.instruction.LookupSwitchInstruction;
import dev.xdark.blw.code.instruction.MethodInstruction;
import dev.xdark.blw.code.instruction.PrimitiveConversionInstruction;
import dev.xdark.blw.code.instruction.SimpleInstruction;
import dev.xdark.blw.code.instruction.TableSwitchInstruction;
import dev.xdark.blw.code.instruction.VarInstruction;
import dev.xdark.blw.code.instruction.VariableIncrementInstruction;
import dev.xdark.blw.constant.OfDouble;
import dev.xdark.blw.constant.OfFloat;
import dev.xdark.blw.constant.OfInt;
import dev.xdark.blw.constant.OfLong;
import dev.xdark.blw.type.ObjectType;
import dev.xdark.blw.type.TypeReader;
import dev.xdark.blw.type.Types;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.Arrays;

import static org.objectweb.asm.Opcodes.*;

final class AsmMethodVisitor extends MethodVisitor {
	private final MethodBuilder method;
	private final CodeBuilder code;
	private final CodeListBuilder content;

	AsmMethodVisitor(MethodBuilder method, boolean hasCode) {
		super(ASM9);
		this.method = method;
		if (hasCode) {
			CodeBuilder code = method.code();
			this.code = code;
			content = code != null ? code.codeList() : null;
		} else {
			code = null;
			content = null;
		}
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		var code = this.code;
		if (code != null) {
			code.maxStack(maxStack).maxLocals(maxLocals);
		}
	}

	@Override
	public void visitInsn(int opcode) {
		if (content == null) return;
		Instruction instruction = switch (opcode) {
			case ICONST_M1,
					ICONST_0,
					ICONST_1,
					ICONST_2,
					ICONST_3,
					ICONST_4,
					ICONST_5 -> new ConstantInstruction.Int(new OfInt(opcode - ICONST_0));
			case LCONST_0, LCONST_1 ->
					new ConstantInstruction.Long(new OfLong(opcode - LCONST_0));
			case FCONST_0, FCONST_1, FCONST_2 ->
					new ConstantInstruction.Float(new OfFloat(opcode - FCONST_0));
			case DCONST_0, DCONST_1 ->
					new ConstantInstruction.Double(new OfDouble(opcode - DCONST_0));
			default -> switch (opcode) {
				case I2L -> new PrimitiveConversionInstruction(Types.INT, Types.LONG);
				case I2F -> new PrimitiveConversionInstruction(Types.INT, Types.FLOAT);
				case I2D -> new PrimitiveConversionInstruction(Types.INT, Types.DOUBLE);
				case L2I -> new PrimitiveConversionInstruction(Types.LONG, Types.INT);
				case L2F -> new PrimitiveConversionInstruction(Types.LONG, Types.FLOAT);
				case L2D -> new PrimitiveConversionInstruction(Types.LONG, Types.DOUBLE);
				case F2I -> new PrimitiveConversionInstruction(Types.FLOAT, Types.INT);
				case F2L -> new PrimitiveConversionInstruction(Types.FLOAT, Types.LONG);
				case F2D -> new PrimitiveConversionInstruction(Types.FLOAT, Types.DOUBLE);
				case D2I -> new PrimitiveConversionInstruction(Types.DOUBLE, Types.INT);
				case D2L -> new PrimitiveConversionInstruction(Types.DOUBLE, Types.LONG);
				case D2F -> new PrimitiveConversionInstruction(Types.DOUBLE, Types.FLOAT);
				case I2B -> new PrimitiveConversionInstruction(Types.INT, Types.BYTE);
				case I2C -> new PrimitiveConversionInstruction(Types.INT, Types.CHAR);
				case I2S -> new PrimitiveConversionInstruction(Types.INT, Types.SHORT);
				default -> new SimpleInstruction(opcode);
			};
		};
		add(instruction);
	}

	@Override
	public void visitLdcInsn(Object value) {
		if (content == null) return;
		add(ConstantInstruction.wrap(Util.wrapConstant(value)));
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		if (content == null) return;
		add(new LookupSwitchInstruction(
				keys,
				getLabel(dflt),
				Arrays.stream(labels).map(this::getLabel).toList()
		));
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
		if (content == null) return;
		add(new TableSwitchInstruction(
				min,
				getLabel(dflt),
				Arrays.stream(labels).map(this::getLabel).toList()
		));
	}

	@Override
	public void visitVarInsn(int opcode, int varIndex) {
		if (content == null) return;
		add(new VarInstruction(opcode, varIndex));
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		if (content == null) return;
		ObjectType objectType = Types.objectTypeFromInternalName(type);
		Instruction instruction = switch (opcode) {
			case CHECKCAST -> new CheckCastInstruction(objectType);
			case INSTANCEOF -> new InstanceofInstruction(objectType);
			case NEW -> new AllocateInstruction(objectType);
			case ANEWARRAY -> new AllocateInstruction(Types.arrayType(objectType));
			default -> throw new IllegalStateException("Unexpected value: " + opcode);
		};
		add(instruction);
	}

	@Override
	public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
		if (content == null) return;
		add(new AllocateInstruction(Types.arrayTypeFromDescriptor(descriptor)));
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		if (content == null) return;
		Instruction instruction = switch (opcode) {
			case BIPUSH, SIPUSH -> new ConstantInstruction.Int(new OfInt(operand));
			case NEWARRAY -> new AllocateInstruction(Types.arrayType(Types.primitiveOfKind(operand)));
			default -> throw new IllegalStateException("Unexpected value: " + opcode);
		};
		add(instruction);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
		if (content == null) return;
		add(new MethodInstruction(opcode, Types.objectTypeFromInternalName(owner), name, Types.methodType(descriptor), isInterface));
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
		if (content == null) return;
		add(new FieldInstruction(opcode, Types.instanceTypeFromInternalName(owner), name, new TypeReader(descriptor).requireClassType()));
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
		if (content == null) return;
		add(new InvokeDynamicInstruction(
				name,
				new TypeReader(descriptor).required(),
				Util.wrapMethodHandle(bootstrapMethodHandle),
				Arrays.stream(bootstrapMethodArguments).map(Util::wrapConstant).toList()
		));
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		if (content == null) return;
		var l = getLabel(label);
		Instruction instruction;
		if (opcode == GOTO) {
			instruction = new ImmediateJumpInstruction(GOTO, l);
		} else {
			instruction = new ConditionalJumpInstruction(opcode, l);
		}
		add(instruction);
	}

	@Override
	public void visitIincInsn(int varIndex, int increment) {
		if (content == null) return;
		add(new VariableIncrementInstruction(varIndex, increment));
	}

	@Override
	public void visitLabel(Label label) {
		CodeListBuilder content = this.content;
		if (content == null) return;
		content.element(getLabel(label));
	}

	@Override
	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		if (code == null) return;
		code.tryCatchBlock(new TryCatchBlock(
				getLabel(start),
				getLabel(end),
				getLabel(handler),
				type == null ? null : Types.instanceTypeFromInternalName(type)));
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		if (content == null) return;
		getLabel(start).lineNumber(line);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		return Util.visitAnnotation(method, descriptor, visible);
	}

	@Override
	public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
		CodeBuilder c = code;
		if (c != null) {
			c.localVariable(new GenericLocal(getLabel(start), getLabel(end), index, name, new TypeReader(descriptor).requireClassType(), signature));
		}
	}

	@Override
	public void visitParameter(String name, int access) {
		method.parameter(new GenericParameter(access, name));
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		return new AnnotationDefaultCollector(method);
	}

	private dev.xdark.blw.code.Label getLabel(Label label) {
		Object info = label.info;
		if (info == null) {
			GenericLabel l = new GenericLabel();
			label.info = l;
			return l;
		}
		if (!(info instanceof GenericLabel l)) {
			throw new IllegalStateException("Label info is not GenericLabel");
		}
		return l;
	}

	private void add(Instruction instruction) {
		content.element(instruction);
	}
}
