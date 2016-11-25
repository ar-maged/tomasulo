package tomasulo.instructions;

import tomasulo.exceptions.InvalidInstructionException;

import java.util.ArrayList;

public class Assembler {

    public ArrayList<Instruction> parseInstructions(String[] stringInstructions) {
        ArrayList<Instruction> parsedInstructions = new ArrayList<Instruction>();

        for (String stringInstruction : stringInstructions) {
            parsedInstructions.add(parseInstruction(stringInstruction));
        }

        return parsedInstructions;
    }

    private Instruction parseInstruction(String stringInstruction) {
        Instruction instruction = new Instruction();
        String[] splitStringInstruction = stringInstruction.split(" ");

        for (int i = 0; i < splitStringInstruction.length; i++) {
            splitStringInstruction[i] = splitStringInstruction[i].trim();
        }

        switch (splitStringInstruction[0]) {
            case "ADD":
                instruction.setName(InstructionName.ADD);
                break;
            case "SUB":
                instruction.setName(InstructionName.SUB);
                break;
            case "ADDI":
                instruction.setName(InstructionName.ADDI);
                break;
            case "NAND":
                instruction.setName(InstructionName.NAND);
                break;
            case "LW":
                instruction.setName(InstructionName.LW);
                break;
            case "SW":
                instruction.setName(InstructionName.SW);
                break;
            case "MUL":
                instruction.setName(InstructionName.MUL);
                break;
            case "JMP":
                instruction.setName(InstructionName.JMP);
                break;
            case "JALR":
                instruction.setName(InstructionName.JALR);
                break;
            case "RET":
                instruction.setName(InstructionName.RET);
                break;
            case "BEQ":
                instruction.setName(InstructionName.BEQ);
                break;
            default:
                throw new InvalidInstructionException("Unsupported instruction");
        }

        if (instruction.getName().equals(InstructionName.ADD)
                || instruction.getName().equals(InstructionName.ADDI)
                || instruction.getName().equals(InstructionName.SUB)
                || instruction.getName().equals(InstructionName.MUL)
                || instruction.getName().equals(InstructionName.NAND)) {
            if (splitStringInstruction.length != 4) {
                throw new InvalidInstructionException(
                        "Erroneous number of operands ("
                                + (splitStringInstruction.length - 1)
                                + " not 3)");
            } else {
                if (!(instruction.getName().equals(InstructionName.ADDI))) {
                    instruction.setDestinationRegister(Integer
                            .parseInt((splitStringInstruction[1]
                                    .substring(1, 2))));
                    instruction.setSourceRegister1(Integer
                            .parseInt((splitStringInstruction[2]
                                    .substring(1, 2))));
                    instruction.setSourceRegister2(Integer
                            .parseInt((splitStringInstruction[3]
                                    .substring(1, 2))));
                } else {
                    instruction.setDestinationRegister(Integer
                            .parseInt((splitStringInstruction[1]
                                    .substring(1, 2))));
                    instruction.setSourceRegister1(Integer
                            .parseInt((splitStringInstruction[2]
                                    .substring(1, 2))));
                    instruction.setImmediate(Integer
                            .parseInt(splitStringInstruction[3]));
                }
            }
        } else {
            if (instruction.getName().equals(InstructionName.BEQ)
                    || instruction.getName().equals(InstructionName.LW)
                    || instruction.getName().equals(InstructionName.SW)) {
                if (splitStringInstruction.length != 4) {
                    throw new InvalidInstructionException(
                            "Erroneous number of operands ("
                                    + (splitStringInstruction.length - 1)
                                    + " not 3)");
                } else {
                    switch (instruction.getName()) {
                        case LW:
                            instruction.setDestinationRegister(Integer
                                    .parseInt((splitStringInstruction[1].substring(
                                            1, 2))));
                            instruction.setSourceRegister1(Integer
                                    .parseInt((splitStringInstruction[2].substring(
                                            1, 2))));
                            instruction.setImmediate(Integer
                                    .parseInt(splitStringInstruction[3]));
                            break;
                        case SW:
                            instruction.setSourceRegister1(Integer
                                    .parseInt((splitStringInstruction[1].substring(
                                            1, 2))));
                            instruction.setSourceRegister2(Integer
                                    .parseInt((splitStringInstruction[2].substring(
                                            1, 2))));
                            instruction.setImmediate(Integer
                                    .parseInt(splitStringInstruction[3]));
                            break;
                        case BEQ:
                            instruction.setSourceRegister1(Integer
                                    .parseInt((splitStringInstruction[1].substring(
                                            1, 2))));
                            instruction.setSourceRegister2(Integer
                                    .parseInt((splitStringInstruction[2].substring(
                                            1, 2))));
                            instruction.setImmediate(Integer
                                    .parseInt(splitStringInstruction[3]));
                            break;
                        default:
                            break;
                    }
                }
            } else {
                if (instruction.getName().equals(InstructionName.JALR)
                        || instruction.getName().equals(InstructionName.JMP)) {
                    if (splitStringInstruction.length != 3) {
                        throw new InvalidInstructionException(
                                "Erroneous number of operands ("
                                        + (splitStringInstruction.length - 1)
                                        + " not 2)");
                    } else {
                        switch (instruction.getName()) {
                            case JALR:
                                instruction.setDestinationRegister(Integer
                                        .parseInt((splitStringInstruction[1]
                                                .substring(1, 2))));
                                instruction.setSourceRegister1(Integer
                                        .parseInt((splitStringInstruction[2]
                                                .substring(1, 2))));
                                break;
                            case JMP:
                                instruction.setSourceRegister1(Integer
                                        .parseInt((splitStringInstruction[1]
                                                .substring(1, 2))));
                                instruction.setImmediate(Integer
                                        .parseInt((splitStringInstruction[2])));
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    if (splitStringInstruction.length != 2) {
                        throw new InvalidInstructionException(
                                "Erroneous number of operands ("
                                        + (splitStringInstruction.length - 1)
                                        + " not 1)");
                    } else {
                        instruction.setSourceRegister1(Integer
                                .parseInt((splitStringInstruction[1].substring(
                                        1, 2))));
                    }
                }
            }

        }

        if (checkIns(instruction)) {
            System.out.println(instruction.getName() + " "
                    + instruction.getDestinationRegister() + " "
                    + instruction.getSourceRegister1() + " "
                    + instruction.getSourceRegister2() + " "
                    + instruction.getImmediate());
            return instruction;
        } else throw new InvalidInstructionException("Instruction data not valid");
    }

    private boolean checkIns(Instruction Instruction) {
        boolean valid = false;

        switch (Instruction.getName()) {
            case ADD:
                if ((checkRegs(Instruction.getDestinationRegister()))
                        && (checkRegs(Instruction.getSourceRegister1()))
                        && (checkRegs(Instruction.getSourceRegister2())))
                    valid = true;
                break;
            case MUL:
                if ((checkRegs(Instruction.getDestinationRegister()))
                        && (checkRegs(Instruction.getSourceRegister1()))
                        && (checkRegs(Instruction.getSourceRegister2())))
                    valid = true;
                break;
            case SUB:
                if ((checkRegs(Instruction.getDestinationRegister()))
                        && (checkRegs(Instruction.getSourceRegister1()))
                        && (checkRegs(Instruction.getSourceRegister2())))
                    valid = true;
                break;
            case NAND:
                if ((checkRegs(Instruction.getDestinationRegister()))
                        && (checkRegs(Instruction.getSourceRegister1()))
                        && (checkRegs(Instruction.getSourceRegister2())))
                    valid = true;
                break;
            case ADDI:
                if ((checkRegs(Instruction.getDestinationRegister()))
                        && (checkRegs(Instruction.getSourceRegister1()))
                        && (checkImm(Instruction.getImmediate())))
                    valid = true;
                break;
            case LW:
                if ((checkRegs(Instruction.getDestinationRegister()))
                        && (checkRegs(Instruction.getSourceRegister1()))
                        && (checkImm(Instruction.getImmediate())))
                    valid = true;
                break;
            case SW:
                if ((checkRegs(Instruction.getSourceRegister2()))
                        && (checkRegs(Instruction.getSourceRegister1()))
                        && (checkImm(Instruction.getImmediate())))
                    valid = true;
                break;
            case JMP:
                if ((checkRegs(Instruction.getSourceRegister1()))
                        && (checkImm(Instruction.getImmediate())))
                    valid = true;
                break;
            case BEQ:
                if ((checkRegs(Instruction.getSourceRegister2()))
                        && (checkRegs(Instruction.getSourceRegister1()))
                        && (checkImm(Instruction.getImmediate())))
                    valid = true;
                break;
            case JALR:
                if ((checkRegs(Instruction.getDestinationRegister()))
                        && (checkRegs(Instruction.getSourceRegister1())))
                    valid = true;
                break;
            case RET:
                if ((checkRegs(Instruction.getSourceRegister1())))
                    valid = true;
                break;
        }

        return valid;
    }

    private boolean checkRegs(Integer regNum) {
        switch (regNum) {
            case 0:
                return true;
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                return true;
            case 4:
                return true;
            case 5:
                return true;
            case 6:
                return true;
            case 7:
                return true;
            default:
                throw new InvalidInstructionException(
                        "Register number not defined: only from 0 to 7");
        }
    }

    private boolean checkImm(Integer immediate) {
        if (immediate >= -64 && immediate <= 63)
            return true;
        else
            throw new InvalidInstructionException("Invalid Instruction, immediate value out of range(must be from -64 till 63)");
    }

}
