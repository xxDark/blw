package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Label;

import java.util.List;
import java.util.stream.Stream;

public sealed interface SwitchInstruction extends BranchInstruction permits LookupSwitchInstruction, TableSwitchInstruction {

	Label defaultTarget();

	List<Label> targets();

	Label select(int key);

	@Override
	default Stream<Label> allTargets() {
		return Stream.concat(Stream.of(defaultTarget()), targets().stream());
	}
}
