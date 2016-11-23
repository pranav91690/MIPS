package edu.ufl.pranav.simulator.entities;

/**
 * Created by pranav on 11/19/16.
 */
public class RS {
    String op;
    boolean busy;
    int Qj;
    int Qk;
    long Vj;
    long Vk;
    long A;
    int ROBEntry;

    public void setOp(String op) {
        this.op = op;
    }

    public void setQj(int qj) {
        Qj = qj;
    }

    public void setQk(int qk) {
        Qk = qk;
    }

    public void setVj(long vj) {
        Vj = vj;
    }

    public void setVk(long vk) {
        Vk = vk;
    }

    public void setA(long a) {
        A = a;
    }

    public void setROBEntry(int ROBEntry) {
        this.ROBEntry = ROBEntry;
    }

    public String getOp() {
        return op;
    }

    public int getQj() {
        return Qj;
    }

    public int getQk() {
        return Qk;
    }

    public long getVj() {
        return Vj;
    }

    public long getVk() {
        return Vk;
    }

    public long getA() {
        return A;
    }

    public int getROBEntry() {
        return ROBEntry;
    }

    public boolean isBusy(){
        return this.busy;
    }

    public void changeStatus(){
        this.busy = !(this.busy);
    }

    public Long execute(){
        // Based on the operation, execute and return the result


        return 0L;
    }
}
