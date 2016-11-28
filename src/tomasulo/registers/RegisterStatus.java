package tomasulo.registers;

import java.util.Arrays;

public class RegisterStatus {

    private Integer[] registersEntries;

    public RegisterStatus() {
        registersEntries = new Integer[8];
        Arrays.fill(registersEntries, null);
    }

    public Integer getROBEntryIndex(Integer registerIndex) {
        if(registerIndex != null)
            return registersEntries[registerIndex];
        return null;
    }

    public void setROBEntryIndex(int registerIndex, int robEntryIndex) {
        registersEntries[registerIndex] = robEntryIndex;
    }

    public void clearROBEntryIndex(int registerIndex) {
        registersEntries[registerIndex] = null;
    }

}
