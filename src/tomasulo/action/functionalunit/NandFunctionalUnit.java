package tomasulo.action.functionalunit;

import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.instructions.Instruction;
import tomasulo.instructions.InstructionName;

public class NandFunctionalUnit extends FunctionalUnit {

    private int executionCycles;
    private int result;

    public NandFunctionalUnit(FunctionalUnitConfig nandFunctionalUnitConfig) {
        this.executionCycles = nandFunctionalUnitConfig.getExecutionCycles();
    }

    public void execute(Instruction instruction) {
        if (instruction.getName().equals(InstructionName.NAND)) {
            this.result = ~(instruction.getSourceRegister1() & instruction.getSourceRegister2());
        }
    }

    public int getResult() {
        return result;
    }

    public int getExecutionCycles() {
        return executionCycles;
    }

}
