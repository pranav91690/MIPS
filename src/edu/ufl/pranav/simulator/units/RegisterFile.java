package edu.ufl.pranav.simulator.units;

import com.sun.org.apache.regexp.internal.RE;
import edu.ufl.pranav.simulator.entities.Register;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pranav on 10/31/16.
 */
public class RegisterFile {
    /**
     * This will contain the map containing the
     * register names and register values
     */
    private Map<String,Register> registers = new HashMap<String, Register>();

    public RegisterFile() {
        for(int i = 0; i<=32; i++){
            String name = "R" + i;
            Register register = new Register(name,0L);
            registers.put(name,register);
        }
    }

    public void setResgister(String name, long value){
        if(registers.containsKey(name)) {
            registers.get(name).setValue(value);
        }
    }

    public Long getRegister(String name){
        return registers.get(name).getValue();

    }

    public boolean isRegisterBusy(String name){
        return registers.get(name).isBusy();
    }

    public int getROBNumber(String name){
        return registers.get(name).getROBNumber();
    }
}
