package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.Instruction;
import tomasulo.instructions.InstructionName;

public class AdditionFunctionalUnit extends FunctionalUnit {

    private int executionCycles;
    private int result;

    public AdditionFunctionalUnit(FunctionalUnitConfig additionFunctionalUnitConfig) {
        this.executionCycles = additionFunctionalUnitConfig.getExecutionCycles();
    }

    public void execute(Instruction instruction) {
        if (instruction.getName().equals(InstructionName.ADD)) {
            this.result = instruction.getSourceRegister1() + instruction.getSourceRegister2();
        }

        if (instruction.getName().equals(InstructionName.ADDI)) {
            this.result = instruction.getSourceRegister1() + instruction.getImmediate();
        }
    }

    public int getResult() {
        return result;
    }

    public int getExecutionCycles() {
        return executionCycles;
    }
}
