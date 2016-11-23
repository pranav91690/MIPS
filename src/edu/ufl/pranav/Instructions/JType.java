package edu.ufl.pranav.Instructions;
/**
 * Created by pranav on 11/2/16.
 */
public class JType  implements Instruction{
    String binary;
    int PC;
    String opCode;

    String instruction;
    String target;

    public JType(String binary, int PC, String opCode, String instruction, String target) {
        this.binary = binary;
        this.PC = PC;
        this.opCode = opCode;
        this.instruction = instruction;
        this.target = target;
    }

    /**
     * Output will slightly differ based on the iType instructions
     * Difference
     * @return
     */
    public String buildOutput(){
        return (this.binary + " " + this.PC + " " + buildInstruction());
    }

    public String buildInstruction(){
        return this.instruction + " " + "#"
                + target;
    }
}
