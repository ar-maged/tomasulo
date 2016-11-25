package tomasulo.configuration.action;

public class FunctionalUnitsConfig {

    private FunctionalUnitConfig additionUnitConfig;
    private FunctionalUnitConfig multiplicationUnitConfig;
    private FunctionalUnitConfig loadUnitConfig;
    private FunctionalUnitConfig storeUnitConfig;
    private FunctionalUnitConfig subtractionUnitConfig;
    private FunctionalUnitConfig nandUnitConfig;
    private FunctionalUnitConfig branchUnitConfig;

    public FunctionalUnitsConfig() {
        additionUnitConfig = new FunctionalUnitConfig();
        multiplicationUnitConfig = new FunctionalUnitConfig();
        loadUnitConfig = new FunctionalUnitConfig();
        storeUnitConfig = new FunctionalUnitConfig();
    }

    public FunctionalUnitConfig getAdditionUnitConfig() {
        return additionUnitConfig;
    }

    public void setAdditionUnitConfig(FunctionalUnitConfig additionUnitConfig) {
        this.additionUnitConfig = additionUnitConfig;
    }

    public FunctionalUnitConfig getMultiplicationUnitConfig() {
        return multiplicationUnitConfig;
    }

    public void setMultiplicationUnitConfig(FunctionalUnitConfig multiplicationUnitConfig) {
        this.multiplicationUnitConfig = multiplicationUnitConfig;
    }

    public FunctionalUnitConfig getLoadUnitConfig() {
        return loadUnitConfig;
    }

    public void setLoadUnitConfig(FunctionalUnitConfig loadUnitConfig) {
        this.loadUnitConfig = loadUnitConfig;
    }

    public FunctionalUnitConfig getStoreUnitConfig() {
        return storeUnitConfig;
    }

    public void setStoreUnitConfig(FunctionalUnitConfig storeUnitConfig) {
        this.storeUnitConfig = storeUnitConfig;
    }

    public FunctionalUnitConfig getSubtractionUnitConfig() {
        return subtractionUnitConfig;
    }

    public void setSubtractionUnitConfig(FunctionalUnitConfig subtractionUnitConfig) {
        this.subtractionUnitConfig = subtractionUnitConfig;
    }

    public FunctionalUnitConfig getNandUnitConfig() {
        return nandUnitConfig;
    }

    public void setNandUnitConfig(FunctionalUnitConfig nandUnitConfig) {
        this.nandUnitConfig = nandUnitConfig;
    }

    public FunctionalUnitConfig getBranchUnitConfig() {
        return branchUnitConfig;
    }

    public void setBranchUnitConfig(FunctionalUnitConfig branchUnitConfig) {
        this.branchUnitConfig = branchUnitConfig;
    }

}
