package edu.ufl.pranav.simulator.stages;

import edu.ufl.pranav.Instructions.IType;
import edu.ufl.pranav.Instructions.InstructionType;
import edu.ufl.pranav.Instructions.JType;
import edu.ufl.pranav.simulator.entities.BTBEntry;
import edu.ufl.pranav.simulator.entities.InstructionStage;
import edu.ufl.pranav.simulator.entities.RS;
import edu.ufl.pranav.simulator.units.BranchTargetBuffer;
import edu.ufl.pranav.simulator.units.Memory;
import edu.ufl.pranav.simulator.units.ReOrderBuffer;
import edu.ufl.pranav.simulator.units.ReservationStations;

import java.util.ArrayList;

/**
 * Created by pranav on 10/31/16.
 */
public class Execute {
    ReservationStations reservationStations;
    Memory memory;
    BranchTargetBuffer branchTargetBuffer;
    ReOrderBuffer reOrderBuffer;
    InstructionFetch instructionFetch;

    public Execute(ReservationStations reservationStations,Memory memory,
                   BranchTargetBuffer branchTargetBuffer, ReOrderBuffer reOrderBuffer,
                   InstructionFetch instructionFetch){
        this.reservationStations = reservationStations;
        this.memory = memory;
        this.branchTargetBuffer = branchTargetBuffer;
        this.reOrderBuffer = reOrderBuffer;
        this.instructionFetch = instructionFetch;
    }


    public void run(){
        ArrayList<RS> stations = reservationStations.getAllStations();
        for(RS station : stations){
            // Check for only the stations that are busy
            if(station.isBusy()) {
                // This will depend based on the instruction Type
                InstructionType instructionType = station.getType();
                int robEntry = station.getROBEntry();
                if(instructionType != InstructionType.NOP && instructionType != InstructionType.BREAK) {
                    if (instructionType == InstructionType.LOAD) {
                        if (station.instruction.getInstructionStage() == InstructionStage.READYTOEXECUTE &&
                                station.getQj() == 0 && reOrderBuffer.earlierStoresAndLoadsAddressCalculated(robEntry)) {
                            if (reOrderBuffer.get(robEntry).isAddressReady()) {
                                int calculatedAddress = reOrderBuffer.get(robEntry).getAddress();
                                if (reOrderBuffer.noEarlierStoreWithMatchingAddress(robEntry, calculatedAddress)) {
                                    station.setResult(memory.getData(calculatedAddress));
                                    station.setResultReady();
                                    station.instruction.setInstructionStage(InstructionStage.EXECUTED);
                                }
                            } else {
                                long value = station.execute();
                                reOrderBuffer.get(robEntry).setAddress((int) value);
                                reOrderBuffer.get(robEntry).setAddressReady(true);
                            }
                        }
                    } else if (instructionType == InstructionType.STORE) {
                        if (station.instruction.getInstructionStage() == InstructionStage.READYTOEXECUTE){
                             if(station.getQj() == 0 &&
                                 reOrderBuffer.earlierStoresAndLoadsAddressCalculated(robEntry)) {
                                 long value = station.execute();
                                 reOrderBuffer.get(robEntry).setAddress((int) value);
                                 reOrderBuffer.get(robEntry).setAddressReady(true);
                             }

                             if (station.getQk() == 0) {
                                reOrderBuffer.get(robEntry).setValue(station.getVk());
                                reOrderBuffer.get(robEntry).setResultReady(true);
                             }

                            if(reOrderBuffer.get(robEntry).isAddressReady() &&
                                    reOrderBuffer.get(robEntry).isResultReady()){
                                reOrderBuffer.get(robEntry).setReady();
                                station.instruction.setInstructionStage(InstructionStage.WRITTEN);
                            }
                        }
                    } else {
                        if (station.instruction.getInstructionStage() == InstructionStage.READYTOEXECUTE &&
                                station.getQj() == 0 && station.getQk() == 0) {
                            // Get the operands from the
                            long value = station.execute();
                            station.setResult(value);
                            station.setResultReady();
                            station.instruction.setInstructionStage(InstructionStage.EXECUTED);

                            // Update only for Branch Type Instruction, not for Branch
                            if (instructionType == InstructionType.BRANCH) {
                                boolean branchOutcome = station.getBranchOutcome();
                                int instruction_pc = station.getPC();
                                int currentPC = reOrderBuffer.get(robEntry).currentPC;
                                if(!branchTargetBuffer.containsBranch(instruction_pc)){
                                    // Add the Branch
                                    BTBEntry new_entry;
                                    int predictor = (branchOutcome) ? 1 : 0;
                                    new_entry = new BTBEntry(station.getPC(),currentPC + (int)value, predictor, 0);
                                    branchTargetBuffer.add(new_entry);
                                    reOrderBuffer.get(robEntry).setValue(currentPC + value);
                                    reOrderBuffer.get(robEntry).branchPrediction = false;
                                    reOrderBuffer.get(robEntry).branchOutcome = branchOutcome;
                                    reOrderBuffer.get(robEntry).setReady();
                                    station.instruction.setInstructionStage(InstructionStage.WRITTEN);
                                }else {
                                    // Update the Branch
                                    BTBEntry entry = branchTargetBuffer.get(station.getPC());
                                    int prediction = entry.getPredictor();
                                    // Taken
                                    if (branchOutcome) {
                                        reOrderBuffer.get(robEntry).setValue(currentPC + value);
                                        // Prediction is not taken
                                        if (prediction == 0) {
                                            entry.setPredictor(1);
                                            entry.setTarget_PC(currentPC + (int) value);
                                        }
                                    } else {
                                        // Not Taken
                                        reOrderBuffer.get(robEntry).setValue(currentPC + 4);
                                        // Prediction is taken
                                        if (prediction == 1) {
                                            entry.setPredictor(0);
                                            entry.setTarget_PC(currentPC + 4);
                                        }
                                    }

                                    // Send these prediction details to the ROB as well
                                    reOrderBuffer.get(robEntry).branchPrediction = (prediction == 1);
                                    reOrderBuffer.get(robEntry).branchOutcome = branchOutcome;
                                    reOrderBuffer.get(robEntry).setReady();
                                    station.instruction.setInstructionStage(InstructionStage.WRITTEN);
                                }
                            }else if(instructionType == InstructionType.JUMP){
                                reOrderBuffer.get(robEntry).setValue(value);
                                reOrderBuffer.get(robEntry).setReady();
                                reOrderBuffer.get(robEntry).branchOutcome = true;
                                reOrderBuffer.get(robEntry).branchPrediction = true;
                                station.instruction.setInstructionStage(InstructionStage.WRITTEN);
                            }
                        }
                    }
                }
            }
        }
    }
}
