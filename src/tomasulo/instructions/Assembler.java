package tomasulo.instructions;

import java.util.ArrayList;
import java.util.Scanner;

public class Assembler {

	private static String instructions;
	private static String cacheInfo;
	private static String cache2Info;
	private static int numOfInstructions;
	private ArrayList<Instruction> decodedInstructions;
	private ArrayList<ArrayList<String>> decodedCacheInfo;
	private int ROBentries;

	public Assembler() { // constructor
		decodedInstructions = new ArrayList<Instruction>();
		decodedCacheInfo = new ArrayList<ArrayList<String>>();

		Scanner sc = new Scanner(System.in);

		// CACHE 1
		System.out.println("Please enter cache geometry");
		cacheInfo = sc.nextLine();
		ArrayList<String> cache1arr = new ArrayList<String>();
		cache1arr = parseCacheInfo(cacheInfo);

		if (cache1arr != null) {
			decodedCacheInfo.add(cache1arr);
		}

		// CACHE 2
		System.out.print("Do you want another level of cache?");
		String newcache = sc.nextLine();

		if (newcache.equalsIgnoreCase("yes")
				|| newcache.equalsIgnoreCase("true")) {
			System.out.println("Please enter cache 2 geometry");

			cache2Info = sc.nextLine();
			ArrayList<String> cache2arr = new ArrayList<String>();
			cache2arr = parseCacheInfo(cache2Info);

			if (cache2arr != null)
				decodedCacheInfo.add(cache2arr);
		}

		// ROB ENTRIES
		System.out.println("Please enter number of ROB entries");
		String ROBentries_ = sc.nextLine();
		ROBentries = Integer.parseInt(ROBentries_);

		// INSTRUCTIONS
		System.out.println("Enter the instructions"); // one instruction format
														// should be ex: ADD
														// r1,r2,r3
		instructions = sc.nextLine();
		sc.close();
		// several instructions should be ex: add r1,r2,r3 , sub r4,r5,6

		String[] instructionsSeperate = instructions.split(" , ");
		numOfInstructions = instructionsSeperate.length;

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

	public static int getNumOfInstructions() {
		return numOfInstructions;
	}

	public static String getCacheInfo() {
		return cacheInfo;
	}

	public static String getCache2Info() {
		return cache2Info;
	}

	public static ArrayList<String> parseCacheInfo(String s) {
		ArrayList<String> cacheInfoDecoded = new ArrayList<String>();

		String[] info = s.split(", ");

		for (int i = 0; i < info.length; i++) {
			System.out.println(info[i]);
			cacheInfoDecoded.add(info[i]);
		}

		return cacheInfoDecoded;
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

	public ArrayList<ArrayList<String>> getDecodedCacheInfo() {
		return decodedCacheInfo;
	}

	public int getROBentries() {
		return ROBentries;
	}

}
