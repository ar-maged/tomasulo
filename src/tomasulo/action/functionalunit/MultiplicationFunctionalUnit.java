package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.InstructionName;

public class MultiplicationFunctionalUnit extends FunctionalUnit {

	public MultiplicationFunctionalUnit(FunctionalUnitConfig config) {
		super(config);
	}

	public void execute(InstructionName operation, Integer Vj, Integer Vk, Integer addressOrImmediateValue) {
		
		if (operation.equals(InstructionName.MUL)) {
			this.result = Vj * Vk;
		}
	}

}
