package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.Instruction;
import tomasulo.instructions.InstructionName;

public class LoadStoreUnit extends FunctionalUnit {

	public LoadStoreUnit(FunctionalUnitConfig config) {
		super(config);
	}

	public void execute(InstructionName operation, int Vj, int Vk, int addressOrImmediateValue) {
		if (operation.equals(InstructionName.LW) || operation.equals(InstructionName.SW))
		{
			this.result = Vj + addressOrImmediateValue;
		}
		
	}

}