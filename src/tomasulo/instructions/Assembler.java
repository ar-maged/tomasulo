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
									.parseInt((splitStringInstruction[2]
											)));
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
		System.out.println(instruction.getName() + " "
				+ instruction.getDestinationRegister() + " "
				+ instruction.getSourceRegister1() + " "
				+ instruction.getSourceRegister2() + " "
				+ instruction.getImmediate());

		return instruction;

	}

}
