package edu.ufl.pranav.simulator.stages;

import edu.ufl.pranav.Instructions.*;
import edu.ufl.pranav.simulator.entities.InstructionStage;
import edu.ufl.pranav.simulator.entities.ROBEntry;
import edu.ufl.pranav.simulator.entities.RS;
import edu.ufl.pranav.simulator.entities.Register;
import edu.ufl.pranav.simulator.units.InstructionQueue;
import edu.ufl.pranav.simulator.units.ReOrderBuffer;
import edu.ufl.pranav.simulator.units.RegisterFile;
import edu.ufl.pranav.simulator.units.ReservationStations;

/**
 * Created by pranav on 10/31/16.
 */
public class Issue {
    InstructionQueue instructionQueue;
    RegisterFile registerFile;
    ReOrderBuffer reOrderBuffer;
    ReservationStations reservationStations;
    InstructionFetch instructionFetch;

    public Issue(InstructionQueue instructionQueue, RegisterFile registerFile, ReOrderBuffer reOrderBuffer,
                 ReservationStations reservationStations, InstructionFetch instructionFetch) {
        this.instructionQueue = instructionQueue;
        this.registerFile = registerFile;
        this.reOrderBuffer = reOrderBuffer;
        this.reservationStations = reservationStations;
        this.instructionFetch = instructionFetch;
    }

