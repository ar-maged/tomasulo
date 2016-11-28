package tomasulo.main;

import tomasulo.action.FunctionalUnits;
import tomasulo.action.ReorderBuffer;
import tomasulo.action.ReservationStationState;
import tomasulo.action.ReservationStations;
import tomasulo.configuration.Config;
import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.configuration.memory.CacheConfig;
import tomasulo.configuration.memory.WritingPolicy;
import tomasulo.instructions.Assembler;
import tomasulo.instructions.Instruction;
import tomasulo.instructions.InstructionBuffer;
import tomasulo.memory.Memory;
import tomasulo.registers.RegisterFile;
import tomasulo.registers.RegisterStatus;
import tomasulo.util.filesystem.FileReader;
import tomasulo.util.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException {

        /////////////// CONFIGURATIONS ///////////////
        Config config = new Config();

        // General configurations
        config.setPipelineWidth(4);
        config.setInstructionBufferSize(20);
        config.setReorderBufferSize(10);

        // Memory configurations
        config.getMemoryConfig().setBlockSizeBytes(16);
        config.getMemoryConfig().setHitWritingPolicy(WritingPolicy.ALLOCATE);
        config.getMemoryConfig().setMissWritingPolicy(WritingPolicy.AROUND);

        // Main memory configurations
        config.getMemoryConfig().getMainMemoryConfig().setAccessCycles(100);

        // Cache(s) configurations
        config.getMemoryConfig().addCacheConfig(new CacheConfig(16 * 1024, 16, 1, 5));
        config.getMemoryConfig().addCacheConfig(new CacheConfig(32 * 1024, 16, 2, 10));

        // Functional units configurations
        config.getFunctionalUnitsConfig().setAdditionUnitConfig(new FunctionalUnitConfig(2, 1));
        config.getFunctionalUnitsConfig().setSubtractionUnitConfig(new FunctionalUnitConfig(2, 1));
        config.getFunctionalUnitsConfig().setMultiplicationUnitConfig(new FunctionalUnitConfig(1, 1));
        config.getFunctionalUnitsConfig().setNandUnitConfig(new FunctionalUnitConfig(1, 1));
        config.getFunctionalUnitsConfig().setLoadUnitConfig(new FunctionalUnitConfig(1, 1));
        config.getFunctionalUnitsConfig().setBranchUnitConfig(new FunctionalUnitConfig(1, 1));

        /////////////// INIT ///////////////
        FileReader fileReader = new FileReader();
        Assembler assembler = new Assembler();
        InstructionBuffer instructionBuffer = new InstructionBuffer(config.getInstructionBufferSize());
        ReorderBuffer reorderBuffer = new ReorderBuffer(config.getReorderBufferSize());
        RegisterFile registerFile = new RegisterFile();
        RegisterStatus registerStatus = new RegisterStatus();
        Memory memory = new Memory(config.getMemoryConfig());
        FunctionalUnits functionalUnits = new FunctionalUnits(config.getFunctionalUnitsConfig());
        ReservationStations reservationStations = new ReservationStations(functionalUnits, config.getFunctionalUnitsConfig());
        Logger l = new Logger();

        Integer PC = 5;

        /////////////// PRE-EXECUTION ///////////////
        String[] stringInstructions = fileReader.readFile("assembly/arithmetic-1.asm");
        ArrayList<Instruction> instructions = assembler.parseInstructions(stringInstructions);
        //memory.loadProgram(instructions, 0);

        // for (int i = 0; i < instructions.size(); i++) {
        // System.out.println(memory.readBlock(i * blockSizeInBytes));
        // }

        /////////////// PERFORMANCE METRICS ///////////////
        l.printMetrics();

        /////////////// EXECUTION ///////////////
        // TODO: Tomasulo's algorithm

        registerFile.writeRegister(1, 2);
        registerFile.writeRegister(2, 3);

        int size = Math.min(config.getPipelineWidth(), instructions.size());
        Instruction[] instructionArray = new Instruction[size]; //testing

        for (int i = 0; i < size; i++) {

            instructionArray[i] = instructions.get(i);

            //instructionBuffer.insertInstructions(memory.readInstruction());
        }

        instructionBuffer.insertInstructions(instructionArray);

        do {
            // TODO: Handle null
            Instruction instruction = instructionBuffer.readFirstInstruction();
            ArrayList<Integer> immutableReservationStations = new ArrayList<Integer>();

            if(instruction != null){
                Integer reservationStationIndex = reservationStations.hasAvailableStation(instruction);
                Integer source1 = null;
                Integer source2 = null;
                Integer robEntrySrc1 = null;
                Integer robEntrySrc2 = null;

                if (reservationStationIndex != null && !reorderBuffer.isFull()) {
                    int robEntryIndex = reorderBuffer.addInstruction(instruction.getName(), instruction.getDestinationRegister());
                    registerStatus.setROBEntryIndex(instruction.getDestinationRegister(), robEntryIndex);

                    if (registerStatus.getROBEntryIndex(instruction.getSourceRegister1()) == null && instruction.getSourceRegister1() !=null) {
                        source1 = registerFile.readRegister(instruction.getSourceRegister1());
                    } else {
                        robEntrySrc1 = registerStatus.getROBEntryIndex(instruction.getSourceRegister1());
                    }

                    if (registerStatus.getROBEntryIndex(instruction.getSourceRegister2()) == null && instruction.getSourceRegister2() !=null) {
                        source2 = registerFile.readRegister(instruction.getSourceRegister2());
                    } else {
                        robEntrySrc2 = registerStatus.getROBEntryIndex(instruction.getSourceRegister2());
                    }

                    reservationStations.issue(instruction, reservationStationIndex, robEntryIndex, source1, source2, robEntrySrc1, robEntrySrc2);
                    instructionBuffer.removeFirstInstruction();
                }

                immutableReservationStations.add(reservationStationIndex);
            }



            for (Integer i = 0; i < reservationStations.getEntries().length; i++) {

                if (reservationStations.getEntries()[i].getState().equals(ReservationStationState.ISSUED) && !immutableReservationStations.contains(i)) {

                    HashMap<String, Integer> missed = reservationStations.missingOperand(reservationStations.getEntries()[i]);
                    HashMap<String, Integer> robResult = new HashMap<String, Integer>();

                    if (missed != null) {
                        Integer Qj = missed.get("Qj");
                        if (Qj != null && reorderBuffer.isReadyEntry(Qj)) {
                            robResult.put("Vj", reorderBuffer.getRegisterValue(Qj));
                        }

                        Integer Qk = missed.get("Qk");
                        if (Qk != null && reorderBuffer.isReadyEntry(Qk)) {
                            robResult.put("Vk", reorderBuffer.getRegisterValue(Qk));
                        }
                        reservationStations.setNotReadyOperands(robResult, i);
                        immutableReservationStations.add(i);
                    }
                }

            }

            HashMap<String, Integer> executed = reservationStations.executeExecutables(immutableReservationStations);
            Integer excluded = null;

            if (executed != null) {
                if(reorderBuffer.getTypeofEntry(executed.get("dest")).equals(ReorderBuffer.Type.LW))
                {
//                    reorderBuffer.setRegisterValue(executed.get("dest"), memory.getInstructionOrData(executed.get("value")));
                }
                else{
                    if(reorderBuffer.getTypeofEntry(executed.get("dest")).equals(ReorderBuffer.Type.SW))
                    {
//                        reorderBuffer.setRegisterValue(executed.get("dest"), memory.storeData(executed.get("value"), executed.get("Vk")));
                    }
                    else{
                        reorderBuffer.setRegisterValue(executed.get("dest"), executed.get("value"));
                        excluded = executed.get("dest");
                    }
                }


            }

            if (reorderBuffer.isHeadCommitable(excluded)) {
                registerFile.writeRegister(reorderBuffer.getRegisterIndex(reorderBuffer.getHead()), reorderBuffer.getRegisterValue(reorderBuffer.getHead()));
                registerStatus.clearROBEntryIndex(reorderBuffer.getRegisterIndex(reorderBuffer.getHead()));
                reorderBuffer.incrementHead();
            }
        } while (!instructionBuffer.isEmpty() || !reservationStations.isEmpty() || !reorderBuffer.isEmpty());

    }

}
