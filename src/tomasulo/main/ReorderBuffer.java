package tomasulo.main;

public class ReorderBuffer {

	private Instruction[] instruction;
	private int[] destinationReg;
	private int[] value;

	public ReorderBuffer(int numOfFUs) {

		instruction = new Instruction[numOfFUs];
		destinationReg = new int[numOfFUs];
		value = new int[numOfFUs];
	}

	public Instruction[] getInstruction() {
		return instruction;
	}

	public void setInstruction(int index, Instruction instruction) {
		this.instruction[index] = instruction;
	}

	public int[] getDestinationReg() {
		return destinationReg;
	}

	public void setDestinationReg(int index, int destinationReg) {
		this.destinationReg[index] = destinationReg;
	}

	public int[] getValue() {
		return value;
	}

	public void setValue(int index, int value) {
		this.value[index] = value;
	}
}
