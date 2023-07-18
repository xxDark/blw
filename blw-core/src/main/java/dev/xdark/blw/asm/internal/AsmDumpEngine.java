package dev.xdark.blw.asm.internal;

import dev.xdark.blw.code.Label;
import dev.xdark.blw.code.instruction.AllocateInstruction;
import dev.xdark.blw.code.instruction.CheckCastInstruction;
import dev.xdark.blw.code.instruction.ConditionalJumpInstruction;
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
import dev.xdark.blw.code.instruction.ConstantInstruction;
import dev.xdark.blw.constant.Constant;
import dev.xdark.blw.constant.OfType;
import dev.xdark.blw.constant.OfString;
import dev.xdark.blw.constant.OfMethodHandle;
import dev.xdark.blw.constant.OfDynamic;
import dev.xdark.blw.constant.OfFloat;
import dev.xdark.blw.constant.OfDouble;
import dev.xdark.blw.constant.OfLong;
import dev.xdark.blw.constant.OfInt;
import dev.xdark.blw.simulation.ExecutionEngine;
import dev.xdark.blw.type.ArrayType;
import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.type.ObjectType;
import dev.xdark.blw.type.PrimitiveType;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static dev.xdark.blw.code.Opcodes.*;

final class AsmDumpEngine implements ExecutionEngine {
	private final LabelMapping labelMapping;
	private final MethodVisitor mv;

	AsmDumpEngine(LabelMapping labelMapping, MethodVisitor mv) {
		this.labelMapping = labelMapping;
		this.mv = mv;
	}

	@Override
	public void label(Label label) {
		MethodVisitor mv = this.mv;
		org.objectweb.asm.Label l = labelMapping.getLabel(label);
		mv.visitLabel(l);
		int line = label.lineNumber();
		if (line != Label.UNSET) {
			mv.visitLineNumber(line, l);
		}
	}

	@Override
	public void execute(SimpleInstruction instruction) {
		mv.visitInsn(instruction.opcode());
	}

	@Override
	public void execute(ConstantInstruction<?> instruction) {
		MethodVisitor mv = this.mv;
		Constant constant = instruction.constant();
		// It is faster if we do switch on opcode
		switch (instruction.opcode()) {
			case STRING_CONSTANT -> mv.visitLdcInsn(((OfString) constant).value());
			case METHOD_HANDLE_CONSTANT -> mv.visitLdcInsn(Util.unwrapMethodHandle(((OfMethodHandle) constant).value()));
			case DYNAMIC_CONSTANT -> mv.visitLdcInsn(Util.unwrapConstantDynamic(((OfDynamic) constant).value()));
			case LONG_CONSTANT -> mv.visitLdcInsn(((OfLong) constant).value());
			case DOUBLE_CONSTANT -> mv.visitLdcInsn(((OfDouble) constant).value());
			case INT_CONSTANT -> mv.visitLdcInsn(((OfInt) constant).value());
			case FLOAT_CONSTANT -> mv.visitLdcInsn(((OfFloat) constant).value());
			case TYPE_CONSTANT -> mv.visitLdcInsn(Util.unwrapType(((OfType) constant).value()));
		}
	}

	@Override
	public void execute(VarInstruction instruction) {
		mv.visitVarInsn(instruction.opcode(), instruction.variableIndex());
	}

	@Override
	public void execute(LookupSwitchInstruction instruction) {
		LabelMapping mapping = labelMapping;
		mv.visitLookupSwitchInsn(
				mapping.getLabel(instruction.defaultTarget()),
				instruction.keys(),
				instruction.targets().stream().map(mapping::getLabel).toArray(org.objectweb.asm.Label[]::new)
		);
	}

	@Override
	public void execute(TableSwitchInstruction instruction) {
		LabelMapping mapping = labelMapping;
		int min = instruction.min();
		mv.visitTableSwitchInsn(
				min,
				min + instruction.targets().size() - 1,
				mapping.getLabel(instruction.defaultTarget()),
				instruction.targets().stream().map(mapping::getLabel).toArray(org.objectweb.asm.Label[]::new)
		);
	}

	@Override
	public void execute(InstanceofInstruction instruction) {
		mv.visitTypeInsn(Opcodes.INSTANCEOF, instruction.type().internalName());
	}

	@Override
	public void execute(CheckCastInstruction instruction) {
		mv.visitTypeInsn(Opcodes.CHECKCAST, instruction.type().internalName());
	}

	@Override
	public void execute(AllocateInstruction instruction) {
		MethodVisitor mv = this.mv;
		ObjectType type = instruction.type();
		if (type instanceof InstanceType instance) {
			mv.visitTypeInsn(Opcodes.NEW, instance.internalName());
		} else {
			ArrayType arrayType = (ArrayType) type;
			String descriptor = arrayType.descriptor();
			int dimensions = arrayType.dimensions();
			if (dimensions == 1) {
				ClassType component = arrayType.componentType();
				if (!(component instanceof PrimitiveType primitiveType)) {
					mv.visitTypeInsn(Opcodes.ANEWARRAY, descriptor);
				} else {
					mv.visitIntInsn(Opcodes.NEWARRAY, primitiveType.kind());
				}
			} else {
				mv.visitMultiANewArrayInsn(descriptor, dimensions);
			}
		}
	}

	@Override
	public void execute(MethodInstruction instruction) {
		mv.visitMethodInsn(instruction.opcode(), instruction.owner().internalName(), instruction.name(), instruction.type().descriptor(), instruction.isInterface());
	}

	@Override
	public void execute(FieldInstruction instruction) {
		mv.visitFieldInsn(instruction.opcode(), instruction.owner().internalName(), instruction.name(), instruction.type().descriptor());
	}

	@Override
	public void execute(InvokeDynamicInstruction instruction) {
		mv.visitInvokeDynamicInsn(
				instruction.name(),
				instruction.type().descriptor(),
				Util.unwrapMethodHandle(instruction.bootstrapHandle()),
				instruction.args().stream().map(Util::unwrapConstant).toArray()
		);
	}

	@Override
	public void execute(ImmediateJumpInstruction instruction) {
		mv.visitJumpInsn(instruction.opcode(), labelMapping.getLabel(instruction.target()));
	}

	@Override
	public void execute(ConditionalJumpInstruction instruction) {
		mv.visitJumpInsn(instruction.opcode(), labelMapping.getLabel(instruction.target()));
	}

	@Override
	public void execute(VariableIncrementInstruction instruction) {
		mv.visitIincInsn(instruction.variableIndex(), instruction.incrementBy());
	}

	@Override
	public void execute(Instruction instruction) {
		throw new IllegalStateException("Unknown instruction %s".formatted(instruction));
	}
}
