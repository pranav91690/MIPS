package edu.ufl.pranav.simulator.stages;

import edu.ufl.pranav.Instructions.Instruction;
import edu.ufl.pranav.simulator.units.InstructionQueue;


/**
 * Created by pranav on 10/31/16.
 */
public class InstructionFetch {
    InstructionQueue instructionQueue;

    public InstructionFetch(InstructionQueue instructionQueue) {
        this.instructionQueue = instructionQueue;
    }

    /**
     * Check the instruction. also use the BTP
     * @return
     */
    public void run(){
        Instruction present = instructionQueue.peek();
        // BTB logic goes here!


    }
}
