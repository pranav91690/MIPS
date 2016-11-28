package edu.ufl.pranav.simulator.entities;

/**
 * Created by soumya on 11/25/16.
 */
public class BTBEntry implements Comparable<BTBEntry> {
    int instruction_pc;
    int target_pc;
    int predictor;
    int timesHit;

    public BTBEntry(int instruction_pc,int target_pc,int predictor,int timesHit ) {
        this.instruction_pc = instruction_pc;
        this.timesHit = timesHit;
        this.predictor = predictor;
        this.target_pc = target_pc;
    }

    public int getInstruction_pc() {
        return instruction_pc;
    }

    public int getTarget_pc() {
        return target_pc;
    }

    public int getPredictor() {
        return predictor;
    }

    public int getTimesHit(){
        return timesHit;
    }

    public void incrementTimesHit(){
        this.timesHit += 1;
    }

    public void setPredictor(int predictor) {
        this.predictor = predictor;
    }

    public void setTarget_PC(int target_pc){
        this.target_pc = target_pc;
    }

    public int compareTo(BTBEntry other){
        return Integer.compare(this.getInstruction_pc(),other.getInstruction_pc());
    }
}
