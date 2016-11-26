package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.Instruction;
import tomasulo.instructions.InstructionName;

public class LoadStoreUnit extends FunctionalUnit {

	public LoadStoreUnit(FunctionalUnitConfig config) {
		super(config);
	}

	public void execute(Instruction instruction) {
		if ((instruction.getName().equals(InstructionName.LW)) || ((instruction.getName().equals(InstructionName.SW)))) {
			this.result = instruction.getSourceRegister1() + instruction.getImmediate();
		}
	}

}