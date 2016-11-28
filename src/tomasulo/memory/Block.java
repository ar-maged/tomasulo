package tomasulo.memory;

import tomasulo.instructions.Instruction;

import static tomasulo.memory.Cache.log2;

public class Block {

    private Object[] data;
    private boolean dirtyBit = false;

    public Block(int blockSizeWords) {
        // Number of instructions per block
        this.data = new Object[blockSizeWords];
    }

    // This is the worst thing ever but it should work ;')
    public Object readInstructionOrData(int addressWords){
        String binaryAddress = String.format("%16s", Integer.toBinaryString(addressWords)).replace(' ', '0');
        int offsetBits = log2(this.data.length);
        String offsetBinary = binaryAddress.substring(binaryAddress.length() - offsetBits);
        int offsetDecimal = Integer.parseInt(offsetBinary, 2);
        return this.data[offsetDecimal];
    }

    public void addInstruction(Instruction instruction, int offset) {
        this.data[offset] = instruction;
    }

    public void addData(Integer data, int offset) {
        this.data[offset] = data;
    }

    public boolean isFull() {
        return this.data[this.data.length - 1] != null;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String dataStringified = "";
        dataStringified += "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
        for (int i = 0; i < this.data.length; i++) {
            dataStringified += i + " --->  (" + this.data[i] + ") | ";
        }
        dataStringified += "\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
        return dataStringified;
    }

    public boolean isDirtyBit() {
        return dirtyBit;
    }

    public void setDirtyBit(boolean dirtyBit) {
        this.dirtyBit = dirtyBit;
    }

}
