package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.Instructions.Instruction;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by pranav on 10/31/16.
 */
public class InstructionQueue {
    Queue<Instruction> instructions = new LinkedList<Instruction>();

    public InstructionQueue(List<Instruction> instructions) {
        this.instructions.addAll(instructions);
    }

    public Instruction peek(){
        return instructions.peek();
    }

    public Instruction poll(){
        return instructions.poll();
    }

}
