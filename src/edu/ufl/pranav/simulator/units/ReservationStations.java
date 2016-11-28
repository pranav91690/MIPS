package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.simulator.entities.RS;

import java.util.ArrayList;

/**
 * Created by pranav on 10/31/16.
 */
public class ReservationStations {
    ArrayList<RS> stations = new ArrayList<RS>();

    public ReservationStations(){
        for(int i = 0; i<10; i++){
            stations.add(i,new RS());
        }
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

    public RS getAvailableStation(){
        for(RS station : stations){
            if(!station.isBusy()) {
                return station;
            }
        }

        return null;
    }

    public ArrayList<RS> getAllStations(){
        return stations;
    }

    public void buildOutput(StringBuilder builder){
        builder.append("RS:");
        builder.append(System.getProperty("line.separator"));
        for(RS station : stations){
            if(station.isBusy()) {
                String out = "[" + station.instruction.buildInstruction() + "]";
                builder.append(out);
                builder.append(System.getProperty("line.separator"));
            }
        }
    }
}
