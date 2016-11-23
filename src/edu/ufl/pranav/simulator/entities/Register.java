package edu.ufl.pranav.simulator.entities;

/**
 * Created by pranav on 11/19/16.
 */
public class Register {
    String name;
    Long value;
    int ROBNumber;
    boolean busy;

    public Register(String name, Long value) {
        this.name = name;
        this.value = value;
        busy = false;
    }

    public void setValue(Long value){
        this.value = value;
    }

    public Long getValue(){
        return this.value;
    }

    public boolean isBusy(){
        return this.busy;
    }

    public void changeStatus(){
        this.busy = !(this.busy);
    }

    public int getROBNumber() {
        return ROBNumber;
    }

    public void setROBNumber(int ROBNumber) {
        this.ROBNumber = ROBNumber;
    }
}
