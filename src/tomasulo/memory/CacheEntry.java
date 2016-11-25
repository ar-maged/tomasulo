package tomasulo.memory;

public class CacheEntry {

    private Block block;
    private int tag;
    private boolean valid;

    public CacheEntry(Block block, int tag, boolean valid) {
        this.block = block;
        this.tag = tag;
        this.valid = valid;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
