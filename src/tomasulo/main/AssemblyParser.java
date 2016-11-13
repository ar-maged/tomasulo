package tomasulo.main;

import java.util.ArrayList;
import java.util.Scanner;

public class AssemblyParser {

	static String instructions;
	static String cacheInfo;
	static String cache2Info;
	static int numOfInstructions;

	public static int getNumOfInstructions() {
		return numOfInstructions;
	}

	public static String getCacheInfo() {
		return cacheInfo;
	}

	public static String getCache2Info() {
		return cache2Info;
	}

	public static ArrayList parseInstruction(String s) {
		ArrayList instructionDecoded = new ArrayList();

		String[] inst = s.split(" ");
		instructionDecoded.add(inst[0]); // the instruction

		String[] regs = inst[1].split(","); // the registers
		for (int i = 0; i < regs.length; i++) {
			instructionDecoded.add(regs[i]);
		}

		for (int i = 0; i < instructionDecoded.size(); i++) {
			System.out.println(instructionDecoded.get(i));
		}
		return instructionDecoded;
	}

	public static void main(String[] args) // for testing
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter cache geometry");
		cacheInfo = sc.nextLine();
		System.out.print("Do you want another level of cache?");
		String newcache = sc.nextLine();
		if (newcache.equalsIgnoreCase("yes")
				|| newcache.equalsIgnoreCase("true")) {
			System.out.println("Please enter cache 2 geometry");
			cache2Info = sc.nextLine();
		}
		System.out.println("Enter the instructions"); // one instruction format
														// should be ex: add
														// r1,r2,r3
		instructions = sc.nextLine();
		// several instructions should be ex: add r1,r2,r3 . sub r4,r5,6

		String[] instructionsSeperate = instructions.split(" , ");

		numOfInstructions = instructionsSeperate.length;
		System.out.println(instructionsSeperate.length);

		for (int i = 0; i < instructionsSeperate.length; i++) {
			parseInstruction(instructionsSeperate[i]);

		}

	}

}
