package tomasulo.instructions;

public class Instruction {

	InstructionName name;
	Integer source1;
	Integer source2;

	Integer destination;
	Integer immediate;

	public InstructionName getName() {
		return name;
	}

	public void setName(InstructionName name) {
		this.name = name;
	}

	public Integer getSource1() {
		return source1;
	}

	public void setSource1(Integer source1) {
		this.source1 = source1;
	}

	public Integer getSource2() {
		return source2;
	}

	public void setSource2(Integer source2) {
		this.source2 = source2;
	}

	public Integer getDestination() {
		return destination;
	}

	public void setDestination(Integer destination) {
		this.destination = destination;
	}

	public Integer getImmediate() {
		return immediate;
	}

	public void setImmediate(Integer immediate) {
		this.immediate = immediate;
	}

}
