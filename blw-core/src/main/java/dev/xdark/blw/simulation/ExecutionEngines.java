package dev.xdark.blw.simulation;

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

public final class ExecutionEngines {

	private ExecutionEngines() {
	}

	public static void execute(ExecutionEngine engine, Instruction instruction) {
		switch (instruction) {
			case SimpleInstruction sim -> engine.execute(sim);
			case LookupSwitchInstruction lsw -> engine.execute(lsw);
			case TableSwitchInstruction tsw -> engine.execute(tsw);
			case ConditionalJumpInstruction cj -> engine.execute(cj);
			case ImmediateJumpInstruction ij -> engine.execute(ij);
			case VarInstruction var -> engine.execute(var);
			case VariableIncrementInstruction vi -> engine.execute(vi);
			case MethodInstruction m -> engine.execute(m);
			case FieldInstruction f -> engine.execute(f);
			case InvokeDynamicInstruction indy -> engine.execute(indy);
			case AllocateInstruction a -> engine.execute(a);
			case CheckCastInstruction cc -> engine.execute(cc);
			case InstanceofInstruction i -> engine.execute(i);
			case ConstantInstruction<?> c -> engine.execute(c);
			default -> engine.execute(instruction);
		}
	}
}
