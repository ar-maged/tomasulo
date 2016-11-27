package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.InstructionName;

public class FunctionalUnit {

	int executionCycles;
	int cyclesSpanned;
	int result; 
	FunctionalUnitState state;

	public FunctionalUnit(FunctionalUnitConfig config) {
		this.executionCycles = config.getExecutionCycles();
		state = FunctionalUnitState.IDLE;
	}

	public int getExecutionCycles() {
		return executionCycles;
	}

	public int getResult() {
		return result;
	}

	public void incrementCyclesSpanned() {
		this.cyclesSpanned++;
		setState();
	}

	public int getCyclesSpanned() {
		return cyclesSpanned;
	}

	public FunctionalUnitState getState() {
		return state;
	}

	public void setState() {
		if (executionCycles == cyclesSpanned)
			this.state = FunctionalUnitState.DONE;
		else
			this.state = FunctionalUnitState.EXECUTING;
	}
	
	public void execute(InstructionName operation, int Vj, int Vk, int addressOrImmediateValue){}
}
