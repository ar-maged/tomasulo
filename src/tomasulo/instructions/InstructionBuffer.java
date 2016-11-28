package tomasulo.instructions;

import tomasulo.exceptions.InstructionBufferOverflowException;

import java.util.ArrayList;


public class InstructionBuffer {

    private ArrayList<Instruction> instructionBuffer;
    private int maxSize;

    public InstructionBuffer(int maxSize) {
        this.instructionBuffer = new ArrayList<Instruction>();
        this.maxSize = maxSize;
    }

    public void insertInstructions(ArrayList<Instruction> instructions) {
        for (int i = 0; i < instructions.size(); i++) {
            if (this.instructionBuffer.size() < this.maxSize)
                instructionBuffer.add(instructions.get(i));
            else
                throw new InstructionBufferOverflowException();
        }
    }

    public Instruction removeFirstInstruction() {
        if(this.instructionBuffer.size() > 0)
            return instructionBuffer.remove(0);
        else
            return null;
    }

    public Instruction readFirstInstruction() {
        if(this.instructionBuffer.size() > 0)
            return instructionBuffer.get(0);
        else
            return null;
    }

    public boolean isEmpty(){
        return this.instructionBuffer.isEmpty();
    }

	public ArrayList<Instruction> getInstructionBuffer() {
		return instructionBuffer;
	}
    
    

}
