package edu.ufl.pranav.Instructions;

import edu.ufl.pranav.simulator.entities.InstructionStage;

/**
 * Created by pranav on 11/2/16.
 */
public interface Instruction {
    String buildOutput();
    int getPC();
    InstructionType getInstructionType();
    String getInstructionName();
    String buildInstruction();
    InstructionStage getInstructionStage();
    void setInstructionStage(InstructionStage stage);
}
