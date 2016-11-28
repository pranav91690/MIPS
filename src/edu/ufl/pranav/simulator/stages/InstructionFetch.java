package edu.ufl.pranav.simulator.stages;

import edu.ufl.pranav.Instructions.IType;
import edu.ufl.pranav.Instructions.Instruction;
import edu.ufl.pranav.Instructions.InstructionType;
import edu.ufl.pranav.Instructions.JType;
import edu.ufl.pranav.simulator.entities.BTBEntry;
import edu.ufl.pranav.simulator.entities.InstructionStage;
import edu.ufl.pranav.simulator.units.BranchTargetBuffer;
import edu.ufl.pranav.simulator.units.InstructionQueue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by pranav on 10/31/16.
 */
public class InstructionFetch {
    InstructionQueue instructionQueue;
    BranchTargetBuffer branchTargetBuffer;
    Map<Integer,Instruction> instructionMap = new HashMap<Integer, Instruction>();
    private int pc = 600;
    boolean reachedBreak;
    int breakPC;

    public InstructionFetch(InstructionQueue instructionQueue,BranchTargetBuffer branchTargetBuffer) {
        this.instructionQueue = instructionQueue;
        this.branchTargetBuffer = branchTargetBuffer;
    }

    /**
     * Check the instruction. also use the BTP
     * @return
     */
    public void updatePC(int new_pc){
        if(new_pc != breakPC){
            reachedBreak = false;
        }

        this.pc = new_pc;
    }

    public void addInstructions(List<Instruction> instructions){
        for(Instruction instruction : instructions){
            instructionMap.put(instruction.getPC(),instruction);
        }
    }

    public void run() {
        if (instructionMap.containsKey(pc)) {
            Instruction present = instructionMap.get(pc);
            if (present != null) {
                if (!reachedBreak) {
                    // The instruction can be null if the PC is invalid
                    if (present.getInstructionType() == InstructionType.BRANCH ||
                            present.getInstructionType() == InstructionType.JUMP) {
                        int instruction_pc = present.getPC();
                        if (branchTargetBuffer.containsBranch(instruction_pc)) {
                            BTBEntry btbEntry = branchTargetBuffer.get(instruction_pc);
                            if (btbEntry.getPredictor() == 1) {
                                updatePC(btbEntry.getTarget_pc());
                            } else {
                                if (instructionMap.containsKey(pc + 4)) {
                                    this.pc += 4;
                                }
                            }
                        } else {
                            if (instructionMap.containsKey(pc + 4)) {
                                this.pc += 4;
                            }
                        }
                    } else {
                        if (instructionMap.containsKey(pc + 4)) {
                            this.pc += 4;
                        }
                    }

                    present.setInstructionStage(InstructionStage.INSTRUCTION_FETCHED);
                    instructionQueue.add(present);
                }
                reachedBreak = present.getInstructionType() == InstructionType.BREAK;
                if(reachedBreak){
                    breakPC = pc;
                }
            }
        }
    }
}
