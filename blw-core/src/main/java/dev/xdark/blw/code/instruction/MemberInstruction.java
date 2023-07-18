package dev.xdark.blw.code.instruction;

import dev.xdark.blw.code.Instruction;
import dev.xdark.blw.type.ObjectType;
import dev.xdark.blw.type.Type;

public interface MemberInstruction extends Instruction {

	ObjectType owner();

	String name();

	Type type();
}
