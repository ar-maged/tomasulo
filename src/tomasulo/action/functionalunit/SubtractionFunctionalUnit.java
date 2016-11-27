package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.Instruction;
import tomasulo.instructions.InstructionName;

public class SubtractionFunctionalUnit extends FunctionalUnit {

	public SubtractionFunctionalUnit(FunctionalUnitConfig config) {
		super(config);
	}

	public void execute(InstructionName operation, int Vj, int Vk, int addressOrImmediateValue) {
		if (operation.equals(InstructionName.SUB)) {
			this.result = Vj - Vk;
		}
	}

}
