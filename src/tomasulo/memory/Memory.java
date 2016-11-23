package tomasulo.memory;

public class Memory {

	private MainMemory mainMemory;
	private Cache[] caches;
	private int blockSizeInBytes;

	public Memory(int blockSizeInBytes, int cacheLevels) {
		mainMemory = new MainMemory(blockSizeInBytes);
		caches = new Cache[cacheLevels];
		this.blockSizeInBytes = blockSizeInBytes;
	}

	public Block readBlock(int byteAddress) {
		return new Block(this.blockSizeInBytes);
	}

	public void writeBlock(int byteAddress, Block block) {

	}

}
