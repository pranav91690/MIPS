package edu.ufl.pranav.Instructions;

/**
 * Created by pranav on 11/2/16.
 */
public class Blank  implements Instruction{
    String binary;
    int PC;
    String data;

    public Blank(String binary, int PC, String data) {
        this.binary = binary;
        this.PC = PC;
        this.data = data;
    }

    public String buildOutput(){
        return binary + " " + PC + " " + data;
    }
}
