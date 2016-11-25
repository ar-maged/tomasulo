package tomasulo.memory;

import tomasulo.configuration.memory.MemoryConfig;
import tomasulo.instructions.Instruction;

import java.util.ArrayList;

public class Memory {

    private MainMemory mainMemory;
    private Cache[] caches;
    private int blockSizeWords;

    public Memory(MemoryConfig memoryConfig) {
        this.mainMemory = new MainMemory(memoryConfig.getBlockSizeBytes() / 2);
        this.caches = new Cache[memoryConfig.getCachesConfig().size()];
        this.blockSizeWords = memoryConfig.getBlockSizeBytes();
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
