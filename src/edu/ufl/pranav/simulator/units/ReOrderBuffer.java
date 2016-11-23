package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.simulator.entities.ROBEntry;

import java.util.ArrayList;

/**
 * Created by pranav on 10/31/16.
 */
public class ReOrderBuffer {
    ArrayList<ROBEntry> entries  = new ArrayList<ROBEntry>();

    public boolean bufferFull(){
        if(entries.size() < 6){
            return false;
        }else {
            return true;
        }
    }

    public void add(ROBEntry robEntry){
        entries.add(robEntry);
    }

    public void poll(ROBEntry r){
        if(entries.size() != 0) {
            entries.remove(0);

        }
    }

    public ROBEntry get(int index){
        return entries.get(index);
    }

    public boolean isBufferReady(int entry){
        return entries.get(entry).isReady();
    }

    public long getValue(int entry){
        return entries.get(entry).getValue();
    }

    public int getNextIndex(){
        return entries.size();
    }

    public void setInstruction(int entry){
            
    }
}
