package tomasulo.instructions;

public class Instruction {

	private InstructionName name;
	private Integer sourceRegister1;
	private Integer sourceRegister2;
	private Integer destinationRegister;
	private Integer immediate;

	public Instruction() {

	}

	public Instruction(InstructionName name, Integer sourceRegister1, Integer sourceRegister2, Integer destinationRegister, Integer immediate) {
		this.name = name;
		this.sourceRegister1 = sourceRegister1;
		this.sourceRegister2 = sourceRegister2;
		this.destinationRegister = destinationRegister;
		this.immediate = immediate;
	}

	public InstructionName getName() {
		return name;
	}

	public void setName(InstructionName name) {
		this.name = name;
	}

	public Integer getSourceRegister1() {
		return sourceRegister1;
	}

	public void setSourceRegister1(Integer sourceRegister1) {
		this.sourceRegister1 = sourceRegister1;
	}

	public Integer getSourceRegister2() {
		return sourceRegister2;
	}

	public void setSourceRegister2(Integer sourceRegister2) {
		this.sourceRegister2 = sourceRegister2;
	}

	public Integer getDestinationRegister() {
		return destinationRegister;
	}

	public void setDestinationRegister(Integer destinationRegister) {
		this.destinationRegister = destinationRegister;
	}

	public Integer getImmediate() {
		return immediate;
	}

	public void setImmediate(Integer immediate) {
		this.immediate = immediate;
	}

}
