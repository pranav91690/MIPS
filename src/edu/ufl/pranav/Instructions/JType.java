package edu.ufl.pranav.Instructions;

import edu.ufl.pranav.simulator.entities.InstructionStage;

/**
 * Created by pranav on 11/2/16.
 */
public class JType  implements Instruction{
    String binary;
    int PC;
    String opCode;

    String instruction;
    String target;
    InstructionType type;
    InstructionStage stage = InstructionStage.NOSTAGE;

    public JType(String binary, int PC, String opCode, String instruction, String target, InstructionType type) {
        this.binary = binary;
        this.PC = PC;
        this.opCode = opCode;
        this.instruction = instruction;
        this.target = target;
        this.type = type;
    }

    /**
     * Output will slightly differ based on the iType instructions
     * Difference
     * @return
     */
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
        return this.instruction + " " + "#"
                + target;
    }

    public InstructionStage getInstructionStage(){
        return stage;
    }
    public void setInstructionStage(InstructionStage stage){
        this.stage = stage;
    }

    public int getBranchTarget(){
        return Integer.parseInt(target);
    }


    public Long getTarget(){
        return Long.parseLong(target);
    }
}
