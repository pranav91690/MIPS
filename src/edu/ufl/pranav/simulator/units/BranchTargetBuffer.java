package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.simulator.entities.BTBEntry;

import java.util.*;

/**
 * Created by pranav on 10/31/16.
 */
public class BranchTargetBuffer {
    private LinkedList<BTBEntry> entryList;
    Map<Integer, BTBEntry> entries;
    private final int size = 16;

    public BranchTargetBuffer() {
        entryList = new LinkedList<BTBEntry>();
        entries = new HashMap<Integer, BTBEntry>();
    }

    public boolean containsBranch(int instruction_pc){
        return entries.containsKey(instruction_pc);
    }

    public BTBEntry get(int instruction_pc){
        BTBEntry entry = entries.get(instruction_pc);
        // Move the element to the front of the list
        entryList.remove(entry);
        entryList.offerFirst(entry);
        entries.get(instruction_pc).incrementTimesHit();
        return entry;
    }



    public void add(BTBEntry entry){
        if(entries.size() < 16){
            entries.put(entry.getInstruction_pc(),entry);
            entryList.offerFirst(entry);
        }else{
            // Remove the Least Recently Entry
            entries.remove(entryList.removeLast().getInstruction_pc());
            entries.put(entry.getInstruction_pc(),entry);
        }
    }

    public void buildOutput(StringBuilder builder){
        builder.append("BTB:");
        builder.append(System.getProperty("line.separator") );

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
