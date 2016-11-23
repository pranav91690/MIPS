package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.Instructions.Instruction;
import edu.ufl.pranav.simulator.entities.RS;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pranav on 10/31/16.
 */
public class ReservationStations {
    RS[] stations = new RS[10];
    RegisterFile registerFile;
    ReOrderBuffer reOrderBuffer;
    Memory memory;

    public void issueToStation(int number, Instruction instruction){
        // Setup the Station here!!


    }

    public RS getStation(int index){
        return stations[index];
    }

    public boolean allStationsBusy(){
        boolean status = true;
        for(RS station : stations){
            if(!station.isBusy()){
                status = false;
                break;
            }
        }

        return status;
    }

    public int getNextAvailableStation(){
        for(int i = 0; i<10; i++){
            RS station = stations[i];
            if(!station.isBusy()) {
                return i;
            }
        }

        return -1;
    }

    public void execute(){
        for(RS station : stations){
            // If ready, execute the instruction,
            // else wait for the instruction

        }

    }

}
