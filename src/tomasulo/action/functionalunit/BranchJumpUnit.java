package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.Instruction;
import tomasulo.instructions.InstructionName;

public class BranchJumpUnit extends FunctionalUnit {

	public BranchJumpUnit(FunctionalUnitConfig config) {
		super(config);
	}

	public void execute(InstructionName operation, int Vj, int Vk, int addressOrImmediateValue) {
		if (operation.equals(InstructionName.BEQ)) {
			if (Vj == Vk) {
				this.result = addressOrImmediateValue + 1; // +PCvalue
			}
		}

		if (operation.equals(InstructionName.JALR)) {
//			PCvalue = Vj; 
//			this.result= PCvalue+1;
		}

		if (operation.equals(InstructionName.JMP)) {
			this.result = Vj + addressOrImmediateValue + 1; // +PCvalue;
		}
	}

}
