package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.JavaOpcodes;
import dev.xdark.blw.code.Label;

import java.util.Arrays;
import java.util.List;

public final class LookupSwitchInstruction implements SwitchInstruction {
	private final int[] keys;
	private final Label defaultTarget;
	private final List<Label> targets;

	public LookupSwitchInstruction(int[] keys, Label defaultTarget, List<Label> targets) {
		this.keys = keys;
		this.defaultTarget = defaultTarget;
		this.targets = targets;
	}

	public int[] keys() {
		return keys;
	}

	@Override
	public int opcode() {
		return JavaOpcodes.LOOKUPSWITCH;
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
		int idx = Arrays.binarySearch(keys(), key);
		return idx < 0 ? defaultTarget() : targets().get(idx);
	}
}
