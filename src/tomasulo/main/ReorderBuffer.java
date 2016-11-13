package tomasulo.main;

public class ReorderBuffer {

	private ROBEntry[] entries;
	private int head;
	private int tail;

	public ReorderBuffer(int n) {
		entries = new ROBEntry[n];
		head = tail = 0;
	}

	// TODO commit

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

	public int getTail() {
		return tail;
	}

	public void setTail(int tail) {
		this.tail = tail;
	}

	class ROBEntry {

		private Instruction instruction;
		private int register;
		private int value;
		private boolean ready;

		public ROBEntry(Instruction inst, int reg) {

			instruction = inst;
			register = reg;
			ready = false;

		}

		public Instruction getInstruction() {
			return instruction;
		}

		public void setInstruction(Instruction instruction) {
			this.instruction = instruction;
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

	}

}
