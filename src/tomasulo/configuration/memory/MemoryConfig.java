package tomasulo.configuration.memory;

import java.util.ArrayList;

public class MemoryConfig {

	private ArrayList<CacheConfig> cachesConfig;
	private MainMemoryConfig mainMemoryConfig;
	private WritingPolicy hitWritingPolicy;
	private WritingPolicy missWritingPolicy;

	public MemoryConfig() {
		cachesConfig = new ArrayList<CacheConfig>();
		mainMemoryConfig = new MainMemoryConfig();
	}

	public MemoryConfig(WritingPolicy hitWritingPolicy, WritingPolicy missWritingPolcity) {
		this();
		this.hitWritingPolicy = hitWritingPolicy;
		this.missWritingPolicy = missWritingPolcity;
	}

	public ArrayList<CacheConfig> getCachesConfig() {
		return cachesConfig;
	}

	public void setCachesConfig(ArrayList<CacheConfig> cachesConfig) {
		this.cachesConfig = cachesConfig;
	}

	public MainMemoryConfig getMainMemoryConfig() {
		return mainMemoryConfig;
	}

	public void setMainMemoryConfig(MainMemoryConfig mainMemoryConfig) {
		this.mainMemoryConfig = mainMemoryConfig;
	}

	public WritingPolicy getHitWritingPolicy() {
		return hitWritingPolicy;
	}

	public void setHitWritingPolicy(WritingPolicy hitWritingPolicy) {
		this.hitWritingPolicy = hitWritingPolicy;
	}

	public WritingPolicy getMissWritingPolicy() {
		return missWritingPolicy;
	}

	public void setMissWritingPolicy(WritingPolicy missWritingPolicy) {
		this.missWritingPolicy = missWritingPolicy;
	}

}
