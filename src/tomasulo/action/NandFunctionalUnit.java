package tomasulo.action;

import tomasulo.instructions.*;  

public class NandFunctionalUnit extends FunctionalUnit {
	private int numberOfcycles; 
	private int result;

	public NandFunctionalUnit(int cycles)
	{ 
	   this.numberOfcycles = cycles;   	
	}
	public void execute(Instruction instruction) {
     if (instruction.getName().equals(InstructionName.NAND)) { 
			
    	 this.result = ~(instruction.getSourceRegister1() & instruction.getSourceRegister2());
     }
		
	}
	public int getResult() {
		return result;
	} 
	public int getNumberOfcycles() {
		return numberOfcycles;
	} 
}
