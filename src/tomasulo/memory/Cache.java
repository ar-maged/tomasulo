package tomasulo.memory;

public class Cache {

	private WritingPolicy writingPolicy;
	private Block[] blocks;

	public Cache() {
		// TODO Auto-generated constructor stub
	}

	public boolean write(int addressInBytes) {
		return false;
	}

	public Block read(int addressInBytes) {
		return null;
	}

	private Block findBlock(int addressInBytes) {
		return null;
	}

}
