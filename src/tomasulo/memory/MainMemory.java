package tomasulo.memory;

import tomasulo.instructions.Instruction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MainMemory {

    public static int memoryCapacity = 64 * 1024;
    private Block[] blocks;
    private int blockSizeWords;

    public MainMemory(int blockSizeWords) {
        this.blockSizeWords = blockSizeWords;
        int numberOfRows = memoryCapacity / (blockSizeWords * 2);
        this.blocks = new Block[numberOfRows];
        for (int i = 0; i < numberOfRows; i++) {
            this.blocks[i] = new Block(blockSizeWords);
        }
    }

    private Stack<Object> convertMemoryEntryToStack(ArrayList<?> memoryEntryArray) {
        Stack<Object> memoryEntryStack = new Stack<>();
        ArrayList<?> temp = (ArrayList<?>) memoryEntryArray.clone();
        Collections.reverse(temp);
        memoryEntryStack.addAll(temp);
        return memoryEntryStack;
    }

    public void loadProgram(ArrayList<Instruction> instructions, int startAddressWords) {
        Stack<Object> instructionsStack = this.convertMemoryEntryToStack(instructions);
        while (!instructionsStack.isEmpty()) {
            // Loop over number of instructions per block
            for (int i = 0; i < this.blockSizeWords; i++) {
                if (!instructionsStack.isEmpty()) {
                    this.blocks[startAddressWords / this.blockSizeWords].addInstruction((Instruction)instructionsStack.pop(), i);
                }
            }
            startAddressWords += this.blockSizeWords;
        }
    }

    public void loadData(ArrayList<Integer> data, int startAddressWords) {
        Stack<Object> dataStack = this.convertMemoryEntryToStack(data);
        while (!dataStack.isEmpty()) {
            // Loop over number of instructions per block
            for (int i = 0; i < this.blockSizeWords; i++) {
                if (!dataStack.isEmpty()) {
                    this.blocks[startAddressWords / this.blockSizeWords].addData((Integer) dataStack.pop(), i);
                }
            }
            startAddressWords += this.blockSizeWords;
        }
    }

    public Block readBlock(int addressWords) {
        return this.blocks[addressWords / blockSizeWords];
    }

    public void writeBlock(int addressWords, Block block){
        this.blocks[addressWords/blockSizeWords] = block;
    }

}
