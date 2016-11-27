package tomasulo.memory;

import tomasulo.configuration.memory.CacheConfig;
import tomasulo.configuration.memory.WritingPolicy;

import java.util.HashMap;
import java.util.Objects;

public class Cache {

    private static final int OFFSET = 0;
    private static final int TAG = 1;
    private static final int INDEX = 2;

    private WritingPolicy writeMissPolicy;
    private WritingPolicy writeHitPolicy;
    private CacheEntry[] entries;
    private int associativity;

    private int offsetBits;
    private int indexBits;
    private int tagBits;
    private int cacheLines;

    public Cache(CacheConfig config) {

        this.writeMissPolicy = config.getWriteMissPolicy();
        this.writeHitPolicy = config.getWriteHitPolicy();
        this.cacheLines = config.getSizeBytes() / config.getLineSizeBytes();
        this.associativity = config.getAssociativity();
        this.offsetBits = log2(config.getLineSizeBytes() / 2);
        this.indexBits = log2(cacheLines / this.associativity);
        this.tagBits = (config.getLineSizeBytes() * 8) - (this.offsetBits + this.indexBits);
        entries = new CacheEntry[cacheLines];

        for (int i = 0; i < entries.length; i++) {
            entries[i] = new CacheEntry(null, 0, false);
        }

    }

