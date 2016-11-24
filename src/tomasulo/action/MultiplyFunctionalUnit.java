package tomasulo.action;
 

import tomasulo.instructions.*;  

public class MultiplyFunctionalUnit {
	private int numberOfcycles; 
	private int result;

	public MultiplyFunctionalUnit(int cycles)
	{ 
	   this.numberOfcycles = cycles;   	
	}
	public void execute(Instruction instruction) {
		if (instruction.getName().equals(InstructionName.MULT)) {
			this.result = instruction.getSourceRegister1() * instruction.getSourceRegister2();
			
			
		}
		
	}
	public int getResult() {
		return result;
	}
	public int getNumberOfcycles() {
		return numberOfcycles;
	} 
	
	
}
