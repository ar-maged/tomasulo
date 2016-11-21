package tomasulo.action;

import tomasulo.instructions.InstructionName;

public class ReorderBuffer {

	private ROBEntry[] entries;
	private int head;
	private int tail;

	public ReorderBuffer() {

	}

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

	public boolean addInstruction(InstructionName instruction, int reg) {
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

		public ROBEntry(InstructionName inst, int reg) {

			type = instructionType(inst);
			register = reg;
			ready = false;

		}

		public Type getInstructionType() {
			return type;
		}

		public void setInstructionType(InstructionName instruction) {
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

		private Type instructionType(InstructionName instruction) {

			if (instruction == InstructionName.LW) {
				return Type.LW;
			}
			if (instruction == InstructionName.SW) {
				return Type.SW;
			}
			if (instruction == InstructionName.BEQ) {
				return Type.BRANCH;
			}
			if (instruction == InstructionName.JMP
					|| instruction == InstructionName.JALR
					|| instruction == InstructionName.RET) {
				return Type.JUMP;
			}
			return Type.INT;
		}
	}

	enum Type {
		FP, INT, LW, SW, BRANCH, JUMP
	}

}