    public static int log2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }

    public static void testDirectMap() {
        CacheConfig config = new CacheConfig(128, 4, 1, 10, WritingPolicy.THROUGH, WritingPolicy.BACK);
        Cache cache = new Cache(config);
        Block block = null;
        for (int i = 0; i < 20; i += 2) {
            block = new Block(2);
            block.addData(i, 0);
            block.addData(i + 1, 1);
            System.out.println("Write (" + i + "): " + cache.write(i, block));
        }
        System.out.println(cache);
    }
    public static void testDirectReplace(){
        CacheConfig config = new CacheConfig(128, 4, 1, 10, WritingPolicy.BACK, WritingPolicy.BACK);
        Cache cache = new Cache(config);
        Block block = null;
//        for (int i = 0; i < 64; i += 2) {
//            block = new Block(2);
//            block.addData(i, 0);
//            block.addData(i + 1, 1);
////            cache.write(i, block);
//            System.out.println("Write (" + i + "): " + cache.write(i, block));
//        }
//        for (int i = 64; i < 128; i += 2) {
//            block = new Block(2);
//            block.addData(i * 2, 0);
//            block.addData(i * 2 + 1, 1);
////            cache.write(i, block);
//            System.out.println("Write (" + i + "): " + cache.write(i, block));
//        }

        block = new Block(2);
        block.addData(128,0);
        block.addData(129,1);
        System.out.println(cache.write(4,block));
        block = new Block(2);
        block.addData(208, 0);
        block.addData(100, 1);
        System.out.println(cache.write(4, block));



        System.out.println(cache);
    }
    public static void testFullAssociativity(){
        CacheConfig config = new CacheConfig(128, 4, 32, 10, WritingPolicy.BACK, WritingPolicy.BACK);
        Cache cache = new Cache(config);
        Block block = null;
        for (int i = 0; i < 128; i += 2) {
            block = new Block(2);
            block.addData(i, 0);
            block.addData(i + 1, 1);
            System.out.println("Write (" + i + "): " + cache.write(i, block));
        }

        block = new Block(2);
        block.addData(500,0);
        block.addData(501,1);
        System.out.println(cache.write(4,block));
        block = new Block(2);
        block.addData(600, 0);
        block.addData(601, 1);
        System.out.println(cache.write(6, block));



        System.out.println(cache);
    }
    public static void testSetAssociativity(int associativity){
        CacheConfig config = new CacheConfig(128, 4, associativity, 10, WritingPolicy.THROUGH, WritingPolicy.THROUGH);
        Cache cache = new Cache(config);
        Block block = null;
        for (int i = 16; i < 17; i += 1) {
            block = new Block(2);
            block.addData(i, 0);
            block.addData(i + 1, 1);
            System.out.println("Write (" + i + "): " + cache.write(i, block));
        }
        System.out.println(cache);
    }
    public static void main(String[] args) {
//        testDirectMap();
//        testDirectReplace();
        testFullAssociativity();
//        testSetAssociativity(2);
    }

    @Override
    public String toString() {
        String s = "";
        //FIXME: Set it to a fixed number for now
        for (int i = 0; i < entries.length; i++) {
            s += i + " -> \n" + entries[i] + "\n";
        }
        return s;
    }

    public HashMap<Integer, Integer> convertAddress(int addressWords) {
        HashMap<Integer, Integer> map = new HashMap<>();
        String binaryAddress = String.format("%16s", Integer.toBinaryString(addressWords)).replace(' ', '0');
        String tagBinary, indexBinary, offsetBinary;
        int offsetDecimal, indexDecimal, tagDecimal;

        offsetBinary = binaryAddress.substring(binaryAddress.length() - offsetBits);
        indexBinary = binaryAddress.substring(binaryAddress.length() - (offsetBits + indexBits), binaryAddress.length() - offsetBits);
        tagBinary = binaryAddress.substring(0, binaryAddress.length() - (offsetBits + indexBits));

        offsetDecimal = Integer.parseInt(offsetBinary, 2);
        indexDecimal = Objects.equals(indexBinary, "") ? 0 : Integer.parseInt(indexBinary, 2);
        tagDecimal = Integer.parseInt(tagBinary, 2);

        map.put(TAG, tagDecimal);
        map.put(INDEX, indexDecimal);
        map.put(OFFSET, offsetDecimal);

        return map;
    }

    private Block writeHelper(int entryIndex, int entryTag, Block block) {
        CacheEntry entry = this.entries[entryIndex];
        if (entry.isValid() && entry.getTag() == entryTag) {
            if (this.writeHitPolicy == WritingPolicy.BACK) {
                entry.setBlock(block);
                entry.setDirty(true);
                return null;
            } else {
//                Block oldBlock = entry.getBlock();
                entry.setBlock(block);
                entry.setDirty(false);
//                return oldBlock;
                return block;
            }
        } else {
            if (this.writeMissPolicy == WritingPolicy.BACK) {
                if (entry.isDirty()) {
                    boolean isValid = entry.isValid();
                    Block oldBlock = entry.getBlock();
                    entry.setBlock(block);
                    entry.setTag(entryTag);
                    entry.setValid(true);
                    entry.setDirty(true);
                    return isValid ? oldBlock : null;
                } else {
                    entry.setBlock(block);
                    entry.setTag(entryTag);
                    entry.setValid(true);
                    entry.setDirty(true);
                    return null;
                }
            } else {
//                Block oldBlock = entry.getBlock();
                entry.setBlock(block);
                entry.setTag(entryTag);
                entry.setValid(true);
                entry.setDirty(false);
                return block;
            }
        }
    }

    private Block writeHelperII(CacheEntry entry, int entryTag, Block block) {
        if (entry.isValid() && entry.getTag() == entryTag) {
            if (this.writeHitPolicy == WritingPolicy.BACK) {
                entry.setBlock(block);
                entry.setDirty(true);
                return null;
            } else {
//                Block oldBlock = entry.getBlock();
                entry.setBlock(block);
                entry.setDirty(false);
//                return oldBlock;
                return block;
            }
        } else {
            if (this.writeMissPolicy == WritingPolicy.BACK) {
                if (entry.isDirty()) {
                    boolean isValid = entry.isValid();
                    Block oldBlock = entry.getBlock();
                    entry.setBlock(block);
                    entry.setTag(entryTag);
                    entry.setValid(true);
                    entry.setDirty(true);
                    return isValid ? oldBlock : null;
                } else {
                    entry.setBlock(block);
                    entry.setTag(entryTag);
                    entry.setValid(true);
                    entry.setDirty(true);
                    return null;
                }
            } else {
//                Block oldBlock = entry.getBlock();
                entry.setBlock(block);
                entry.setTag(entryTag);
                entry.setValid(true);
                entry.setDirty(false);
                return block;
            }
        }
    }

    private Block writeFullyAssociativeHelper(int entryIndex, int entryTag, Block block){
        CacheEntry entry = null;
        int numberOfEntriesPerSet = entries.length / (entries.length / this.associativity);
        for (int i = 0; i < this.associativity; i++) {
            entry = entries[i];
            if (entry.getTag() == entryTag) {
                return writeHelperII(entry, entryTag, block);
            }
        }
        for (int i = 0; i < this.associativity; i++) {
            entry = entries[i];
            if (!entry.isValid()) {
                entry.setBlock(block);
                entry.setTag(entryTag);
                entry.setValid(true);
                if (this.writeMissPolicy == WritingPolicy.BACK) {
                    entry.setDirty(true);
                    return null;
                } else {
                    entry.setDirty(false);
                    return block;
                }
            }
        }
        Block oldBlock = entry.getBlock();
        entry.setBlock(block);
        entry.setTag(entryTag);
        entry.setValid(true);
        if (this.writeMissPolicy == WritingPolicy.BACK) {
            boolean isDirty = entry.isDirty();
            entry.setDirty(true);
//            System.out.println("sagdjhasgjgashdgajhsd ghas dgsha dasgdh ashjd gsaj dhasghdjhas");
//            System.out.println("old block => " + oldBlock );
//            System.out.println("New Block => " + block);
            return isDirty ? oldBlock : null;
        } else {
            entry.setDirty(false);
            return block;
        }
    }

    private Block writeSetAssociativeHelper(int entryIndex, int entryTag, Block block) {
        CacheEntry entry = null;
        int numberOfEntriesPerSet = entries.length / (entries.length / this.associativity);
        for (int i = 0; i < entries.length / this.associativity; i++) {
            entry = this.entries[entryIndex + (numberOfEntriesPerSet * i)];
            if (entry.getTag() == entryTag) {
                return writeHelper(entryIndex, entryTag, block);
            }
        }
        for (int i = 0; i < entries.length / this.associativity; i++) {
            entry = this.entries[entryIndex + (numberOfEntriesPerSet * i)];
            if (!entry.isValid()) {
                entry.setBlock(block);
                entry.setTag(entryTag);
                entry.setValid(true);
                if (this.writeMissPolicy == WritingPolicy.BACK) {
                    entry.setDirty(true);
                    return null;
                } else {
                    entry.setDirty(false);
                    return block;
                }
            }
        }
        Block oldBlock = entry.getBlock();
        entry.setBlock(block);
        entry.setTag(entryTag);
        entry.setValid(true);
        if (this.writeMissPolicy == WritingPolicy.BACK) {
            boolean isDirty = entry.isDirty();
            entry.setDirty(true);
            return isDirty ? oldBlock : null;
        } else {
            entry.setDirty(false);
            return block;
        }
    }

    public Block write(int addressWords, Block block) {
        HashMap<Integer, Integer> map = convertAddress(addressWords);
        int offsetDecimal, indexDecimal, tagDecimal;
        offsetDecimal = map.get(OFFSET);
        indexDecimal = map.get(INDEX);
        tagDecimal = map.get(TAG);

        if (associativity == 1) {
            return writeHelper(indexDecimal, tagDecimal, block);
        } else {
            if (associativity != this.cacheLines)
                return writeSetAssociativeHelper(indexDecimal, tagDecimal, block);
            else
                return writeFullyAssociativeHelper(indexDecimal, tagDecimal, block);

        }
    }

    public Block read(int addressWords) {
        HashMap<Integer, Integer> map = convertAddress(addressWords);
        int offsetDecimal, indexDecimal, tagDecimal;

        offsetDecimal = map.get(OFFSET);
        indexDecimal = map.get(INDEX);
        tagDecimal = map.get(TAG);
        if (associativity == 1) {
            CacheEntry entry = this.entries[indexDecimal];
            if (entry.isValid() && entry.getTag() == tagDecimal) {
                return entry.getBlock();
            }
        } else if (associativity == this.cacheLines) {
            for (int i = 0; i < entries.length; i++) {
                CacheEntry entry = entries[i];
                System.out.println(entry.getBlock() + " " + entry.getTag());
                if (entry.getTag() == tagDecimal) {
                    return entry.getBlock();
                }
            }
        } else {
            CacheEntry entry;
            for (int i = 0; i < this.associativity; i++) {
                int numberOfEntriesPerSet = entries.length / this.associativity;
                entry = this.entries[indexDecimal + (numberOfEntriesPerSet * i)];
                if (entry.isValid() && entry.getTag() == tagDecimal) {
                    return entry.getBlock();
                }
            }
        }

        return null;
    }

}
