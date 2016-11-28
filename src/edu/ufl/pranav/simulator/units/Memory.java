package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.Instructions.Data;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by pranav on 10/31/16.
 */
public class Memory {
    /**
     * This simulates the memory on the word boundary
     */

    private HashMap<Integer,Long> memory = new LinkedHashMap<Integer, Long>();


    public void addData(Data data){
        memory.put(data.getPC(), Long.parseLong(data.getData()));
    }

    public Long getData(int location){
        if(memory.containsKey(location)){
            return memory.get(location);
        }

        return Long.MAX_VALUE;
    }

    public void setData(int location, long value){
        if(memory.containsKey(location)){
            memory.put(location,value);
        }
    }

    public void buildOutput(StringBuilder builder){
        builder.append("Data Segment:");
        builder.append(System.getProperty("line.separator"));
        builder.append("716:");
        for(Long value : memory.values()){
            builder.append("\t");
            String valueString = String.valueOf(value);
            builder.append(valueString.replaceFirst("^0+(?!$)", ""));
        }
    }
}
