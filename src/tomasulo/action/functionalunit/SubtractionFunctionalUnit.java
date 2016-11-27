package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.InstructionName;

public class SubtractionFunctionalUnit extends FunctionalUnit {

	public SubtractionFunctionalUnit(FunctionalUnitConfig config) {
		super(config);
	}

	public void execute(InstructionName operation, Integer Vj, Integer Vk, Integer addressOrImmediateValue) {
		
		if (operation.equals(InstructionName.SUB)) {
			this.result = Vj - Vk;
		}
	}

}
