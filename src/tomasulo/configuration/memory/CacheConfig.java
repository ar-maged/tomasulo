package tomasulo.configuration.memory;

public class CacheConfig {

    private int sizeBytes;
    private int lineSizeBytes;
    private int associativity;
    private int accessCycles;
    private WritingPolicy writeMissPolicy;
    private WritingPolicy writeHitPolicy;

    public CacheConfig() {
    }

    public CacheConfig(int sizeBytes, int lineSizeBytes, int associativity, int accessCycles, WritingPolicy writeMissPolicy) {
        this.sizeBytes = sizeBytes;
        this.lineSizeBytes = lineSizeBytes;
        this.associativity = associativity;
        this.accessCycles = accessCycles;
        this.writeMissPolicy = writeMissPolicy;
    }

    public WritingPolicy getWriteMissPolicy() { // :''''''((
        return writeMissPolicy;
    }

    public void setWriteMissPolicy(WritingPolicy writeMissPolicy) {
        this.writeMissPolicy = writeMissPolicy;
    }

    public WritingPolicy getWriteHitPolicy() {
        return writeHitPolicy;
    }

    public void setWriteHitPolicy(WritingPolicy writeHitPolicy) {
        this.writeHitPolicy = writeHitPolicy;
    }

    public int getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(int sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public int getLineSizeBytes() {
        return lineSizeBytes;
    }

    public void setLineSizeBytes(int lineSizeBytes) {
        this.lineSizeBytes = lineSizeBytes;
    }

    public int getAssociativity() {
        return associativity;
    }

    public void setAssociativity(int associativity) {
        this.associativity = associativity;
    }

    public int getAccessCycles() {
        return accessCycles;
    }

    public void setAccessCycles(int accessCycles) {
        this.accessCycles = accessCycles;
    }

}
