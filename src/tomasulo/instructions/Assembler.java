package tomasulo.instructions;

import java.util.ArrayList;

import tomasulo.exceptions.InvalidInstructionException;

public class Assembler {

	public ArrayList<Instruction> parseInstructions(String[] stringInstructions) {

		ArrayList<Instruction> parsedInstructions = new ArrayList<Instruction>();

		for (int i = 0; i < stringInstructions.length; i++) {
			parsedInstructions.add(parseInstruction(stringInstructions[i]));
		}

		return parsedInstructions;

	}

	private Instruction parseInstruction(String stringInstruction) {

		Instruction instruction = new Instruction();
		String[] splitStringInstruction = stringInstruction.split(" ");

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
		case "MULT":
			instruction.setName(InstructionName.MULT);
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

		String[] operands = splitStringInstruction[1].split(",");

		if (instruction.getName().equals(InstructionName.ADD)
				|| instruction.getName().equals(InstructionName.ADDI)
				|| instruction.getName().equals(InstructionName.SUB)
				|| instruction.getName().equals(InstructionName.MULT)
				|| instruction.getName().equals(InstructionName.NAND)) {
			if (operands.length != 3) {
				throw new InvalidInstructionException("Erroneous number of operands (" + operands.length + " not 3)");
			} else {
				if (!(instruction.getName().equals(InstructionName.ADDI))) {
					instruction.setDestinationRegister(Integer.parseInt((operands[0].substring(1, 2))));
					instruction.setSourceRegister1(Integer.parseInt((operands[1].substring(1, 2))));
					instruction.setSourceRegister2(Integer.parseInt((operands[2].substring(1, 2))));
				} else {
					instruction.setDestinationRegister(Integer.parseInt((operands[0].substring(1, 2))));
					instruction.setSourceRegister1(Integer.parseInt((operands[1].substring(1, 2))));
					instruction.setImmediate(Integer.parseInt(operands[2]));
				}
			}
		} else {
			if (instruction.getName().equals(InstructionName.BEQ)
					|| instruction.getName().equals(InstructionName.LW)
					|| instruction.getName().equals(InstructionName.SW)) {
				if (operands.length != 3) {
					throw new InvalidInstructionException("Erroneous number of operands (" + operands.length + " not 3)");
				} else {
					switch (instruction.getName()) {
					case LW:
						instruction.setDestinationRegister(Integer.parseInt((operands[0].substring(1, 2))));
						instruction.setSourceRegister1(Integer.parseInt((operands[1].substring(1, 2))));
						instruction.setImmediate(Integer.parseInt(operands[2]));
						break;
					case SW:
						instruction.setSourceRegister1(Integer.parseInt((operands[0].substring(1, 2))));
						instruction.setSourceRegister2(Integer.parseInt((operands[1].substring(1, 2))));
						instruction.setImmediate(Integer.parseInt(operands[2]));
						break;
					case BEQ:
						instruction.setSourceRegister1(Integer.parseInt((operands[0].substring(1, 2))));
						instruction.setSourceRegister2(Integer.parseInt((operands[1].substring(1, 2))));
						instruction.setImmediate(Integer.parseInt(operands[2]));
						break;
					default:
						break;
					}
				}
			} else {
				if (instruction.getName().equals(InstructionName.JALR)
						|| instruction.getName().equals(InstructionName.JMP)) {
					if (operands.length != 2) {
						throw new InvalidInstructionException("Erroneous number of operands (" + operands.length + " not 2)");
					} else {
						switch (instruction.getName()) {
						case JALR:
							instruction.setDestinationRegister(Integer.parseInt((operands[0].substring(1, 2))));
							instruction.setSourceRegister1(Integer.parseInt((operands[1].substring(1, 2))));
							break;
						case JMP:
							instruction.setSourceRegister1(Integer.parseInt((operands[0].substring(1, 2))));
							instruction.setImmediate(Integer.parseInt((operands[1].substring(1, 2))));
							break;
						default:
							break;
						}
					}
				} else {
					if (operands.length != 1) {
						throw new InvalidInstructionException("Erroneous number of operands (" + operands.length + " not 1)");
					} else {
						instruction.setSourceRegister1(Integer.parseInt((operands[0].substring(1, 2))));
					}
				}
			}

		}

		return instruction;

	}

}
