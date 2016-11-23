package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.Instructions.Data;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pranav on 10/31/16.
 */
public class Memory {
    /**
     * This simulates the memory on the word boundary
     */

    private HashMap<Integer,Long> memory = new HashMap<Integer, Long>();

    public Memory(ArrayList<Data> data){
        for(Data d : data)  {
            memory.put(d.getPC(),Long.parseLong(d.getData()));
        }
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
}
