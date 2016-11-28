package edu.ufl.pranav.simulator.stages;

import edu.ufl.pranav.Instructions.*;
import edu.ufl.pranav.Instructions.JType;
import edu.ufl.pranav.simulator.entities.*;
import edu.ufl.pranav.simulator.units.*;

/**
 * Created by pranav on 10/31/16.
 */
public class Commit {
    ReOrderBuffer reOrderBuffer;
    ReservationStations reservationStations;
    RegisterFile registerFile;
    Memory memory;
    InstructionFetch instructionFetch;
    BranchTargetBuffer branchTargetBuffer;
    InstructionQueue instructionQueue;

    public Commit(ReOrderBuffer reOrderBuffer, ReservationStations reservationStations, RegisterFile registerFile,
                  Memory memory, InstructionFetch instructionFetch,BranchTargetBuffer branchTargetBuffer,
                  InstructionQueue instructionQueue) {
        this.reOrderBuffer = reOrderBuffer;
        this.reservationStations = reservationStations;
        this.registerFile = registerFile;
        this.memory = memory;
        this.instructionFetch = instructionFetch;
        this.branchTargetBuffer = branchTargetBuffer;
        this.instructionQueue = instructionQueue;
    }

    /**
     * Get the head of the ReOrder Buffer. Commit if it ready
     *
     */
    public boolean run(){
        boolean end = false;
       if(reOrderBuffer.size() != 0 && reOrderBuffer.isHeadReadyToCommit()){
           ROBEntry head = reOrderBuffer.peek();
           int robEntryNumber = head.getEntryNumber();
           String destination = head.getDestinationRegister();
           Instruction ins = head.getInstruction();
           InstructionType instructionType = ins.getInstructionType();

           if(instructionType == InstructionType.BRANCH){
               int target_pc = (int)head.getValue();
               // Outcome and Prediction are Both True
                if(head.branchOutcome != head.branchPrediction){
                   // Set the PC as actual PC and move on
                   instructionFetch.updatePC(target_pc);
                   reOrderBuffer.removeAllEntriesAfterBranch();
                   instructionQueue.clear();
                    // Clear the Corresponding Reservation Station
                    for(RS station : reservationStations.getAllStations()){
                        if(station.isBusy()){
                            station.clearStation();
                        }
                    }
               }
           }else if(instructionType == InstructionType.STORE){
               int address = head.getAddress();
               memory.setData(address,head.getValue());
           }else if(instructionType == InstructionType.IMMEDIATE || instructionType == InstructionType.LOAD
                   || instructionType == InstructionType.REGISTER || instructionType == InstructionType.SHIFT){
               Register r_d = registerFile.getRegister(destination);
               r_d.setValue(head.getValue());
               if(r_d.getROBNumber() == robEntryNumber){
                   r_d.setNotBusy();
               }
           }else if(instructionType == InstructionType.JUMP){
               // If not found, update the BTB
               JType instruction = (JType)head.getInstruction();
               int target_pc = (int)head.getValue();
               if(!branchTargetBuffer.containsBranch(instruction.getPC())){
                   BTBEntry new_entry;
                   new_entry = new BTBEntry(instruction.getPC(),instruction.getBranchTarget(),1, 0);
                   branchTargetBuffer.add(new_entry);
                   instructionFetch.updatePC(target_pc);
                   reOrderBuffer.removeAllEntriesAfterBranch();
                   instructionQueue.clear();
                   // Clear the Corresponding Reservation Station
                   for(RS station : reservationStations.getAllStations()){
                       if(station.isBusy()){
                           station.clearStation();
                       }
                   }
               }else{
//                   instructionFetch.updatePC(target_pc);
                   branchTargetBuffer.get(instruction.getPC()).incrementTimesHit();
               }
           }else if(instructionType == InstructionType.BREAK){
                end = true;
           }


           // Clear the Corresponding Reservation Station
           for(RS station : reservationStations.getAllStations()){
               if(station.isBusy() && station.getROBEntry() == robEntryNumber){
                   station.clearStation();
               }
           }
           // Remove the entry from the ROB
           reOrderBuffer.poll();
       }

        return end;
    }
}
