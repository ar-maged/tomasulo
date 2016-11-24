package tomasulo.action;

import tomasulo.instructions.*;  

public class NandFunctionalUnit {
	private int numberOfcycles; 
	private int result;

	public NandFunctionalUnit(int cycles)
	{ 
	   this.numberOfcycles = cycles;   	
	}
	public void execute(Instruction instruction) {
     if (instruction.getName().equals(InstructionName.NAND)) { 
			
	  if((instruction.getSourceRegister1().equals(1)) && (instruction.getSourceRegister2().equals(1)))
		{
				
			this.result=0;
		}
			
	  else 
	    {
		   this.result=1;
	    }
			
		}
		
	}
	public int getResult() {
		return result;
	} 
	public int getNumberOfcycles() {
		return numberOfcycles;
	} 
}
