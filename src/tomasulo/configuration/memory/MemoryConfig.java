package tomasulo.configuration.memory;

import java.util.ArrayList;

public class MemoryConfig {

    private int blockSizeBytes;
    private ArrayList<CacheConfig> cachesConfig;
    private MainMemoryConfig mainMemoryConfig;

    public MemoryConfig() {
        cachesConfig = new ArrayList<CacheConfig>();
        mainMemoryConfig = new MainMemoryConfig();
    }

    public MemoryConfig(int blockSizeBytes) {
        this();
        this.blockSizeBytes = blockSizeBytes;
    }

    public ArrayList<CacheConfig> getCachesConfig() {
        return cachesConfig;
    }

    public void setCachesConfig(ArrayList<CacheConfig> cachesConfig) {
        this.cachesConfig = cachesConfig;
    }

    public void addCacheConfig(CacheConfig cacheConfig) {
        this.cachesConfig.add(cacheConfig);
    }

    public MainMemoryConfig getMainMemoryConfig() {
        return mainMemoryConfig;
    }

    public void setMainMemoryConfig(MainMemoryConfig mainMemoryConfig) {
        this.mainMemoryConfig = mainMemoryConfig;
    }

    public int getBlockSizeBytes() { return blockSizeBytes; }

    public void setBlockSizeBytes(int blockSizeBytes) { this.blockSizeBytes = blockSizeBytes; }

}
