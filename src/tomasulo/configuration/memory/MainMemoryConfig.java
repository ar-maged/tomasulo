package tomasulo.configuration.memory;

public class MainMemoryConfig {

    private final int capacityBytes;
    private int accessCycles;

    public MainMemoryConfig() {
        capacityBytes = 64 * 1024;
    }

    public MainMemoryConfig(int accessCycles) {
        this();
        this.accessCycles = accessCycles;
    }

    public int getCapacityBytes() {
        return capacityBytes;
    }

    public int getAccessCycles() {
        return accessCycles;
    }

    public void setAccessCycles(int accessCycles) {
        this.accessCycles = accessCycles;
    }

}
