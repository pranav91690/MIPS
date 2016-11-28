package edu.ufl.pranav.simulator.entities;

/**
 * Created by soumya on 11/26/16.
 */
public enum InstructionStage {
    NOSTAGE,
    INSTRUCTION_FETCHED,
    READYTOISSUE,ISSUED,
    READYTOEXECUTE,EXECUTED,
    READYTOWRITE,WRITTEN,
    READYTOCOMMIT
}
