package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.Instructions.Instruction;

import java.util.*;

/**
 * Created by pranav on 10/31/16.
 */
public class InstructionQueue {
    Queue<Instruction> instructions = new LinkedList<Instruction>();

    public Instruction poll(){
        if(instructions.size() != 0) {
            return instructions.poll();
        }

        return null;
    }

    public Instruction peek(){
        if(instructions.size() != 0){
            return instructions.peek();
        }

        return null;
    }

    public void add(Instruction instruction){
        instructions.add(instruction);
    }

    public void buildOutput(StringBuilder builder){
        builder.append("IQ:");
        builder.append(System.getProperty("line.separator"));
        for(Instruction instruction : instructions){
            String out = "[" + instruction.buildInstruction() + "]";
            builder.append(out);
            builder.append(System.getProperty("line.separator"));
        }
    }

    public void clear(){
        instructions.clear();
    }
}
