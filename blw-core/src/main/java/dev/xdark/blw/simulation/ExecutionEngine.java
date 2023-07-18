package dev.xdark.blw.simulation;

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

public interface ExecutionEngine {

	void label(Label label);

	void execute(SimpleInstruction instruction);

	void execute(ConstantInstruction<?> instruction);

	void execute(VarInstruction instruction);

	void execute(LookupSwitchInstruction instruction);

	void execute(TableSwitchInstruction instruction);

	void execute(InstanceofInstruction instruction);

	void execute(CheckCastInstruction instruction);

	void execute(AllocateInstruction instruction);

	void execute(MethodInstruction instruction);

	void execute(FieldInstruction instruction);

	void execute(InvokeDynamicInstruction instruction);

	void execute(ImmediateJumpInstruction instruction);

	void execute(ConditionalJumpInstruction instruction);

	void execute(VariableIncrementInstruction instruction);

	void execute(Instruction instruction);
}
