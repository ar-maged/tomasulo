package tomasulo.action;

import java.util.ArrayList;
import java.util.HashMap;

import tomasulo.action.functionalunit.*;
import tomasulo.configuration.action.FunctionalUnitsConfig;
import tomasulo.instructions.*;

public class ReservationStations {

	private ReservationStation[] entries;

	public ReservationStations(FunctionalUnits functionalUnits, FunctionalUnitsConfig config){
		int size = config.getAdditionUnitConfig().getUnitsCount() + config.getMultiplicationUnitConfig().getUnitsCount() 
				+ config.getSubtractionUnitConfig().getUnitsCount() + config.getNandUnitConfig().getUnitsCount() + 2; 
           
		entries = new ReservationStation[size];
		initializeEntries(functionalUnits);
	}

	public void initializeEntries(FunctionalUnits functionalUnits) {
		int index = 0;

		for (int i = 0; i < functionalUnits.getAdditionFUs().length; i++) {
			entries[index++] = new ReservationStation(functionalUnits.getAdditionFUs()[i]);
		}

		for (int i = 0; i < functionalUnits.getSubtractionFUs().length; i++) {
			entries[index++] = new ReservationStation(functionalUnits.getSubtractionFUs()[i]);
		}

		for (int i = 0; i < functionalUnits.getMultiplicationFUs().length; i++) {
			entries[index++] = new ReservationStation(functionalUnits.getMultiplicationFUs()[i]);
		}

		for (int i = 0; i < functionalUnits.getNandFUs().length; i++) {
			entries[index++] = new ReservationStation(functionalUnits.getNandFUs()[i]);
		}

		entries[index++] = new ReservationStation(functionalUnits.getLoadStoreFU());
		entries[index++] = new ReservationStation(functionalUnits.getBranchJumpFU());
	}
	
	
	public ReservationStation[] getEntries() {
		return entries;
	}

	public Integer hasAvailableStation(Instruction instruction){
		
		InstructionName instructionName = instruction.getName();
		
		switch(instructionName){
			case ADDI:
			case ADD:
				for (int i = 0; i < entries.length; i++) {
					if(entries[i].getFunctionalUnit() instanceof AdditionFunctionalUnit){
						if (!entries[i].isBusy()) 
							return i;
					}
				}
				return null;
				
			case SUB:
				for (int i = 0; i < entries.length; i++) {
					if(entries[i].getFunctionalUnit() instanceof SubtractionFunctionalUnit){
						if (!entries[i].isBusy()) 
							return i;
					}
				}
				return null;
				
			case MUL:
				for (int i = 0; i < entries.length; i++) {
					if(entries[i].getFunctionalUnit() instanceof MultiplicationFunctionalUnit){
						if (!entries[i].isBusy()) 
							return i;
					}
				}
				return null;
				
			case NAND:
				for (int i = 0; i < entries.length; i++) {
					if(entries[i].getFunctionalUnit() instanceof NandFunctionalUnit){
						if (!entries[i].isBusy()) 
							return i;
					}
				}
				return null;
				
			case LW:
			case SW:
				for (int i = 0; i < entries.length; i++) {
					if(entries[i].getFunctionalUnit() instanceof LoadStoreUnit){
						if (!entries[i].isBusy()) 
							return i;
					}
				}
				return null;
				
			case JMP:
			case BEQ:
			case JALR:
				for (int i = 0; i < entries.length; i++) {
					if(entries[i].getFunctionalUnit() instanceof BranchJumpUnit){
						if (!entries[i].isBusy()) 
							return i;
					}
				}
				return null;
				
			default: return null;	
		}	
	}
	
	public void issue(Instruction instruction, int reservationStationIndex, int robEntryIndex, Integer source1, Integer source2, Integer robEntrySource1, Integer robEntrySource2 ){
		
		ReservationStation reservationStation = entries[reservationStationIndex]; 
		   
		reservationStation.setState(ReservationStationState.ISSUED);
		reservationStation.setBusy(true); 
		reservationStation.setOperation(instruction.getName()); 
		reservationStation.setDestinationROBIndex(robEntryIndex);
		
		if(instruction.getName().equals(InstructionName.LW) || instruction.getName().equals(InstructionName.SW) || 
			instruction.getName().equals(InstructionName.BEQ) || instruction.getName().equals(InstructionName.JMP) || 
			instruction.getName().equals(InstructionName.ADDI)){
			reservationStation.setAddressOrImmediateValue(instruction.getImmediate());
		}
		
		if(instruction.getName().equals(InstructionName.SW)){
			
			if (source1 != null){
				reservationStation.setVk(source1);
			}
			else{
				reservationStation.setQk(robEntrySource1);
			}
			
			if (source2 != null){
				reservationStation.setVj(source2);
			}
			else{
				reservationStation.setQj(robEntrySource2);
			}
			
			if (reservationStation.getQj() == null && reservationStation.getQk() == null){
				reservationStation.setState(ReservationStationState.READYTOEXECUTE);
			}
		}
		else{
			
			if (instruction.getName().equals(InstructionName.LW) || instruction.getName().equals(InstructionName.JMP) ||
					instruction.getName().equals(InstructionName.JALR)){
				
				if (source1 != null){
					reservationStation.setVj(source1);
				}
				else{
					reservationStation.setQj(robEntrySource1);
				}
				
				if (reservationStation.getQj() == null){
					reservationStation.setState(ReservationStationState.READYTOEXECUTE);
				}
			}
			else{
				if (source1 != null){
					reservationStation.setVj(source1);
				}
				else{
					reservationStation.setQj(robEntrySource1);
				}
				if (source2 != null){
					reservationStation.setVk(source2);
				}
				else{
					reservationStation.setQk(robEntrySource2);
				}
				
				if (reservationStation.getQj() == null && reservationStation.getQk() == null){
					reservationStation.setState(ReservationStationState.READYTOEXECUTE);
				}
				
			}
			
		}
		
	}
	public void setNotReadyOperands(HashMap <String, Integer> robResult, Integer reservationStationIndex){
		
		if (robResult != null){
			
			if(robResult.get("Vj") != null){
				entries[reservationStationIndex].setVj(robResult.get("Vj"));
				entries[reservationStationIndex].setQj(null);
			}
			if(robResult.get("Vk") != null){
				entries[reservationStationIndex].setVk(robResult.get("Vk"));
				entries[reservationStationIndex].setQk(null);
			}
			
			if (entries[reservationStationIndex].getQj() == null && entries[reservationStationIndex].getQk() == null){
				entries[reservationStationIndex].setState(ReservationStationState.READYTOEXECUTE);
			}	
			
		}
		
	}
	
