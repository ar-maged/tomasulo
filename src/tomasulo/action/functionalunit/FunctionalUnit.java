package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.Instruction;
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
	
	public void execute(InstructionName operation, int Vj, int Vk, int addressOrImmediateValue){
		
		
	}

}
