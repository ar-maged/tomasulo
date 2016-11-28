package tomasulo.memory;

import tomasulo.configuration.Config;
import tomasulo.configuration.memory.CacheConfig;
import tomasulo.configuration.memory.MemoryConfig;
import tomasulo.configuration.memory.WritingPolicy;
import tomasulo.instructions.Instruction;

import java.util.ArrayList;
import java.util.HashMap;

public class Memory {

    private MainMemory mainMemory;
    private Cache[] caches;

    public Memory(MemoryConfig memoryConfig) {
        this.mainMemory = new MainMemory(memoryConfig.getBlockSizeBytes() / 2);
        this.caches = new Cache[memoryConfig.getCachesConfig().size()];
        for (int i = 0; i < this.caches.length; i++) {
            this.caches[i] = new Cache(memoryConfig.getCachesConfig().get(i));
        }
    }

    public Block readBlock(int addressWords) {
        Block block = null;
        int i;

        for (i = 0; i < caches.length; i++) {
            block = this.caches[i].read(addressWords);
            if (block != null) break;
        }

        if(block == null) block = this.mainMemory.readBlock(addressWords);

        for (int j = 0; j < i; j++) {
            this.caches[j].writeTrivial(addressWords, block);
        }
        return block;
    }

    public Object readInstructionOrData(int addressWords) {
        Block block = null;
        int i;

        for (i = 0; i < caches.length; i++) {
            block = this.caches[i].read(addressWords);
            if (block != null) break;
        }

        if(block == null) block = this.mainMemory.readBlock(addressWords);

        for (int j = 0; j < i; j++) {
            this.caches[j].writeTrivial(addressWords, block);
        }

        return block.readInstructionOrData(addressWords);

    }

    public Instruction readInstruction(int addressWords){
        return (Instruction) this.mainMemory.readBlock(addressWords).readInstructionOrData(addressWords);
    }



    public void writeBlock(int addressWords, Block block) {
        HashMap<String, Object> returnedBlock = null;
        int i;
        for (i = 0; i < caches.length; i++) {
            returnedBlock = caches[i].write(addressWords, block);
            if(returnedBlock == null) break;
        }
        if(i == caches.length){
            if(returnedBlock.get("enable").equals(true)){
                this.mainMemory.writeBlock((int)returnedBlock.get("address"), (Block)returnedBlock.get("block"));
            }else{
                this.mainMemory.writeBlock(addressWords, (Block)returnedBlock.get("block"));
            }
        }
//        if()
//        this.mainMemory.writeBlock(addressWords, block);
    }

    public void loadProgram(ArrayList<Instruction> instructions, int startAddressWords) {
        this.mainMemory.loadProgram(instructions, startAddressWords);
    }

    public static void main(String[] args){
        Config config = new Config();

        // Memory configurations
        config.getMemoryConfig().setBlockSizeBytes(8);
//        config.getMemoryConfig().setHitWritingPolicy(WritingPolicy.ALLOCATE);
//        config.getMemoryConfig().setMissWritingPolicy(WritingPolicy.AROUND);

        // Main memory configurations
        config.getMemoryConfig().getMainMemoryConfig().setAccessCycles(100);

        // Cache(s) configurations
        config.getMemoryConfig().addCacheConfig(new CacheConfig(32, 8, 1, 5, WritingPolicy.BACK, WritingPolicy.BACK));
//        config.getMemoryConfig().addCacheConfig(new CacheConfig(64, 8, 2, 10, WritingPolicy.THROUGH, WritingPolicy.THROUGH));

        Memory memory = new Memory(config.getMemoryConfig());
        Block block = new Block(8/2);
        block.addData(12,0);
        block.addData(13,1);
        block.addData(14,2);
        block.addData(15,3);
        memory.writeBlock(24, block);

        Block block2 = new Block(8/2);
        block2.addData(120,0);
        block2.addData(130,1);
        block2.addData(140,2);
        block2.addData(150,3);
        memory.writeBlock(56, block2);

//        System.out.println("Memory \n" + memory.readBlock(63));
        System.out.println("Cache 1 \n" + memory.mainMemory);
//        System.out.println("Cache 1 \n" + memory.caches[0]);
//        System.out.println("Cache 2 \n" + memory.caches[1]);

    }
}
