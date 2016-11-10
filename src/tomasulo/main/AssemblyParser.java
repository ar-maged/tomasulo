package tomasulo.main;

import java.util.ArrayList;

public class AssemblyParser {

	
	
	public ArrayList parseInstruction(String s)
	{
		ArrayList instructionDecoded = new ArrayList();
		
		String[]  inst = s.split(" ");
		instructionDecoded.add(inst[0]); //the instruction 
		
		String [] regs = inst[1].split(", "); //the registers
		for(int i = 0; i<regs.length; i++)
		{
			instructionDecoded.add(regs[i]);
		}
		
		return instructionDecoded;
	}
}
