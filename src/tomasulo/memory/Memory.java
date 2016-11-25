package tomasulo.memory;

import tomasulo.instructions.Instruction;

import java.util.ArrayList;

public class Memory {

    private MainMemory mainMemory;
    private Cache[] caches;
    private int blockSizeWords;

    public Memory(int blockSizeBytes, int cacheLevels) {
        mainMemory = new MainMemory(blockSizeBytes / 2);
        caches = new Cache[cacheLevels];
        this.blockSizeWords = blockSizeBytes;
    }

    public Block readBlock(int byteAddress) {
        return this.mainMemory.readBlock(byteAddress / 2);
    }

    public void writeBlock(int byteAddress, Block block) {
        this.mainMemory.writeBlock(byteAddress / 2, block);
    }

    public void loadProgram(ArrayList<Instruction> instructions, int startAddressBytes) {
        this.mainMemory.loadProgram(instructions, startAddressBytes / 2);
    }

}
