package tomasulo.action;

import tomasulo.instructions.Instruction;
import tomasulo.instructions.InstructionName;

public class BranchJumpUnit extends FunctionalUnit {

    private int numberOfcycles;
    private int result;

    public BranchJumpUnit(int cycles) {
        this.numberOfcycles = cycles;
    }

    public void execute(Instruction instruction) {
        if (instruction.getName().equals(InstructionName.BEQ)) {
            if (instruction.getSourceRegister1().compareTo(instruction.getSourceRegister2()) == 0) {
                this.result = instruction.getImmediate() + 1; //+PCvalue
            }
        }

        if (instruction.getName().equals(InstructionName.JALR)) {
            //instruction.getDestinationRegister().intValue()= PCvalue+1
        }

        if (instruction.getName().equals(InstructionName.JMP)) {
            this.result = instruction.getDestinationRegister().intValue() + instruction.getImmediate() + 1; //+PCvalue;
        }
    }

    public int getResult() {
        return result;
    }

    public int getNumberOfcycles() {
        return numberOfcycles;
    }
}
