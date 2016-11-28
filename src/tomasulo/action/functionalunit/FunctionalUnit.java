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
		this.done = (this.executionCycles == ++this.cyclesSpanned);
	}

	public int getCyclesSpanned() {
		return cyclesSpanned;
	}

	
	
	public boolean isDone() {
		return done;
	}

	public void execute(InstructionName operation, Integer Vj, Integer Vk, Integer addressOrImmediateValue){}

	public void clear(){
		this.cyclesSpanned = 0;
		this.done = false;
		this.result = 0;
	}
}
