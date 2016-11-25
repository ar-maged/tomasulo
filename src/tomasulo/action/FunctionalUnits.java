package tomasulo.action;


import tomasulo.action.functionalunit.*;
import tomasulo.configuration.action.FunctionalUnitConfig;
import tomasulo.configuration.action.FunctionalUnitsConfig;

public class FunctionalUnits {

    AdditionFunctionalUnit additionFUs[];
    SubtractionFunctionalUnit subtractionFUs[];
    MultiplicationFunctionalUnit multiplicationFUs[];
    NandFunctionalUnit nandFUs[];
    LoadStoreUnit loadStoreFU;
    BranchJumpUnit branchJumpFU;

    public FunctionalUnits(FunctionalUnitsConfig functionalUnitsConfig) {
        initializeAdditionFUs(functionalUnitsConfig.getAdditionUnitConfig());
        initializeSubtractionFUs(functionalUnitsConfig.getSubtractionUnitConfig());
        initializeMultiplicationFUs(functionalUnitsConfig.getMultiplicationUnitConfig());
        initializeNandFUs(functionalUnitsConfig.getNandUnitConfig());

        loadStoreFU = new LoadStoreUnit(functionalUnitsConfig.getLoadUnitConfig());
        branchJumpFU = new BranchJumpUnit(functionalUnitsConfig.getBranchUnitConfig());
    }

    public void initializeAdditionFUs(FunctionalUnitConfig additionUnitConfig) {
        for (int i = 0; i < additionUnitConfig.getUnitsCount(); i++) {
            additionFUs[i] = new AdditionFunctionalUnit(additionUnitConfig);
        }
    }

    public void initializeSubtractionFUs(FunctionalUnitConfig subtractionUnitConfig) {
        for (int i = 0; i < subtractionUnitConfig.getUnitsCount(); i++) {
            subtractionFUs[i] = new SubtractionFunctionalUnit(subtractionUnitConfig);
        }
    }

    public void initializeMultiplicationFUs(FunctionalUnitConfig multiplicationUnitConfig) {
        for (int i = 0; i < multiplicationUnitConfig.getUnitsCount(); i++) {
            multiplicationFUs[i] = new MultiplicationFunctionalUnit(multiplicationUnitConfig);
        }
    }

    public void initializeNandFUs(FunctionalUnitConfig nandUnitConfig) {
        for (int i = 0; i < nandUnitConfig.getUnitsCount(); i++) {
            nandFUs[i] = new NandFunctionalUnit(nandUnitConfig);
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

    public FunctionalUnit getBranchJumpFU() {
        return branchJumpFU;
    }

}
