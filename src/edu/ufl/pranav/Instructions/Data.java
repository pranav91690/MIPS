package edu.ufl.pranav.Instructions;

/**
 * Created by pranav on 11/2/16.
 */
public class Data implements Instruction {
    String binary;
    int PC;
    String data;

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
}
