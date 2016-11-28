package tomasulo.data;

public class MemoryEntry {
	
	Integer memoryLocation;
	Integer [] data;
	
	
	public Integer getMemoryLocation() {
		return memoryLocation;
	}
	public void setMemoryLocation(Integer memoryLocation) {
		this.memoryLocation = memoryLocation;
	}
	public Integer[] getData() {
		return data;
	}
	public void setData(Integer[] data) {
		this.data = data;
	}
	

}
