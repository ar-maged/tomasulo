package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.Instruction;
import tomasulo.instructions.InstructionName;

public class NandFunctionalUnit extends FunctionalUnit {

	public NandFunctionalUnit(FunctionalUnitConfig config) {
		super(config);
	}

	public void execute(InstructionName operation, int Vj, int Vk, int addressOrImmediateValue) {
		if (operation.equals(InstructionName.NAND)) {
			this.result = ~(Vj & Vk);
		}
	}

}
