package edu.ufl.pranav.Instructions;

import edu.ufl.pranav.simulator.entities.InstructionStage;

/**
 * Created by pranav on 11/2/16.
 */
public class IType implements Instruction{
    String binary;
    int PC;
    String opCode;

    String instruction;
    String rs;
    String rt;
    String immediate;
    InstructionType type;
    InstructionStage stage = InstructionStage.NOSTAGE;

    public IType(String binary, int PC, String opCode, String instruction, String rs, String rt, String immediate,
                 InstructionType type) {
        this.binary = binary;
        this.PC = PC;
        this.opCode = opCode;
        this.instruction = instruction;
        this.rs = rs;
        this.rt = rt;
        this.immediate = immediate;
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

    public int getBranchTarget(){
        return Integer.parseInt(immediate);
    }

    public InstructionStage getInstructionStage(){
        return stage;
    }
    public void setInstructionStage(InstructionStage stage){
        this.stage = stage;
    }


    public String buildInstruction(){
        if(this.instruction.equals("LW") || this.instruction.equals("SW")){
            return (this.instruction + " " + rt + ", "
                    + immediate
                    + "(" + rs + ")");
        }else if(this.instruction.equals("ADDI") || this.instruction.equals("ADDIU")
                || this.instruction.equals("SLTI")){
            return (this.instruction + " " + rt + ", " + rs
                    + ", " + "#" + immediate);
        }else if(this.instruction.equals("BEQ") || this.instruction.equals("BNE")){
            return (this.instruction + " " + rs + ", " + rt
                    + ", " + "#" + immediate);
        }else{
            return (this.instruction + " " + rs +
                    ", " + immediate);
        }
    }

    public String getRt() {
        return rt;
    }

    public Long getImmediate() {
        return Long.parseLong(immediate);
    }

    public String getRs() {
        return rs;
    }
}
