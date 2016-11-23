package edu.ufl.pranav.Instructions;

/**
 * Created by pranav on 11/2/16.
 */
public class IType implements Instruction {
    String binary;
    int PC;
    String opCode;

    String instruction;
    String rs;
    String rt;
    String immediate;

    public IType(String binary, int PC, String opCode, String instruction, String rs, String rt, String immediate) {
        this.binary = binary;
        this.PC = PC;
        this.opCode = opCode;
        this.instruction = instruction;
        this.rs = rs;
        this.rt = rt;
        this.immediate = immediate;
    }

    public String buildOutput(){
        return (this.binary + " " + this.PC + " " + buildInstruction());
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
}
