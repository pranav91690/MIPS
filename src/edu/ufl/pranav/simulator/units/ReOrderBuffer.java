package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.Instructions.Instruction;
import edu.ufl.pranav.Instructions.InstructionType;
import edu.ufl.pranav.simulator.entities.InstructionStage;
import edu.ufl.pranav.simulator.entities.ROBEntry;

import java.util.ArrayList;

/**
 * Created by pranav on 10/31/16.
 */
public class ReOrderBuffer {
    ArrayList<ROBEntry> entries  = new ArrayList<ROBEntry>();
    int entry = 0;

    public ROBEntry getROBEntry(){
        if(entries.size() < 6){
            this.entry += 1;
            return new ROBEntry(entry);
        }

        return null;
    }

    public boolean bufferFull(){
        if(entries.size() < 6){
            return false;
        }else {
            return true;
        }
    }

    public void add(ROBEntry robEntry){
        if(entries.size() < 6) {
            entries.add(robEntry);
        }
    }

    public ROBEntry peek(){
        if(entries.size() != 0){
            return entries.get(0);
        }

        return null;
    }

    public void poll(){
        if(entries.size() != 0) {
            entries.remove(0);
        }
    }

    public void removeAllEntriesAfterBranch(){
        entries.clear();
    }

    public boolean isHeadReadyToCommit(){
        return entries.get(0).getInstruction().getInstructionStage() == InstructionStage.READYTOCOMMIT;
    }

    public int size(){
        return entries.size();
    }

    public ROBEntry get(int entryNumber){
        for(ROBEntry entry : entries){
            if (entry.getEntryNumber() == entryNumber){
                return entry;
            }
        }

        return null;
    }

    public boolean earlierStoresAndLoadsAddressCalculated(int robNumber){
        boolean status = true;
        for(ROBEntry entry : entries){
            if(entry.getEntryNumber() == robNumber){
                break;
            }

            InstructionType type = entry.getInstruction().getInstructionType();
            if(type == InstructionType.LOAD || type == InstructionType.STORE){
                if(!entry.isAddressReady()){
                    status = false;
                }
            }
        }

        return status;
    }

    public boolean noEarlierStoreWithMatchingAddress(int robNumber, int address){
        boolean status = true;
        for(ROBEntry entry : entries){
            if(entry.getEntryNumber() == robNumber){
                break;
            }

            InstructionType type = entry.getInstruction().getInstructionType();
            if(type == InstructionType.STORE){
                if(entry.getAddress() == address){
                    status = false;
                }
            }
        }

        return status;
    }

    public void buildOutput(StringBuilder builder){
        builder.append("ROB:");
        builder.append(System.getProperty("line.separator"));
        for(ROBEntry entry : entries){
            String out = "[" + entry.getInstruction().buildInstruction() + "]";
            builder.append(out);
            builder.append(System.getProperty("line.separator"));
        }
    }
}
