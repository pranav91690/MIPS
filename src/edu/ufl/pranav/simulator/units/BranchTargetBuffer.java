package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.simulator.entities.BTBEntry;

import java.util.*;

/**
 * Created by pranav on 10/31/16.
 */
public class BranchTargetBuffer {
    Map<Integer, BTBEntry> entries = new LinkedHashMap<Integer, BTBEntry>();

    public boolean containsBranch(int instruction_pc){
        return entries.containsKey(instruction_pc);
    }

    public BTBEntry get(int instruction_pc){
        entries.get(instruction_pc).incrementTimesHit();
        return entries.get(instruction_pc);
    }

    public void add(BTBEntry entry){
        if(entries.size() < 16){
            entries.put(entry.getInstruction_pc(),entry);
        }else{
            // Remove the Least Used Entry
            int key_to_remove = 0;
            int least = Integer.MAX_VALUE;
            for(Map.Entry<Integer,BTBEntry> e : entries.entrySet()){
                if(e.getValue().getTarget_pc() < least) {
                    key_to_remove = e.getKey();
                    least = e.getValue().getTimesHit();
                }
            }

            if(entries.containsKey(key_to_remove)){
                entries.remove(key_to_remove);
            }

            entries.put(entry.getInstruction_pc(),entry);
        }
    }

    public void buildOutput(StringBuilder builder){
        builder.append("BTB:");
        builder.append(System.getProperty("line.separator"));

        int e = 1;
        for(BTBEntry entry : entries.values()){
            String out = "[Entry " + e + "]<" + entry.getInstruction_pc() + "," + entry.getTarget_pc()
                    + "," + entry.getPredictor() + ">";

            builder.append(out);
            builder.append(System.getProperty("line.separator"));
            e += 1;
        }

    }
}
