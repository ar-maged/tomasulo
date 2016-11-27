package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.InstructionName;

public class FunctionalUnit {

	int executionCycles;
	int cyclesSpanned;
	int result; 
    boolean done; 
    
	public FunctionalUnit(FunctionalUnitConfig config) {
		this.executionCycles = config.getExecutionCycles();
		this.done=false;

	}

	public int getExecutionCycles() {
		return executionCycles;
	}

	public int getResult() {
		return result;
	}

	public void incrementCyclesSpanned() {
		this.cyclesSpanned++;
	}

	public int getCyclesSpanned() {
		return cyclesSpanned;
	}

	
	
	public boolean isDone() {
		return executionCycles == cyclesSpanned;
	}

	public void execute(InstructionName operation, Integer Vj, Integer Vk, Integer addressOrImmediateValue){}
}
