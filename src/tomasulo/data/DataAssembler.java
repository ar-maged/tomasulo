package tomasulo.data;

import java.util.ArrayList;


public class DataAssembler {
	
	
	
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
		memoryEntry.memoryLocation = Integer.parseInt(entrySplitted[0]);

		String [] dataSplitted = entrySplitted[1].split(",");
		for (int i = 0; i<dataSplitted.length; i++)
		{
			memoryEntry.data.add(Integer.parseInt(dataSplitted[i]));
		}
		
		System.out.print(memoryEntry.memoryLocation +" ");
		for(int i = 0; i<memoryEntry.data.size(); i++)
		{
			System.out.print(memoryEntry.data.get(i) +",");
		}
		System.out.println();
		return memoryEntry;
	}

}