	public HashMap<String, Integer> missingOperand(ReservationStation reservationStation) {
		
		boolean thereIsMissing = false;
		HashMap<String, Integer> robEntryIndex = new HashMap<String, Integer>();
		
		if(reservationStation.getQj()!=null){ 		
			robEntryIndex.put("Qj", reservationStation.getQj());
			thereIsMissing = true;
		}
		
		if(reservationStation.getQk()!=null){ 		
			robEntryIndex.put("Qk", reservationStation.getQk());
			thereIsMissing = true;
		}
		
		if (thereIsMissing) 
			return robEntryIndex;
		else 
			return null;
		
	}
	
	public HashMap<String, Integer> executeExecutables(ArrayList<Integer> immutables){ 
		
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		boolean resultCaptured = false;
		
		for (int i = 0; i < entries.length; i++){
			
			if(!immutables.contains(i)){
				
				if(entries[i].getState().equals(ReservationStationState.READYTOEXECUTE)) {
					entries[i].getFunctionalUnit().execute(entries[i].getOperation(), entries[i].getVj(), entries[i].getVk(), entries[i].getAddressOrImmediateValue()); 
					entries[i].getFunctionalUnit().incrementCyclesSpanned();
					entries[i].setState(ReservationStationState.EXECUTING); 
					
				} 
				else{
					if(entries[i].getState().equals(ReservationStationState.EXECUTING)){
						 if(entries[i].getFunctionalUnit().isDone()){
							 entries[i].setState(ReservationStationState.WANTTOWRITE);   	
						 }
						 else{
							 entries[i].getFunctionalUnit().incrementCyclesSpanned();   	
						 }
					}
					else{
						if(entries[i].getState().equals(ReservationStationState.WANTTOWRITE) && !resultCaptured){ 
							result.put("dest", entries[i].getDestinationROBIndex());
							result.put("value", entries[i].getFunctionalUnit().getResult()); 
							entries[i].clearReservationStation(); 
							resultCaptured = true;
						}
					}
					
				}
				
			}
			
		}
		if (resultCaptured){
			return result;
		}
		else 
			return null;
	}

	public class ReservationStation {

		private FunctionalUnit functionalUnit;
		private boolean busy;
		private InstructionName operation;
		private Integer Vj;
		private Integer Vk;
		private Integer Qj;
		private Integer Qk;
		private Integer destinationROBIndex;
		private Integer addressOrImmediateValue;
		private ReservationStationState state;

		public ReservationStation(FunctionalUnit functionalUnit) {
			this.functionalUnit = functionalUnit;
			busy = false;
			state = ReservationStationState.EMPTY;
			Vj = Vk = Qj = Qk = destinationROBIndex = addressOrImmediateValue =  null;
		}

		public void clearReservationStation(){
			busy = false;
			state = ReservationStationState.EMPTY;
			operation = null;
			Vj = Vk = Qj = Qk = destinationROBIndex = addressOrImmediateValue =  null;
			
		}
		
		public FunctionalUnit getFunctionalUnit() {
			return functionalUnit;
		}

		public void setFunctionalUnit(FunctionalUnit functionalUnit) {
			this.functionalUnit = functionalUnit;
		}

		public boolean isBusy() {
			return busy;
		}

		public void setBusy(boolean busy) {
			this.busy = busy;
		}

		public InstructionName getOperation() {
			return operation;
		}

		public void setOperation(InstructionName operation) {
			this.operation = operation;
		}

		public Integer getVj() {
			return Vj;
		}

		public void setVj(int vj) {
			Vj = vj;
		}

		public Integer getVk() {
			return Vk;
		}

		public void setVk(int vk) {
			Vk = vk;
		}

		public Integer getQj() {
			return Qj;
		}

		public void setQj(Integer qj) {
			Qj = qj;
		}

		public Integer getQk() {
			return Qk;
		}

		public void setQk(Integer qk) {
			Qk = qk;
		}

		public Integer getAddressOrImmediateValue() {
			return addressOrImmediateValue;
		}

		public void setAddressOrImmediateValue(int addressOrImmediateValue) {
			this.addressOrImmediateValue = addressOrImmediateValue;
		}

		public Integer getDestinationROBIndex() {
			return destinationROBIndex;
		}

		public void setDestinationROBIndex(int destinationROBIndex) {
			this.destinationROBIndex = destinationROBIndex;
		}

		public ReservationStationState getState() {
			return state;
		}

		public void setState(ReservationStationState state) {
			this.state = state;
		}
		
		

	}

}
