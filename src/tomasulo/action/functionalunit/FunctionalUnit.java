package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;

public class FunctionalUnit {

	int executionCycles;
	int cyclesSpanned;
	int result; 
	FunctionalUnitState state;

	public FunctionalUnit(FunctionalUnitConfig config) {
		this.executionCycles = config.getExecutionCycles();
		state = FunctionalUnitState.EXECUTING;
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

	public FunctionalUnitState getState() {
		return state;
	}

	public void setState(FunctionalUnitState state) {
		if (executionCycles == cyclesSpanned)
			this.state = FunctionalUnitState.DONE;
	}

}
