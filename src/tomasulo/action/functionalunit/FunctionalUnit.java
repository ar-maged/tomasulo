package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;

public class FunctionalUnit {

	int executionCycles;
	int cyclesSpanned;
	int result;

	public FunctionalUnit(FunctionalUnitConfig config) {
		this.executionCycles = config.getExecutionCycles();
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

}
