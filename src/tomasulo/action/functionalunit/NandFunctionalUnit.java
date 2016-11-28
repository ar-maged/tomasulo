package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.InstructionName;

public class NandFunctionalUnit extends FunctionalUnit {

	public NandFunctionalUnit(FunctionalUnitConfig config) {
		super(config);
	}

	public void execute(InstructionName operation, Integer Vj, Integer Vk, Integer addressOrImmediateValue) {
		
		if (operation.equals(InstructionName.NAND)) {
			this.result = ~(Vj & Vk);
		}
	}

}
