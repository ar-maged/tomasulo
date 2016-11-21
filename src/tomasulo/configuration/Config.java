package tomasulo.configuration;

import tomasulo.configuration.action.FunctionalUnitsConfig;
import tomasulo.configuration.memory.MemoryConfig;

public class Config {

	private int pipelineWidth;
	private int instructionBufferSize;
	private int reorderBufferSize;

	private MemoryConfig memoryConfig;
	private FunctionalUnitsConfig functionalUnitsConfig;

	public Config() {
		memoryConfig = new MemoryConfig();
		functionalUnitsConfig = new FunctionalUnitsConfig();
	}

	public int getPipelineWidth() {
		return pipelineWidth;
	}

	public void setPipelineWidth(int pipelineWidth) {
		this.pipelineWidth = pipelineWidth;
	}

	public int getInstructionBufferSize() {
		return instructionBufferSize;
	}

	public void setInstructionBufferSize(int instructionBufferSize) {
		this.instructionBufferSize = instructionBufferSize;
	}

	public int getReorderBufferSize() {
		return reorderBufferSize;
	}

	public void setReorderBufferSize(int reorderBufferSize) {
		this.reorderBufferSize = reorderBufferSize;
	}

	public MemoryConfig getMemoryConfig() {
		return memoryConfig;
	}

	public void setMemoryConfig(MemoryConfig memoryConfig) {
		this.memoryConfig = memoryConfig;
	}

	public FunctionalUnitsConfig getFunctionalUnitsConfig() {
		return functionalUnitsConfig;
	}

	public void setFunctionalUnitsConfig(FunctionalUnitsConfig functionalUnitsConfig) {
		this.functionalUnitsConfig = functionalUnitsConfig;
	}

}
