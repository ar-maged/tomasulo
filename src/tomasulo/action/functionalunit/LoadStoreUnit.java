package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.InstructionName;

public class LoadStoreUnit extends FunctionalUnit {

	public LoadStoreUnit(FunctionalUnitConfig config) {
		super(config);
	}

	public void execute(InstructionName operation, Integer Vj, Integer Vk, Integer addressOrImmediateValue) {
		
		if (operation.equals(InstructionName.LW) || operation.equals(InstructionName.SW))
		{
			this.result = Vj + addressOrImmediateValue;
		}
		
	}

}