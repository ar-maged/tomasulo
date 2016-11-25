package tomasulo.action;
 

import tomasulo.instructions.*;  

public class LoadStoreUnit extends FunctionalUnit {
   
	
	private int numberOfcycles; 
	private int result;

	public LoadStoreUnit(int cycles)
	{ 
	   this.numberOfcycles = cycles;   	
	}
	public void execute(Instruction instruction) {
		if ((instruction.getName().equals(InstructionName.LW)) || ((instruction.getName().equals(InstructionName.SW)))) {
			this.result = instruction.getSourceRegister1() + instruction.getImmediate();
			
		}
		
	}
	public int getResult() {
		return result;
	} 
	public int getNumberOfcycles() {
		return numberOfcycles;
	} 
}