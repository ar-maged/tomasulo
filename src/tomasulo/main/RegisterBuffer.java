package tomasulo.main;

public class RegisterBuffer {

	private int[] registrsEntries = { -1, -1, -1, -1, -1, -1, -1 };

	public int getROBIndexOfReg(int reg) {
		return registrsEntries[reg];
	}

	public void setROBIndexOfReg(int reg, int index) {
		registrsEntries[reg] = index;
	}

	public void clearROBIndexOfReg(int reg) {
		registrsEntries[reg] = -1;
	}

}
