package tomasulo.main;

public class RegisterBuffer {

	private Integer[] registrsEntries = { null, null, null, null, null, null,
			null, null };

	public int getROBIndexOfReg(int reg) {
		return registrsEntries[reg];
	}

	public void setROBIndexOfReg(int reg, int index) {
		registrsEntries[reg] = index;
	}

	public void clearROBIndexOfReg(int reg) {
		registrsEntries[reg] = null;
	}

}
