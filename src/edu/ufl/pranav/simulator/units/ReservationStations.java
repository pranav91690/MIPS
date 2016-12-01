package edu.ufl.pranav.simulator.units;

import edu.ufl.pranav.simulator.entities.RS;

import java.util.ArrayList;

/**
 * Created by pranav on 10/31/16.
 */
public class ReservationStations {
    ArrayList<RS> stations = new ArrayList<RS>();

    public ReservationStations(){

    }

    public void remove(RS station){
        stations.remove(station);
    }

    public void clear(){
        stations.clear();
    }

    public boolean allStationsBusy(){
        return !(stations.size() < 10);
    }

    public RS getAvailableStation(){
        RS n = new RS();
        stations.add(n);
        return n;
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
