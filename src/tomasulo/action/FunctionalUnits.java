package tomasulo.action;


import tomasulo.action.functionalunit.*;
import tomasulo.configuration.action.FunctionalUnitsConfig;

public class FunctionalUnits {

    AddFunctionalUnit additionFUs[];
    SubtractFunctionalUnit subtractionFUs[];
    MultiplyFunctionalUnit multiplicationFUs[];
    NandFunctionalUnit nandFUs[];
    LoadStoreUnit loadStoreFU;
    BranchJumpUnit branchJumpFU;

    public FunctionalUnits(FunctionalUnitsConfig FUconfig) {
        initializeAdditionFUs(FUconfig.getAdditionUnitConfig().getExecutionCycles(),
                FUconfig.getAdditionUnitConfig().getUnitsCount());
        initializeSubtractionFUs(FUconfig.getSubtractionUnitConfig().getExecutionCycles(),
                FUconfig.getSubtractionUnitConfig().getUnitsCount());
        initializeMultiplicationFUs(FUconfig.getMultiplicationUnitConfig().getExecutionCycles(),
                FUconfig.getMultiplicationUnitConfig().getUnitsCount());
        initializeNandFUs(FUconfig.getNandUnitConfig().getExecutionCycles(),
                FUconfig.getNandUnitConfig().getUnitsCount());
        loadStoreFU = new LoadStoreUnit(FUconfig.getLoadUnitConfig().getExecutionCycles());
        branchJumpFU = new BranchJumpUnit(FUconfig.getBranchUnitConfig().getExecutionCycles());
    }

    public void initializeAdditionFUs(int executionCycles, int numberOfUnits) {
        for (int i = 0; i < numberOfUnits; i++) {
            additionFUs[i] = new AddFunctionalUnit(executionCycles);
        }
    }

    public void initializeSubtractionFUs(int executionCycles, int numberOfUnits) {
        for (int i = 0; i < numberOfUnits; i++) {
            subtractionFUs[i] = new SubtractFunctionalUnit(executionCycles);
        }
    }

    public void initializeMultiplicationFUs(int executionCycles, int numberOfUnits) {
        for (int i = 0; i < numberOfUnits; i++) {
            multiplicationFUs[i] = new MultiplyFunctionalUnit(executionCycles);
        }
    }

    public void initializeNandFUs(int executionCycles, int numberOfUnits) {
        for (int i = 0; i < numberOfUnits; i++) {
            nandFUs[i] = new NandFunctionalUnit(executionCycles);
        }
    }

    public FunctionalUnit[] getAdditionFUs() {
        return additionFUs;
    }

    public FunctionalUnit[] getSubtractionFUs() {
        return subtractionFUs;
    }

    public FunctionalUnit[] getMultiplicationFUs() {
        return multiplicationFUs;
    }

    public FunctionalUnit[] getNandFUs() {
        return nandFUs;
    }

    public FunctionalUnit getLoadStoreFU() {
        return loadStoreFU;
    }

}
