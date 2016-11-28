package edu.ufl.pranav.simulator.stages;

import edu.ufl.pranav.Instructions.InstructionType;
import edu.ufl.pranav.simulator.entities.InstructionStage;
import edu.ufl.pranav.simulator.entities.ROBEntry;
import edu.ufl.pranav.simulator.entities.RS;
import edu.ufl.pranav.simulator.units.ReOrderBuffer;
import edu.ufl.pranav.simulator.units.ReservationStations;

import java.util.ArrayList;

/**
 * Created by pranav on 10/31/16.
 */
public class WriteResult {
    ReservationStations reservationStations;
    ReOrderBuffer reOrderBuffer;

    public WriteResult(ReservationStations reservationStations, ReOrderBuffer reOrderBuffer) {
        this.reservationStations = reservationStations;
        this.reOrderBuffer = reOrderBuffer;
    }

    public void run(){
        ArrayList<RS> stations = reservationStations.getAllStations();
        for(RS station : stations){
            // Check for only stations that are busy
            if(station.isBusy()) {
                int robEntry = station.getROBEntry();
                ROBEntry entry = reOrderBuffer.get(robEntry);
                InstructionType instructionType = station.getType();
                // Check if the result has been executed and ready to write, Skip for Loads/Store
                if (station.instruction.getInstructionStage() == InstructionStage.READYTOWRITE &&
                        station.isResultReady()  &&
                        (   instructionType != InstructionType.STORE && instructionType != InstructionType.BRANCH &&
                            instructionType != InstructionType.JUMP)){
                    long value = station.getResult();

                    // For Each Station, if any of the operands is waiting for the result,
                    // give them it.
                    for (RS st : stations) {
                        // This takes one cycle - how to implement this simulation
                        if (st.isBusy() && st != station) {
                            if (st.getQj() == robEntry) {
                                st.setVj(value);
                                st.setQj(0);
                            }

                            if (st.getQk() == robEntry) {
                                st.setVk(value);
                                st.setQk(0);
                            }
                        }
                    }
                    entry.setValue(value);
                    entry.setResultReady(true);
                    entry.setReady();
                    station.instruction.setInstructionStage(InstructionStage.WRITTEN);
                }
            }
        }
    }
}
