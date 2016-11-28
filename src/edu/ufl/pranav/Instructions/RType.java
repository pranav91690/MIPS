package edu.ufl.pranav.Instructions;

import edu.ufl.pranav.simulator.entities.InstructionStage;

import java.math.BigInteger;

/**
 * Created by pranav on 11/2/16.
 */
public class RType implements Instruction {
    String binary;
    int PC;
    String opCode;

    String instruction;
    String rs;
    String rt;
    String rd;
    String shift;
    String function;
    InstructionType type;
    InstructionStage stage = InstructionStage.NOSTAGE;

    public RType(String binary, int PC, String opCode,
                 String instruction, String rs, String rt,
                 String rd, String shift, String function, InstructionType type) {
        this.binary = binary;
        this.PC = PC;
        this.opCode = opCode;
        this.instruction = instruction;
        this.rs = rs;
        this.rt = rt;
        this.rd = rd;
        this.shift = shift;
        this.function = function;
        this.type = type;
    }

    public String buildOutput(){
        return (this.binary + " " + this.PC + " " + buildInstruction());
    }

    public int getPC(){
        return this.PC;
    }

    public InstructionType getInstructionType(){
        return this.type;
    }

    public String getInstructionName(){
        return this.instruction;
    }

    public String buildInstruction(){
        if(this.instruction.equals("BREAK") || this.instruction.equals("NOP")){
            return this.instruction;
        }else{
            return  (instruction + " " + this.rd + ", "
                    + this.rs + ", "
                    + this.rt);
        }
    }


    public InstructionStage getInstructionStage(){
        return stage;
    }
    public void setInstructionStage(InstructionStage stage){
        this.stage = stage;
    }

    public String getRs() {
        return rs;
    }

    public String getRt() {
        return rt;
    }

    public String getRd() {
        return rd;
    }

    public long getShift() {
        return Long.parseLong(shift);
    }
}
