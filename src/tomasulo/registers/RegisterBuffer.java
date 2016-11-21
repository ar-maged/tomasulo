package tomasulo.registers;

import java.util.Arrays;

public class RegisterBuffer {

	private Integer[] registersEntries;

	public RegisterBuffer() {
		registersEntries = new Integer[8];
		Arrays.fill(registersEntries, null);
	}

	public int getROBIndexOfReg(int reg) {
		return registersEntries[reg];
	}

	public void setROBIndexOfReg(int reg, int index) {
		registersEntries[reg] = index;
	}

	public void clearROBIndexOfReg(int reg) {
		registersEntries[reg] = null;
	}

}
