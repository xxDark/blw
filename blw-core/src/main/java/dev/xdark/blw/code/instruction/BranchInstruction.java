package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Instruction;
import dev.xdark.blw.code.Label;

import java.util.stream.Stream;

public interface BranchInstruction extends Instruction {

	Stream<Label> allTargets();
}
