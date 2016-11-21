package tomasulo.instructions;

import java.util.ArrayList;
import java.util.Scanner;

public class Assembler {

	private static String instructions;
	private ArrayList<Instruction> decodedInstructions;

	public Assembler() {

		Scanner sc = new Scanner(System.in);
		decodedInstructions = new ArrayList<Instruction>();

		instructions = sc.nextLine();
		sc.close();

		String[] instructionsSeperate = instructions.split(" , ");

		Instruction ins = new Instruction();

		for (int i = 0; i < instructionsSeperate.length; i++) {
			ins = parseInstruction(instructionsSeperate[i]);

			if (ins != null)
				decodedInstructions.add(ins);
		}

		for (int i = 0; i < decodedInstructions.size(); i++) {
			System.out.println(decodedInstructions.get(i).name + " "
					+ decodedInstructions.get(i).destinationRegister + " "
					+ decodedInstructions.get(i).sourceRegister1 + " "
					+ decodedInstructions.get(i).sourceRegister2 + " "
					+ decodedInstructions.get(i).immediate);
		}
	}

	public static Instruction parseInstruction(String s) {

		Instruction instruction = new Instruction();
		String[] inst = s.split(" ");
		// instructionDecoded.add(inst[0]); // the instruction

		switch (inst[0]) {
		case "ADD":
			InstructionName add = InstructionName.ADD;
			instruction.setName(add);
			break;
		case "SUB":
			InstructionName sub = InstructionName.SUB;
			instruction.setName(sub);
			break;
		case "ADDI":
			InstructionName addi = InstructionName.ADDI;
			instruction.setName(addi);
			break;
		case "NAND":
			InstructionName nand = InstructionName.NAND;
			instruction.setName(nand);
			break;
		case "LW":
			InstructionName lw = InstructionName.LW;
			instruction.setName(lw);
			break;
		case "SW":
			InstructionName sw = InstructionName.SW;
			instruction.setName(sw);
			break;
		case "MULT":
			InstructionName mult = InstructionName.MULT;
			instruction.setName(mult);
			break;
		case "JMP":
			InstructionName jmp = InstructionName.JMP;
			instruction.setName(jmp);
			break;
		case "JALR":
			InstructionName jalr = InstructionName.JALR;
			instruction.setName(jalr);
			break;
		case "RET":
			InstructionName ret = InstructionName.RET;
			instruction.setName(ret);
			break;
		case "BEQ":
			InstructionName beq = InstructionName.BEQ;
			instruction.setName(beq);
			break;
		default:
			System.out.println("invalid instruction");
			return null;
		}

		String[] regs = inst[1].split(","); // the registers

		if (instruction.getName().equals(InstructionName.ADD)
				|| instruction.getName().equals(InstructionName.ADDI)
				|| instruction.getName().equals(InstructionName.SUB)
				|| instruction.getName().equals(InstructionName.MULT)
				|| instruction.getName().equals(InstructionName.NAND)) {
			if (regs.length != 3) {
				System.out
						.println("invalid instruction, insufficiant registers");
				return null;
			} else {
				if (!(instruction.getName().equals(InstructionName.ADDI))) {
					instruction.destinationRegister = Integer.parseInt((regs[0]
							.substring(1, 2)));
					instruction.sourceRegister1 = Integer.parseInt((regs[1].substring(
							1, 2)));
					instruction.sourceRegister2 = Integer.parseInt((regs[2].substring(
							1, 2)));
					instruction.immediate = null;
				} else {
					instruction.destinationRegister = Integer.parseInt((regs[0]
							.substring(1, 2)));
					instruction.sourceRegister1 = Integer.parseInt((regs[1].substring(
							1, 2)));
					instruction.sourceRegister2 = null;
					instruction.immediate = Integer.parseInt(regs[2]);
				}

			}
			return instruction;
		} else {
			if (instruction.getName().equals(InstructionName.BEQ)
					|| instruction.getName().equals(InstructionName.LW)
					|| instruction.getName().equals(InstructionName.SW)) {
				if (regs.length != 3) {
					System.out
							.println("invalid instruction, insufficiant registers ");
					return null;
				} else {
					switch (instruction.getName()) {
					case LW:
						instruction.destinationRegister = Integer.parseInt((regs[0]
								.substring(1, 2)));
						instruction.sourceRegister1 = Integer.parseInt((regs[1]
								.substring(1, 2)));
						instruction.sourceRegister2 = null;
						instruction.immediate = Integer.parseInt(regs[2]);
						break;
					case SW:
						instruction.destinationRegister = null;
						instruction.sourceRegister1 = Integer.parseInt((regs[0]
								.substring(1, 2)));
						instruction.sourceRegister2 = Integer.parseInt((regs[1]
								.substring(1, 2)));
						instruction.immediate = Integer.parseInt(regs[2]);
						break;
					case BEQ:
						instruction.destinationRegister = null;
						instruction.sourceRegister1 = Integer.parseInt((regs[0]
								.substring(1, 2)));
						instruction.sourceRegister2 = Integer.parseInt((regs[1]
								.substring(1, 2)));
						instruction.immediate = Integer.parseInt(regs[2]);
						break;
					default:
						break;
					}
				}
				return instruction;
			} else {
				if (instruction.getName().equals(InstructionName.JALR)
						|| instruction.getName().equals(InstructionName.JMP)) {
					if (regs.length != 2) {
						System.out
								.println("invalid instruction, insufficiant registors");
						return null;
					} else {
						switch (instruction.name) {
						case JALR:
							instruction.destinationRegister = Integer.parseInt((regs[0]
									.substring(1, 2)));
							instruction.sourceRegister1 = Integer.parseInt((regs[1]
									.substring(1, 2)));
							instruction.sourceRegister2 = null;
							instruction.immediate = null;
							break;
						case JMP:
							instruction.destinationRegister = null;
							instruction.sourceRegister1 = Integer.parseInt((regs[0]
									.substring(1, 2)));
							instruction.sourceRegister2 = null;
							instruction.immediate = Integer.parseInt((regs[1]
									.substring(1, 2)));
							break;
						default:
							break;
						}
					}
					return instruction;
				} else { // RET
					if (regs.length != 1) {
						System.out
								.println("invalid instruction, insufficiant registors");
						return null;
					} else {
						instruction.destinationRegister = null;
						instruction.sourceRegister1 = Integer.parseInt((regs[0]
								.substring(1, 2)));
						instruction.sourceRegister2 = null;
						instruction.immediate = null;
					}
					return instruction;
				}
			}

		}

	}

	public ArrayList<Instruction> getDecodedInstructions() {
		return decodedInstructions;
	}

}
