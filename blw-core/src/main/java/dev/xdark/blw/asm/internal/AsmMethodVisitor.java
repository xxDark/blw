package dev.xdark.blw.asm.internal;

import dev.xdark.blw.classfile.MethodBuilder;
import dev.xdark.blw.code.CodeBuilder;
import dev.xdark.blw.code.CodeListBuilder;
import dev.xdark.blw.code.TryCatchBlock;
import dev.xdark.blw.code.generic.GenericLabel;
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
import org.objectweb.asm.Opcodes;

import java.util.Arrays;

final class AsmMethodVisitor extends MethodVisitor {
	private final MethodBuilder method;
	private final CodeBuilder code;
	private final CodeListBuilder content;

	AsmMethodVisitor(MethodBuilder method, boolean hasCode) {
		super(Opcodes.ASM9);
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
			case Opcodes.ICONST_M1,
					Opcodes.ICONST_0,
					Opcodes.ICONST_1,
					Opcodes.ICONST_2,
					Opcodes.ICONST_3,
					Opcodes.ICONST_4,
					Opcodes.ICONST_5 -> new ConstantInstruction.Int(new OfInt(opcode - Opcodes.ICONST_0));
			case Opcodes.LCONST_0, Opcodes.LCONST_1 ->
					new ConstantInstruction.Long(new OfLong(opcode - Opcodes.LCONST_0));
			case Opcodes.FCONST_0, Opcodes.FCONST_1, Opcodes.FCONST_2 ->
					new ConstantInstruction.Float(new OfFloat(opcode - Opcodes.FCONST_0));
			case Opcodes.DCONST_0, Opcodes.DCONST_1 ->
					new ConstantInstruction.Double(new OfDouble(opcode - Opcodes.DCONST_0));
			default -> new SimpleInstruction(opcode);
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
			case Opcodes.CHECKCAST -> new CheckCastInstruction(objectType);
			case Opcodes.INSTANCEOF -> new InstanceofInstruction(objectType);
			case Opcodes.NEW -> new AllocateInstruction(objectType);
			case Opcodes.ANEWARRAY -> new AllocateInstruction(Types.arrayType(objectType));
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
			case Opcodes.BIPUSH, Opcodes.SIPUSH -> new ConstantInstruction.Int(new OfInt(opcode));
			case Opcodes.NEWARRAY -> new AllocateInstruction(Types.arrayType(Types.primitiveOfKind(operand)));
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
		if (opcode == Opcodes.GOTO) {
			instruction = new ImmediateJumpInstruction(Opcodes.GOTO, l);
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
