package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.InstructionName;

public class AdditionFunctionalUnit extends FunctionalUnit {

	public AdditionFunctionalUnit(FunctionalUnitConfig config) {
		super(config);
	}

	public void execute(InstructionName operation, int Vj, int Vk, int addressOrImmediateValue) {
		
		if (operation.equals(InstructionName.ADD)) {
			this.result = Vj + Vk;
		}

		if (operation.equals(InstructionName.ADDI)) {
			this.result = Vj + addressOrImmediateValue;
		}
	}

}
