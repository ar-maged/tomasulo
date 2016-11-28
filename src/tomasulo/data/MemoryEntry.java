package tomasulo.data;

import java.util.ArrayList;

public class MemoryEntry {
	
	Integer memoryLocation;
	ArrayList<Integer> data;
	
	
	public MemoryEntry()
	{
		this.data = new ArrayList<Integer>();
	}
	public Integer getMemoryLocation() {
		return memoryLocation;
	}
	public void setMemoryLocation(Integer memoryLocation) {
		this.memoryLocation = memoryLocation;
	}
	public ArrayList<Integer> getData() {
		return data;
	}
	public void setData(ArrayList<Integer> data) {
		this.data = data;
	}
	
	

}
