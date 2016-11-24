package tomasulo.action;

import tomasulo.configuration.action.FunctionalUnitsConfig;
import tomasulo.instructions.InstructionName;

public class ReservationStations {

	private ReservationStation[] entries;

	public ReservationStations(FunctionalUnits functionalUnits,
			FunctionalUnitsConfig config) {
		int size = config.getAdditionUnitConfig().getUnitsCount()
				+ config.getMultiplicationUnitConfig().getUnitsCount()
				+ config.getSubtractionUnitConfig().getUnitsCount()
				+ config.getNandUnitConfig().getUnitsCount() + 1;
		entries = new ReservationStation[size];
		initializeEntries(functionalUnits);

	}

	public void initializeEntries(FunctionalUnits functionalUnits) {

		int index = 0;
		for (int i = 0; i < functionalUnits.getAdditionFUs().length; i++) {
			entries[index] = new ReservationStation(
					functionalUnits.getAdditionFUs()[i]);
			index++;
		}
		for (int i = 0; i < functionalUnits.getSubtractionFUs().length; i++) {
			entries[index] = new ReservationStation(
					functionalUnits.getSubtractionFUs()[i]);
			index++;
		}
		for (int i = 0; i < functionalUnits.getMultiplicationFUs().length; i++) {
			entries[index] = new ReservationStation(
					functionalUnits.getMultiplicationFUs()[i]);
			index++;
		}
		for (int i = 0; i < functionalUnits.getNandFUs().length; i++) {
			entries[index] = new ReservationStation(
					functionalUnits.getNandFUs()[i]);
			index++;
		}
		entries[index] = new ReservationStation(
				functionalUnits.getLoadStoreFU());
	}

	public class ReservationStation {

		FunctionalUnit functionalUnit;
		boolean busy;
		InstructionName operation;
		int Vj; // valueOfRegB
		int Vk; // valueOfRegA of store and valueOfRegC of Arithmetic
		FunctionalUnit Qj;
		FunctionalUnit Qk;
		int destinationROBIndex;
		int addressOrImmediateValue; // offset then address in load and store,
										// immediate value in Arithmetic

		public ReservationStation(FunctionalUnit functionalUnit) {
			this.functionalUnit = functionalUnit;
			busy = false;
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

		public int getVj() {
			return Vj;
		}

		public void setVj(int vj) {
			Vj = vj;
		}

		public int getVk() {
			return Vk;
		}

		public void setVk(int vk) {
			Vk = vk;
		}

		public FunctionalUnit getQj() {
			return Qj;
		}

		public void setQj(FunctionalUnit qj) {
			Qj = qj;
		}

		public FunctionalUnit getQk() {
			return Qk;
		}

		public void setQk(FunctionalUnit qk) {
			Qk = qk;
		}

		public int getAddressOrImmediateValue() {
			return addressOrImmediateValue;
		}

		public void setAddressOrImmediateValue(int addressOrImmediateValue) {
			this.addressOrImmediateValue = addressOrImmediateValue;
		}

		public int getDestinationROBIndex() {
			return destinationROBIndex;
		}

		public void setDestinationROBIndex(int destinationROBIndex) {
			this.destinationROBIndex = destinationROBIndex;
		}

	}

}
