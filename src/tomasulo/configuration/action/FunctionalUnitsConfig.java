package tomasulo.configuration.action;

public class FunctionalUnitsConfig {

	private FunctionalUnitConfig additionUnitConfig;
	private FunctionalUnitConfig multiplicationUnitConfig;
	private FunctionalUnitConfig loadUnitConfig;
	private FunctionalUnitConfig storeUnitConfig;

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

}
