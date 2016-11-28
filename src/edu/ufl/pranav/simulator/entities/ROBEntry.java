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
    int entry;
    boolean ready = true;
    boolean resultReady;
    boolean addressReady;
    int address;
    long value;
    Instruction instruction;
    String destinationRegister;
    public boolean branchPrediction;
    public boolean branchOutcome;
    public int currentPC;

    public ROBEntry(int entry){
        this.entry = entry;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(){
        this.ready = true;
    }

    public void setNotReady(){
        this.ready = false;
    }

    public void setValue(long value){
        this.value = value;
    }

    public void setDestinationRegister(String destinationRegister) {
        this.destinationRegister = destinationRegister;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public void setResultReady(boolean resultReady) {
        this.resultReady = resultReady;
    }

    public void setAddressReady(boolean addressReady) {
        this.addressReady = addressReady;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public long getValue() {
        return value;
    }

    public String getDestinationRegister() {
        return destinationRegister;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public int getEntryNumber(){
        return this.entry;
    }

    public boolean isAddressReady() {
        return addressReady;
    }

    public boolean isResultReady() {
        return resultReady;
    }

    public int getAddress() {
        return address;
    }
}
