package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.Instruction;
import tomasulo.instructions.InstructionName;

public class NandFunctionalUnit extends FunctionalUnit {

	public NandFunctionalUnit(FunctionalUnitConfig config) {
		super(config);
	}

	public void execute(Instruction instruction) {
		if (instruction.getName().equals(InstructionName.NAND)) {
			this.result = ~(instruction.getSourceRegister1() & instruction.getSourceRegister2());
		}
	}

}
