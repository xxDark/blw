package dev.xdark.blw.simulation;

import dev.xdark.blw.classfile.Method;
import dev.xdark.blw.code.Code;
import dev.xdark.blw.code.CodeElement;
import dev.xdark.blw.code.CodeWalker;
import dev.xdark.blw.code.Label;
import dev.xdark.blw.code.Instruction;

import java.util.Objects;

public final class StraightforwardSimulation implements Simulation<ExecutionEngine, Method> {

	@Override
	public void execute(ExecutionEngine engine, Method method) {
		Code code = Objects.requireNonNull(method.code(), "method.code()");
		CodeWalker walker = code.walker();
		while (true) {
			walker.advance();
			CodeElement element = walker.element();
			if (element == null) {
				return;
			}
			if (element instanceof Label) {
				engine.label((Label) element);
			} else {
				ExecutionEngines.execute(engine, (Instruction) element);
			}
		}
	}
}
