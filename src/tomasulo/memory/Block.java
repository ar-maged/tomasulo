package tomasulo.memory;

public class Block {

	private String[] data;
	private boolean dirtyBit = false;

	public Block(int blockSizeInBytes) {
		// Number of instructions per block
		this.data = new String[blockSizeInBytes / 2];
	}

	public void addData(String instruction, int offset) {
		this.data[offset] = instruction;
	}

	public boolean isFull() {
		for (int i = 0; i < this.data.length; i++) {
			if (this.data[i] == null)
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String datastringified = "";
		datastringified += "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		for (int i = 0; i < this.data.length; i++) {
			datastringified += i + " --->  (" + this.data[i] + ") | ";
		}
		datastringified += "\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";

		return datastringified;
	}

	public boolean isDirtyBit() {
		return dirtyBit;
	}

	public void setDirtyBit(boolean dirtyBit) {
		this.dirtyBit = dirtyBit;
	}

}
