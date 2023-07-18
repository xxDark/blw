package dev.xdark.blw.simulation;

public interface Simulation<E extends ExecutionEngine, M> {

	void execute(E engine, M method);
}
