package edu.ufl.pranav.simulator.entities;

import edu.ufl.pranav.Instructions.Instruction;
import edu.ufl.pranav.Instructions.InstructionType;

/**
 * Created by pranav on 11/19/16.
 */
public class RS {
    String op;
    InstructionType type;
    public Instruction instruction;
    boolean busy;
    int Qj;
    int Qk;
    long Vj;
    long Vk;
    long A;
    int ROBEntry;
    long result;
    boolean resultReady;
    boolean loadStoreStageOneDone;
    boolean branchOutcome;
    int PC;

    public void setOp(String op) {
        this.op = op;
    }

    public void setType(InstructionType type){
        this.type = type;
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

    public void setResult(long result) {
        this.result = result;
    }

    public void setPC(int PC){
        this.PC = PC;
    }

    public boolean getBranchOutcome(){
        return this.branchOutcome;
    }

    public String getOp() {
        return op;
    }

    public InstructionType getType(){
        return this.type;
    }

    public int getQj() {
        return Qj;
    }

    public int getQk() {
        return Qk;
    }

    public long getVk() {
        return Vk;
    }

    public int getROBEntry() {
        return ROBEntry;
    }

    public long getResult(){
        return result;
    }

    public int getPC(){
        return this.PC;
    }

    public boolean isBusy(){
        return this.busy;
    }

    public boolean isResultReady(){
        return this.resultReady;
    }

    public void setBusy(){
        this.busy = true;
    }

    public void setResultReady(){
        this.resultReady  = true;
    }

    public void clearStation(){
        op = "";
        type = null;
        busy = false;
        Qj = 0;
        Qk = 0;
        Vj  = 0;
        Vk = 0;
        A  = 0;
        ROBEntry = 0;
        result = 0;
        resultReady = false;
        loadStoreStageOneDone = false;
        branchOutcome = false;
        PC = 0;
    }

    public Long execute(){
        // Based on the operation, execute and return the result
        if(op.equals("ADD") || op.equals("ADDU") || op.equals("ADDI") || op.equals("ADDIU")){
            if(op.equals("ADD") || op.equals("ADDU")){
                return Vj + Vk;
            }
            if(op.equals("ADDI") || op.equals("ADDIU")){
                return Vj + A;
            }
        }else if(op.equals("SUB") || op.equals("SUBU")){
            return Vj - Vk;
        }else if(op.equals("AND")){
            return (Vj & Vk);
        }else if(op.equals("OR")){
            return (Vj | Vk);
        }else if(op.equals("XOR")){
            return (Vj ^ Vk);
        }else if(op.equals("NOR")){
            return ~(Vj ^ Vk);
        }else if(op.equals("SLL")){
            return Vk << A;
        }else if(op.equals("SRL") || op.equals("SRA")){
            return Vk >> A;
        }else if(op.equals("SLT") || op.equals("SLTU") || op.equals("SLTI")) {
            if(op.equals("SLT") || op.equals("SLTU")){
               if(Vj < Vk){
                   return 1l;
               }else{
                   return 0l;
               }
            }else if(op.equals("SLTI")){
                if(Vj < A){
                    return 1l;
                }else{
                    return 0l;
                }
            }
        }else if(op.equals("SW")){
            return Vj + A;
        }else if(op.equals("LW")){
            return Vj + A;
        }else if(op.equals("J")){
            branchOutcome = true;
            return A;
        }else if(op.equals("BEQ")){
            if(Vj == Vk){
                branchOutcome = true;
                return A;
            }else{
                branchOutcome = false;
                return 4l;
            }
        }else if(op.equals("BNE")){
            if(Vj != Vk){
                branchOutcome = true;
                return A;
            }else{
                branchOutcome = false;
                return 4l;
            }
        }else if(op.equals("BGEZ")){
            if(Vj >= 0l){
                branchOutcome = true;
                return A;
            }else{
                branchOutcome = false;
                return 4l;
            }
        }else if(op.equals("BGTZ")){
            if(Vj > 0l){
                branchOutcome = true;
                return A;
            }else{
                branchOutcome = false;
                return 4l;
            }
        }else if(op.equals("BLEZ")){
            if(Vj <= 0l){
                branchOutcome = true;
                return A;
            }else{
                branchOutcome = false;
                return 4l;
            }
        }else if(op.equals("BLTZ")){
            if(Vj < 0l){
                branchOutcome = true;
                return A;
            }else {
                branchOutcome = false;
                return 4l;
            }
        }

        return 0L;
    }
}
