package tomasulo.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import tomasulo.instructions.Instruction;

public class MainMemory {

	public static int memoryCapacity = 64 * 1024;
	private Block[] blocks;
	private int blockSizeInBytes;

	public MainMemory(int blockSizeInBytes) {
		this.blockSizeInBytes = blockSizeInBytes;
		int numberOfRows = memoryCapacity / blockSizeInBytes;
		this.blocks = new Block[numberOfRows];
		for (int i = 0; i < numberOfRows; i++) {
			this.blocks[i] = new Block(blockSizeInBytes);
		}
	}

	private Stack<Instruction> convertInstructionsArrayToStack(ArrayList<Instruction> instructionsArray) {
		Stack<Instruction> instructionsStack = new Stack<>();
		Collections.reverse(instructionsArray);
		instructionsStack.addAll(instructionsArray);
		return instructionsStack;
	}

	public void readProgram(ArrayList<Instruction> instructions, int startAddressInBytes) {
		Stack<Instruction> instructionsStack = this.convertInstructionsArrayToStack(instructions);
		while (!instructionsStack.isEmpty()) {
			// Loop over number of instructions per block
			for (int i = 0; i < this.blockSizeInBytes / 2; i++) {
				if (!instructionsStack.isEmpty()) {
					this.blocks[startAddressInBytes / this.blockSizeInBytes].addData(instructionsStack.pop(), i);
				}
			}
			startAddressInBytes += this.blockSizeInBytes;
		}
	}

	public Block readBlock(int addressInBytes) {
		return this.blocks[addressInBytes / blockSizeInBytes];
	}

}
