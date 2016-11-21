package tomasulo.registers;

public class RegisterFile {

	private Register[] registers;

	public RegisterFile() {

		registers = new Register[8];

		for (int i = 0; i < registers.length; i++)
			registers[i] = new Register();

		registers[0].setImmutable();

	}

	public int readRegister(int index) {
		return registers[index].getValue();
	}

	public void writeRegister(int index, int newValue) {
		registers[index].setValue(newValue);
	}

	@Override
	public String toString() {

		String toString = "[";

		for (int i = 0; i < registers.length; i++)
			toString += "R" + i + ":" + registers[i].toString() + ", ";

		toString = toString.substring(0, toString.length() - 2);
		toString += "]";

		return toString;

	}

	private class Register {

		private final int capacity;
		private boolean mutable;
		private int value;

		private Register() {

			capacity = 16;
			mutable = true;
			value = 0;

		}

		private int getValue() {
			return value;
		}

		private void setValue(int newValue) {

			if (mutable) {

				String newValueBinary = Integer.toBinaryString(newValue);

				if (newValueBinary.length() >= capacity)
					value = Integer.parseInt(newValueBinary.substring(newValueBinary.length() - capacity), 2);
				else
					value = newValue;

			}

		}

		private void setImmutable() {
			mutable = false;
		}

		@Override
		public String toString() {
			return "{" + String.format("%16s", Integer.toBinaryString(value)).replace(' ', '0') + "}";

		}

	}

}
