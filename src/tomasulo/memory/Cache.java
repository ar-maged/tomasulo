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

    public Cache(CacheConfig config) {

        int cacheLines = config.getSizeBytes() / config.getLineSizeBytes();
        this.offsetBits = log2(config.getLineSizeBytes() / 2);
        this.indexBits = log2(cacheLines);
        this.tagBits = (config.getLineSizeBytes() * 8) - (this.offsetBits + this.indexBits);
        this.associativity = config.getAssociativity();
        entries = new CacheEntry[cacheLines];

        for (int i = 0; i < entries.length; i++) {
            entries[i] = new CacheEntry(null, 0, false);
        }

    }

    public static int log2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }

    public static void main(String[] batekha) {
        CacheConfig config = new CacheConfig(32 * 1024, 4, 1, 10);
        Cache cache = new Cache(config);
        Block block = new Block(2);
        block.addData(5, 0);
        block.addData(8, 1);
        cache.write(2, block);
        System.out.println(cache.read(0).toString());
    }

    public HashMap<Integer, Integer> convertAddress(int addressWords) {
        HashMap<Integer, Integer> map = new HashMap<>();
        String binaryAddress = String.format("%16s", Integer.toBinaryString(addressWords)).replace(' ', '0');

        String offsetBinary, indexBinary, tagBinary;
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
        if (associativity == 1) {
            HashMap<Integer, Integer> map = convertAddress(addressWords);
            int offsetDecimal, indexDecimal, tagDecimal;

            offsetDecimal = map.get(OFFSET);
            indexDecimal = map.get(INDEX);
            tagDecimal = map.get(TAG);

            CacheEntry entry = this.entries[indexDecimal];
            entry.setValid(true);
            entry.setTag(tagDecimal);
            entry.setBlock(block);
        }
    }

    public Block read(int addressWords) {
        if (associativity == 1) {
            HashMap<Integer, Integer> map = convertAddress(addressWords);
            int offsetDecimal, indexDecimal, tagDecimal;

            offsetDecimal = map.get(OFFSET);
            indexDecimal = map.get(INDEX);
            tagDecimal = map.get(TAG);

            CacheEntry entry = this.entries[indexDecimal];

            if (entry.isValid()) {
                if (entry.getTag() == tagDecimal) {
                    return entry.getBlock();
                }
            }
        }

        return null;
    }

    private Block findBlock(int addressInBytes) {
        return null;
    }

}
