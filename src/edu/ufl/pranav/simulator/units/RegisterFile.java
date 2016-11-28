package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.simulator.entities.Register;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by pranav on 10/31/16.
 */
public class RegisterFile {
    /**
     * This will contain the map containing the
     * Register names and Register values
     */
    private Map<String,Register> registers = new LinkedHashMap<String, Register>();

    public RegisterFile() {
        for(int i = 0; i<=31; i++){
            String name = "R" + i;
            Register register = new Register(name,0L);
            registers.put(name,register);
        }
    }

    public Register getRegister(String name){
        return registers.get(name);
    }

    public void buildOutput(StringBuilder builder){
        builder.append("Registers:");
        builder.append(System.getProperty("line.separator"));

        int i = 0;
        builder.append("R00:");
        for(Register register : registers.values()){
            builder.append("\t");
            String value = String.valueOf(register.getValue());
            // Remove leading zeroes
            builder.append(value.replaceFirst("^0+(?!$)", ""));
            i++;
            if(i%8 == 0 && i < 32){
                builder.append(System.getProperty("line.separator"));
                String header = "R";
                if(i == 8){
                    header += "08:";
                }else{
                    header += i + ":";

                }
                builder.append(header);
            }
        }
        builder.append(System.getProperty("line.separator"));
    }
}
