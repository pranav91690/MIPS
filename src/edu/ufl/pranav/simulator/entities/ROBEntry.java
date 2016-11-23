package edu.ufl.pranav.simulator.entities;

/**
 * Created by pranav on 11/20/16.
 */

import edu.ufl.pranav.Instructions.Instruction;

/**
 * Each entry in the ROB will have a ROB entry number
 * InstructionType indicates whether it's a branch, store, ALU/load
 * Destination is empty for branch, mem address for store, register adress for ALU/load
 * Value will store the result until the instruction commits
 * Ready will tell if the instruction is complete and value is ready or not
 */
public class ROBEntry {
    int robEntryNumber;
    boolean ready;
    Instruction instruction;
    String state;
    int destination;
    long value;


    public boolean isReady() {
        return ready;
    }

    public void changeStatus() {
        this.ready = !this.ready;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public long getValue() {
        return value;
    }




}
