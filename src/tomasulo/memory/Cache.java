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

    public static void main(String[] batekha) {
        CacheConfig config = new CacheConfig(128, 4, 32, 10, WritingPolicy.THROUGH);
        Cache cache = new Cache(config);

        Block block = new Block(2);
        block.addData(5, 0);
        block.addData(8, 1);
        cache.write(0, block);

        Block block2 = new Block(2);
        block2.addData(100, 0);
        block2.addData(2, 1);
        cache.write(0, block2);


        Block block3 = new Block(2);
        block3.addData(9999, 0);
        block3.addData(44, 1);
        cache.write(0, block3);


//        System.out.println(cache.read(0));
//        System.out.println(cache.read(2));
//        System.out.println(cache.read(4));
//        System.out.println(cache.read(6));

        cache.read(2);

//        System.out.println(cache);
    }

    @Override
    public String toString() {
        String s = "";
        //FIXME: Set it to a fixed number for now
        for (int i = 0; i < entries.length; i++) {
            s += i + " -> \n" + entries[i].getBlock() + "\n";
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
                Block oldBlock = entry.getBlock();
                entry.setBlock(block);
                System.out.println("remember me -> " + oldBlock.equals(block) + " If true :'((");
                return oldBlock;
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
                Block oldBlock = entry.getBlock();
                entry.setTag(entryTag);
                entry.setValid(true);
                entry.setDirty(true);
                return oldBlock;
            }
        }
    }

    private Block writeAssociativeHelper(int entryIndex, int entryTag, Block block) {
        CacheEntry entry = null;
        int numberOfEntriesPerSet = entries.length / this.associativity;
        for (int i = 0; i < this.associativity; i++) {
            if (associativity != this.cacheLines) {
                entry = this.entries[entryIndex + (numberOfEntriesPerSet * i)];
            } else {
                entry = entries[i];
            }
            if (entry.getTag() == entryTag) {
                return writeHelper(entryIndex, entryTag, block);
            }
        }
        for (int i = 0; i < this.associativity; i++) {
            if (associativity != this.cacheLines) {
                entry = this.entries[entryIndex + (numberOfEntriesPerSet * i)];
            } else {
                entry = entries[i];
            }
            if (!entry.isValid()) {
                entry.setBlock(block);
                entry.setTag(entryTag);
                entry.setValid(true);
                if (this.writeMissPolicy == WritingPolicy.BACK) {
                    entry.setDirty(true);
                    return null;
                } else {
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
            return writeAssociativeHelper(indexDecimal, tagDecimal, block);
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
