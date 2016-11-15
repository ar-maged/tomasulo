package tomasulo.main;

public class ReorderBuffer {

	private ROBEntry[] entries;
	private int head;
	private int tail;

	public ReorderBuffer(int n) { // n is user defined
		entries = new ROBEntry[n];
		head = tail = 0;
	}

	public boolean commit() {
		if (entries[head].isReady()) {
			// TODO sendToDataBus(entries[head].getRegister(),
			// entries[head].getValue());
			incrementHead();
			return true;
		}
		return false;
	}

	public boolean addInstruction(Instruction instruction, int reg) {
		if (isFull())
			return false;

		entries[tail++] = new ROBEntry(instruction, reg);
		tail %= entries.length;

		return true;
	}

	public void flush() {
		head = tail;
	}

	public boolean isEmpty() {
		return head == tail;
	}

	public boolean isFull() {
		return head == ((tail + 1) % entries.length);
	}

	public ROBEntry[] getEntries() {
		return entries;
	}

	public ROBEntry getFirst() {
		if (isEmpty())
			return null;
		return entries[head];
	}

	public ROBEntry getEntry(int address) {
		return entries[address];
	}

	public int getRegisterValue(int address) {
		return entries[address].getValue();
	}

	public void setEntries(ROBEntry[] entries) {
		this.entries = entries;
	}

	public void setRegisterValue(int address, int value) {
		entries[address].setRegister(value);
		entries[address].setReady(true);
	}

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public void incrementHead() {
		head++;
		head %= entries.length;
	}

	public int getTail() {
		return tail;
	}

	public void setTail(int tail) {
		this.tail = tail;
	}

	class ROBEntry {

		private Type type;
		private int register;
		private int value;
		private boolean ready;

		public ROBEntry(Instruction inst, int reg) {

			type = instructionType(inst);
			register = reg;
			ready = false;

		}

		public Type getInstructionType() {
			return type;
		}

		public void setInstructionType(Instruction instruction) {
			this.type = instructionType(instruction);
		}

		public int getRegister() {
			return register;
		}

		public void setRegister(int register) {
			this.register = register;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public boolean isReady() {
			return ready;
		}

		public void setReady(boolean ready) {
			this.ready = ready;
		}

		private Type instructionType(Instruction instruction) {

			if (instruction == Instruction.LW) {
				return Type.LW;
			}
			if (instruction == Instruction.SW) {
				return Type.SW;
			}
			if (instruction == Instruction.BEQ) {
				return Type.BRANCH;
			}
			if (instruction == Instruction.JMP
					|| instruction == Instruction.JALR
					|| instruction == Instruction.RET) {
				return Type.JUMP;
			}
			if (instruction == Instruction.ADD
					|| instruction == Instruction.SUB
					|| instruction == Instruction.ADDI
					|| instruction == Instruction.NAND
					|| instruction == Instruction.MUL) {
				return Type.INT;
			}
		}
	}

	enum Type {
		FP, INT, LW, SW, BRANCH, JUMP
	}

}
