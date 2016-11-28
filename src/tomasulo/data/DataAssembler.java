package tomasulo.data;

import java.util.ArrayList;

import tomasulo.exceptions.*;

public class DataAssembler {
	
	int LineSize;
	public DataAssembler(int lineSize)
	{
		this.LineSize = lineSize;
	}
	
	public int getLineSize() {
		return LineSize;
	}

	public void setLineSize(int lineSize) {
		LineSize = lineSize;
	}

	public ArrayList<MemoryEntry> parseAllData(String [] stringData)
	{
		 ArrayList<MemoryEntry> parsedData = new ArrayList<MemoryEntry>();

	        for (String dataEntry : stringData) {
	            parsedData.add(parseData(dataEntry));
	        }

	        return parsedData;
		
	}
	private MemoryEntry parseData(String data)
	{
		MemoryEntry memoryEntry = new MemoryEntry();
		String [] entrySplitted = data.split(" ");
		memoryEntry.setMemoryLocation(Integer.parseInt(entrySplitted[0]));

		String [] dataSplitted = entrySplitted[1].split(",");
		int maxEntries = this.getLineSize()/2;
		if(dataSplitted.length<=maxEntries){
		for (int i = 0; i<dataSplitted.length; i++)
		{
			Integer num = Integer.parseInt(dataSplitted[i]);
			if(checkVal(num)){
			memoryEntry.getData().add(num);}
			else throw new MemoryDataLoadingException("value out of range, has to be within -32768 to 32767");
		}
		}
		else throw new MemoryDataLoadingException("too many entries, line size can take only " + LineSize+" entries");
	
		
		System.out.print(memoryEntry.getMemoryLocation() +" ");
		for(int i = 0; i<memoryEntry.getData().size(); i++)
		{
			System.out.print(memoryEntry.getData().get(i) +", ");
		}
		System.out.println();
		return memoryEntry;
	}
	
	private boolean checkVal(Integer num)
	{
		if(num>= -32768 &&num<= 32767){
			return true;
		}
		else return false;
	}

}
