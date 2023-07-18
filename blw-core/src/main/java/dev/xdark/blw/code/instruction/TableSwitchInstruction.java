package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Label;
import dev.xdark.blw.code.Opcodes;

import java.util.List;

public final class TableSwitchInstruction implements SwitchInstruction {
	private final int min;
	private final Label defaultTarget;
	private final List<Label> targets;

	public TableSwitchInstruction(int min, Label defaultTarget, List<Label> targets) {
		this.min = min;
		this.defaultTarget = defaultTarget;
		this.targets = targets;
	}

	public int min() {
		return min;
	}

	@Override
	public int opcode() {
		return Opcodes.TABLESWITCH;
	}

	@Override
	public Label defaultTarget() {
		return defaultTarget;
	}

	@Override
	public List<Label> targets() {
		return targets;
	}

	@Override
	public Label select(int key) {
		key -= min();
		List<Label> targets;
		if (key >= 0 && key < (targets = targets()).size()) {
			return targets.get(key);
		}
		return defaultTarget();
	}
}
