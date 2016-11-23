package edu.ufl.pranav.simulator.stages;

import edu.ufl.pranav.Instructions.IType;
import edu.ufl.pranav.Instructions.Instruction;
import edu.ufl.pranav.Instructions.JType;
import edu.ufl.pranav.Instructions.RType;
import edu.ufl.pranav.simulator.entities.RS;
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

    public Issue(InstructionQueue instructionQueue, RegisterFile registerFile, ReOrderBuffer reOrderBuffer,
                 ReservationStations reservationStations) {
        this.instructionQueue = instructionQueue;
        this.registerFile = registerFile;
        this.reOrderBuffer = reOrderBuffer;
        this.reservationStations = reservationStations;
    }

    public void run(){
        Instruction instruction = instructionQueue.poll();
        // Get the Next Reservation Station
        // and the next ROB entry number

        // If neither is available, stall the instruction
        if(!reservationStations.allStationsBusy() && !reOrderBuffer.bufferFull()){
            // Get the next available reservation station
            int r = reservationStations.getNextAvailableStation();
            int b = reOrderBuffer.getNextIndex();

            // It's a Register-Register Instruction
            if(instruction instanceof RType){
                String rs = ((RType) instruction).getRs();
                String rt = ((RType) instruction).getRt();
                RS rsEntry = reservationStations.getStation(r);

                if(registerFile.isRegisterBusy(rs)){
                    int rs_rob = registerFile.getROBNumber(rs);
                    if(reOrderBuffer.isBufferReady(rs_rob)){
                        rsEntry.setVj(reOrderBuffer.getValue(rs_rob));
                        rsEntry.setQj(0);
                    }else{
                        rsEntry.setQj(rs_rob);
                    }

                }else{
                    rsEntry.setVj(registerFile.getRegister(rs));
                    rsEntry.setQj(0);
                }

                if(registerFile.isRegisterBusy(rt)){
                    int rt_rob = registerFile.getROBNumber(rs);
                    if(reOrderBuffer.isBufferReady(rt_rob)){
                        rsEntry.setVk(reOrderBuffer.getValue(rt_rob));
                        rsEntry.setQk(0);
                    }else{
                        rsEntry.setQk(rt_rob);
                    }
                }else{
                    rsEntry.setVk(registerFile.getRegister(rt));
                    rsEntry.setQk(0);
                }

                rsEntry.changeStatus();
                rsEntry.setROBEntry(b);


            }
            // It's a Immediate Instruction/Load/Store
            else if(instruction instanceof IType){

            }
            // It's a Jump Type Instruction
            else if(instruction instanceof JType){

            }
        }
    }
}
