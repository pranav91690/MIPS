package edu.ufl.pranav.simulator;

import edu.ufl.pranav.Instructions.*;
import edu.ufl.pranav.simulator.entities.InstructionStage;
import edu.ufl.pranav.simulator.stages.*;
import edu.ufl.pranav.simulator.units.*;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pranav on 10/31/16.
 */
public class Simulator {
    int PC;
    int cycle = 1;
    List<Instruction> instructions = new ArrayList<Instruction>();
    boolean breakProcessed = false;

    /**
     * Initialize the Fuctional Units
     */
    InstructionQueue instructionQueue = new InstructionQueue();
    RegisterFile registerFile = new RegisterFile();
    ReservationStations reservationStations = new ReservationStations();
    ReOrderBuffer reOrderBuffer = new ReOrderBuffer();
    Memory memory = new Memory();
    BranchTargetBuffer branchTargetBuffer = new BranchTargetBuffer();


    InstructionFetch instructionFetch = new InstructionFetch(instructionQueue,branchTargetBuffer);
    Issue issue = new Issue(instructionQueue,registerFile,reOrderBuffer,reservationStations,instructionFetch);
    Execute execute = new Execute(reservationStations,memory,branchTargetBuffer,reOrderBuffer,instructionFetch);
    WriteResult writeResult = new WriteResult(reservationStations,reOrderBuffer);
    Commit commit = new Commit(reOrderBuffer,reservationStations,registerFile,memory,instructionFetch,
            branchTargetBuffer,instructionQueue);
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
        this.PC = 600;

        for(Instruction ins : instructions){
            if(ins instanceof RType ||  ins instanceof IType || ins instanceof JType){
                this.instructions.add(ins);
            } else if(ins instanceof Data){
                memory.addData((Data)ins);
            }
        }

        instructionFetch.addInstructions(this.instructions);
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
            breakProcessed = commit.run();
            if(cycle >48 && cycle < 70) {
                System.out.println(printProcessorState());
            }
            cycle += 1;

            // Change the States
            for(Instruction instruction : instructions){
                InstructionStage stage = instruction.getInstructionStage();
                if(stage == InstructionStage.INSTRUCTION_FETCHED){
                    instruction.setInstructionStage(InstructionStage.READYTOISSUE);
                }else if(stage == InstructionStage.ISSUED){
                    instruction.setInstructionStage(InstructionStage.READYTOEXECUTE);
                }else if(stage == InstructionStage.EXECUTED){
                    instruction.setInstructionStage(InstructionStage.READYTOWRITE);
                }else if(stage == InstructionStage.WRITTEN){
                    instruction.setInstructionStage(InstructionStage.READYTOCOMMIT);
                }
            }
        }

        System.out.println("End of Program");
    }

    public String printProcessorState(){
        StringBuilder sb = new StringBuilder();
        // Print the Cycle Number
        String cycleString = "Cycle <" + cycle + ">:";
        sb.append(cycleString);
        sb.append(System.getProperty("line.separator"));
        // Print the IQ
        instructionQueue.buildOutput(sb);
        // Print the RS
        reservationStations.buildOutput(sb);
        // Print the ROB
        reOrderBuffer.buildOutput(sb);
        // Print the BTB
        branchTargetBuffer.buildOutput(sb);
        // Print the Registers
        registerFile.buildOutput(sb);
        // Print the Data Segment
        memory.buildOutput(sb);

        return sb.toString();
    }
}
