package tomasulo.memory;

import tomasulo.configuration.memory.CacheConfig;
import tomasulo.configuration.memory.WritingPolicy;

import java.util.HashMap;

public class Cache {

    private static final int OFFSET = 0;
    private static final int TAG = 1;
    private static final int INDEX = 2;

    private WritingPolicy writingPolicy;
    private CacheEntry[] entries;
    private int associativity;

    private int offsetBits;
    private int indexBits;
    private int tagBits;
    private int cacheLines;

    public Cache(CacheConfig config) {

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
        CacheConfig config = new CacheConfig(32 * 1024, 4, 2, 10);
        Cache cache = new Cache(config);

        Block block = new Block(2);
        block.addData(5, 0);
        block.addData(8, 1);
        cache.write(0, block);
        Block block2 = new Block(2);
        block2.addData(100, 0);
        block2.addData(2, 1);
        cache.write(4, block2);

        System.out.println(cache);
    }

    @Override
    public String toString() {
        String s = "";
        //FIXME: Set it to a fixed number for now
        for (int i = 0; i < 10; i++) {
            s += entries[i].getBlock() + "\n";
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
        indexDecimal = Integer.parseInt(indexBinary, 2);
        tagDecimal = Integer.parseInt(tagBinary, 2);

        map.put(TAG, tagDecimal);
        map.put(INDEX, indexDecimal);
        map.put(OFFSET, offsetDecimal);

        return map;
    }

    public void write(int addressWords, Block block) {
        HashMap<Integer, Integer> map = convertAddress(addressWords);
        int offsetDecimal, indexDecimal, tagDecimal;

        offsetDecimal = map.get(OFFSET);
        indexDecimal = map.get(INDEX);
        tagDecimal = map.get(TAG);
        if (associativity == 1) {
            CacheEntry entry = this.entries[indexDecimal];
            entry.setValid(true);
            entry.setTag(tagDecimal);
            entry.setBlock(block);
        } else if (associativity == this.cacheLines) {
            CacheEntry entry = null;
            for (int i = 0; i < entries.length; i++) {
                entry = entries[i];
                if (!entry.isValid()) {
                    entry.setValid(true);
                    entry.setTag(tagDecimal);
                    entry.setBlock(block);
                    return;
                }
            }
            entry.setValid(true);
            entry.setTag(tagDecimal);
            entry.setBlock(block);
        } else {
            CacheEntry entry = null;
            for (int i = 0; i < this.associativity; i++) {
                int numberOfEntriesPerSet = entries.length / this.associativity;
                entry = this.entries[indexDecimal + (numberOfEntriesPerSet * i)];
                if (!entry.isValid()) {
                    entry.setValid(true);
                    entry.setTag(tagDecimal);
                    entry.setBlock(block);
                    return;
                }
            }
            entry.setValid(true);
            entry.setTag(tagDecimal);
            entry.setBlock(block);
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