    public void run(){
        // If neither is available, don't process the instruction
        if(!reservationStations.allStationsBusy() && !reOrderBuffer.bufferFull()) {
            // Get the Instruction
            Instruction instruction = instructionQueue.peek();
            if (instruction != null && instruction.getInstructionStage() == InstructionStage.READYTOISSUE) {
                instruction = instructionQueue.poll();
                // Get the Next Available Reservation Station
                RS rsEntry = reservationStations.getAvailableStation();
                rsEntry.instruction = instruction;

                // Get a new ROB entry
                ROBEntry robEntry = reOrderBuffer.getROBEntry();
                int b = robEntry.getEntryNumber();

                /**Only Shift Type instructions do not have rs involved,
                 * while rt is involved in all instructions.
                 */

                if (instruction instanceof RType) {
                    if (instruction.getInstructionType() == InstructionType.BREAK ||
                            instruction.getInstructionType() == InstructionType.NOP) {
                        robEntry.setReady();
                        instruction.setInstructionStage(InstructionStage.WRITTEN);
                    } else {
                        String rt = ((RType) instruction).getRt();
                        Register r_t = registerFile.getRegister(rt);
                        rsEntry.setOp(instruction.getInstructionName());
                        rsEntry.setType(instruction.getInstructionType());

                        if (r_t.isBusy()) {
                            int rt_rob = r_t.getROBNumber();
                            ROBEntry entry_rt = reOrderBuffer.get(rt_rob);
                            if (entry_rt.isReady()) {
                                rsEntry.setVk(entry_rt.getValue());
                                rsEntry.setQk(0);
                            } else {
                                rsEntry.setQk(rt_rob);
                            }
                        } else {
                            rsEntry.setVk(r_t.getValue());
                            rsEntry.setQk(0);
                        }

                        if (instruction.getInstructionType() != InstructionType.SHIFT) {
                            String rs = ((RType) instruction).getRs();
                            Register r_s = registerFile.getRegister(rs);
                            if (r_s.isBusy()) {
                                int rs_rob = r_s.getROBNumber();
                                ROBEntry entry_rs = reOrderBuffer.get(rs_rob);
                                if (entry_rs.isReady()) {
                                    rsEntry.setVj(entry_rs.getValue());
                                    rsEntry.setQj(0);
                                } else {
                                    rsEntry.setQj(rs_rob);
                                }
                            } else {
                                rsEntry.setVj(r_s.getValue());
                                rsEntry.setQj(0);
                            }
                            rsEntry.setA(((RType) instruction).getShift());
                        }


                        // Set the Destination
                        String rd = ((RType) instruction).getRd();
                        Register r_d = registerFile.getRegister(rd);
                        r_d.setROBNumber(b);
                        r_d.setBusy();
                        robEntry.setDestinationRegister(rd);
                    }
                }
                // It's a Immediate Instruction/Load/Store
                else if (instruction instanceof IType) {
                    rsEntry.setA(((IType) instruction).getImmediate());
                    rsEntry.setOp(instruction.getInstructionName());
                    rsEntry.setType(instruction.getInstructionType());

                    // Set up the rs register here
                    String rs = ((IType) instruction).getRs();
                    Register r_s = registerFile.getRegister(rs);
                    if (r_s.isBusy()) {
                        int rs_rob = r_s.getROBNumber();
                        ROBEntry entry_rs = reOrderBuffer.get(rs_rob);
                        if (entry_rs.isReady()) {
                            rsEntry.setVj(entry_rs.getValue());
                            rsEntry.setQj(0);
                        } else {
                            rsEntry.setQj(rs_rob);
                        }
                    } else {
                        rsEntry.setVj(r_s.getValue());
                        rsEntry.setQj(0);
                    }

                    // Additional Status updates for Loads
                    if (instruction.getInstructionType() == InstructionType.LOAD) {
                        // Set the Destination Address As Busy
                        String rt = ((IType) instruction).getRt();
                        Register r_t = registerFile.getRegister(rt);
                        r_t.setROBNumber(b);
                        r_t.setBusy();
                        robEntry.setDestinationRegister(rt);
                    } else if (instruction.getInstructionType() == InstructionType.STORE) {

                        String rt = ((IType) instruction).getRt();
                        Register r_t = registerFile.getRegister(rt);

                        if (r_t.isBusy()) {
                            int rt_rob = r_t.getROBNumber();
                            ROBEntry entry_rt = reOrderBuffer.get(rt_rob);
                            if (entry_rt.isReady()) {
                                rsEntry.setVk(entry_rt.getValue());
                                rsEntry.setQk(0);
                            } else {
                                rsEntry.setQk(rt_rob);
                            }
                        } else {
                            rsEntry.setVk(r_t.getValue());
                            rsEntry.setQk(0);
                        }

                    } else if (instruction.getInstructionType() == InstructionType.BRANCH) {
                        if (instruction.getInstructionName().equals("BNE") ||
                                instruction.getInstructionName().equals("BEQ")) {

                            String rt = ((IType) instruction).getRt();
                            Register r_t = registerFile.getRegister(rt);

                            if (r_t.isBusy()) {
                                int rt_rob = r_t.getROBNumber();
                                ROBEntry entry_rt = reOrderBuffer.get(rt_rob);
                                if (entry_rt.isReady()) {
                                    rsEntry.setVk(entry_rt.getValue());
                                    rsEntry.setQk(0);
                                } else {
                                    rsEntry.setQk(rt_rob);
                                }
                            } else {
                                rsEntry.setVk(r_t.getValue());
                                rsEntry.setQk(0);
                            }
                        }
                    } else if (instruction.getInstructionType() == InstructionType.IMMEDIATE) {
                        // Set the Destination Address As Busy
                        String rt = ((IType) instruction).getRt();
                        Register r_t = registerFile.getRegister(rt);
                        r_t.setROBNumber(b);
                        r_t.setBusy();
                        robEntry.setDestinationRegister(rt);
                    }
                } else if (instruction instanceof JType) {
                    rsEntry.setOp(instruction.getInstructionName());
                    rsEntry.setType(instruction.getInstructionType());
                    rsEntry.setA(((JType) instruction).getTarget());
                }


                if(instruction.getInstructionType() != InstructionType.NOP &&
                        instruction.getInstructionType() != InstructionType.BREAK) {
                    rsEntry.setPC(instruction.getPC());
                    rsEntry.setBusy();
                    rsEntry.setROBEntry(b);
                    instruction.setInstructionStage(InstructionStage.ISSUED);
                }
                robEntry.setNotReady();
                robEntry.setInstruction(instruction);
                if(instructionQueue.peek() != null) {
                    robEntry.currentPC = instructionQueue.peek().getPC();
                }else{
                    robEntry.currentPC = instruction.getPC();
                }
                reOrderBuffer.add(robEntry);

            }
        }
    }
}
