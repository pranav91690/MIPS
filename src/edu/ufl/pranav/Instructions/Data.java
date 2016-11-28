package edu.ufl.pranav.Instructions;

import edu.ufl.pranav.simulator.entities.InstructionStage;

/**
 * Created by pranav on 11/2/16.
 */
public class Data implements Instruction {
    String binary;
    int PC;
    String data;
    InstructionStage stage;

    public Data(String binary, int PC, String data) {
        this.binary = binary;
        this.PC = PC;
        this.data = data;
    }

    public String getData(){
        return this.data;
    }

    public int getPC(){
        return this.PC;
    }

    public String buildOutput(){
        return binary + " " + PC + " " + data;
    }

    public InstructionType getInstructionType(){
        return InstructionType.REGISTER;
    }

    public String getInstructionName(){
        return "";
    }

    public String buildInstruction(){
        return "";
    }

    public InstructionStage getInstructionStage(){
        return stage;
    }
    public void setInstructionStage(InstructionStage stage){
        this.stage = stage;
    }
}
