package tomasulo.memory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

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

	private Stack<String> convertInstructionsArrayToStack(String[] instructionsArray) {
		List<String> instructionsList = Arrays.asList(instructionsArray);
		Stack<String> instructionsStack = new Stack<>();
		Collections.reverse(instructionsList);
		instructionsStack.addAll(instructionsList);
		return instructionsStack;
	}

	public void readProgram(String[] instructions, int startAddressInBytes) {
		Stack<String> instructionsStack = this.convertInstructionsArrayToStack(instructions);
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

	public static void main(String[] args) {
		MainMemory memory = new MainMemory(16);
		String[] instructions = { "ADD R2, R4, R5", "SUB R9, R8, R7", "MUL R2, R4, R4", "DIV R0, R1, R5", "ADDI R2, R2, 45" };

		memory.readProgram(instructions, 0);
		for (int i = 0; i < instructions.length; i++) {
			System.out.println(memory.readBlock(i * memory.blockSizeInBytes));
		}
	}

}
