package tomasulo.configuration.action;

public class FunctionalUnitConfig {

	private int unitsCount;
	private int executionCycles;

	public FunctionalUnitConfig() {

	}

	public FunctionalUnitConfig(int unitsCount, int executionCycles) {
		this.unitsCount = unitsCount;
		this.executionCycles = executionCycles;
	}

	public int getUnitsCount() {
		return unitsCount;
	}

	public void setUnitsCount(int unitsCount) {
		this.unitsCount = unitsCount;
	}

	public int getExecutionCycles() {
		return executionCycles;
	}

	public void setExecutionCycles(int executionCycles) {
		this.executionCycles = executionCycles;
	}

}
