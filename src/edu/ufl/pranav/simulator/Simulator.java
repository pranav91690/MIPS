package edu.ufl.pranav.simulator;

import edu.ufl.pranav.Instructions.*;
import edu.ufl.pranav.simulator.stages.*;
import edu.ufl.pranav.simulator.units.InstructionQueue;
import edu.ufl.pranav.simulator.units.ReOrderBuffer;
import edu.ufl.pranav.simulator.units.RegisterFile;
import edu.ufl.pranav.simulator.units.ReservationStations;

import java.util.List;

/**
 * Created by pranav on 10/31/16.
 */
public class Simulator {
    int PC;
    int cycle;
    List<Instruction> instructions;
    boolean breakProcessed;

    /**
     * Initialize the Fuctional Units
     */
    InstructionQueue instructionQueue = new InstructionQueue(instructions);
    RegisterFile registerFile = new RegisterFile();
    ReservationStations reservationStations = new ReservationStations();
    ReOrderBuffer reOrderBuffer = new ReOrderBuffer();


    InstructionFetch instructionFetch = new InstructionFetch(instructionQueue);
    Issue issue = new Issue(instructionQueue,registerFile,reOrderBuffer,reservationStations);
    Execute execute = new Execute();
    WriteResult writeResult = new WriteResult();
    Commit commit = new Commit();
    /**
     * Initiate the Instruction Queue, Branch Target Buffer, Re Order Buffer,
     * Reservation Station, Register File and Memory. Also receive the list of
     * instructions
     *
     * Reservation Station -> is to buffer operations and operands b/w issue and execute
     * ROB is to buffer -> operations and operands b/w execute and commit
     * Memory -> is obvious
     * Branch Target Buffer ->
     * Instruction Queue -> is obvious
     * Register File ->
     * We also a need a ALU or we don't?
     *
     *

     */
    public Simulator(List<Instruction> instructions)
    {
        this.instructions = instructions;
        this.PC = 600;

        for(Instruction ins : instructions){
            if(ins instanceof RType ||  ins instanceof IType || ins instanceof JType){

            } else if(ins instanceof Data){

            }
        }
    }

    /**
     * Run the program until BREAK instruction is found
     */
    public void start(){

        while(!breakProcessed){
            instructionFetch.run();
            issue.run();
            execute.run();
            writeResult.run();
            commit.run();
            cycle += 1;
        }

    }
}
