package tomasulo.configuration.memory;

public class CacheConfig {

	private int sizeBytes;
	private int lineSizeBytes;
	private int associativty;
	private int accessCycles;

	public CacheConfig() {

	}

	public int getSizeBytes() {
		return sizeBytes;
	}

	public void setSizeBytes(int sizeBytes) {
		this.sizeBytes = sizeBytes;
	}

	public int getLineSizeBytes() {
		return lineSizeBytes;
	}

	public void setLineSizeBytes(int lineSizeBytes) {
		this.lineSizeBytes = lineSizeBytes;
	}

	public int getAssociativty() {
		return associativty;
	}

	public void setAssociativty(int associativty) {
		this.associativty = associativty;
	}

	public int getAccessCycles() {
		return accessCycles;
	}

	public void setAccessCycles(int accessCycles) {
		this.accessCycles = accessCycles;
	}

}
