package memory;

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
		this.blocks = new Block[memoryCapacity / blockSizeInBytes];
		for (int i = 0; i < memoryCapacity / blockSizeInBytes; i++) {
			this.blocks[i] = new Block(blockSizeInBytes);
		}
	}

	private Stack<String> convertInstructionsToStack(String[] instructionsArray) {
		List<String> instructionsList = Arrays.asList(instructionsArray);
		Stack<String> instructionsStack = new Stack<>();
		Collections.reverse(instructionsList);
		instructionsStack.addAll(instructionsList);
		return instructionsStack;
	}

	public void readProgram(String[] instructions, int startAddressInBytes) {
		Stack<String> instructionsStack = this.convertInstructionsToStack(instructions);
		while (!instructionsStack.isEmpty()) {
			for (int i = 0; i < this.blockSizeInBytes / 4; i++) {
				if (!instructionsStack.isEmpty())
					this.blocks[(int)startAddressInBytes / 4].addData(instructionsStack.pop(), i);
			}
			startAddressInBytes += 4;
		}
	}

	public Block readBlock(int addressInBytes) {
		return this.blocks[(int)addressInBytes/4];
	}

	public static void main(String[] args) {
		MainMemory memory = new MainMemory(12);
		String[] instructions = { "ADD R2, R4, R5", "SUB R9, R8, R7", "MUL R2, R4, R4", "DIV R0, R1, R5", "ADDI R2, R2, 45" };
		
		memory.readProgram(instructions, 0);
		for (int i = 0; i < instructions.length; i++) {
			System.out.println(memory.readBlock(i*4));
		}
	}
}
