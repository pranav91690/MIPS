package edu.ufl.pranav.deAssembler;

import edu.ufl.pranav.Instructions.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pranav on 9/25/16.
 */
public class deAssembler {
    int PC;

    public deAssembler() {
        this.PC = 600;
    }

    public boolean isRTypeInstruction(String s){
        return "0".equals(binToHex(s));
    }

    public String binToHex(String s) {
        return new BigInteger(s, 2).toString(16);
    }

    public String binToDec(String s){
        return new BigInteger(s,2).toString(10);
    }

    public String buildOutputEntry(String binary, int PC, String instruction){
        return  binary + " " + PC + " " + instruction;
    }

    public String buildBinary(String opcode, String one, String two, String three, String four, String function){
        return opcode + " " + one + " " + two + " " + three + " " + four + " " + function;
    }

    public String buildRegister(String s){
        return "R" + binToDec(s);
    }


    public String signedBintoDec(String s){
        char signBit = s.charAt(0);
        if(signBit == '0'){
            return new BigInteger(s,2).toString(10);
        }else if(signBit == '1'){
            StringBuilder onesComplementBuilder = new StringBuilder();
            for(char bit : s.toCharArray()) {
                // if bit is '0', append a 1. if bit is '1', append a 0.
                onesComplementBuilder.append((bit == '0') ? 1 : 0);
            }

            String onesComplement = onesComplementBuilder.toString();
            int converted = Integer.valueOf(onesComplement, 2);

            return String.valueOf(-1 * (converted + 1));
        }

        return " ";
    }

    public List<Instruction> deassemble(byte[] bytes){
        List<String> instructions = new ArrayList<String>();
        List<Instruction> assemblyInstructions = new ArrayList<Instruction>();

        // Handle the instructions as a group of 4
        int pointer = 0;
        for(int i = 1; i<=bytes.length/4; i++){
            byte one = bytes[pointer];
            byte two = bytes[pointer+1];
            byte three = bytes[pointer+2];
            byte four = bytes[pointer+3];

            pointer+=4;
            // Construct the instruction from the bytes
            String s1 = String.format("%8s", Integer.toBinaryString(one & 0xFF)).replace(' ', '0');
            String s2 = String.format("%8s", Integer.toBinaryString(two & 0xFF)).replace(' ', '0');
            String s3 = String.format("%8s", Integer.toBinaryString(three & 0xFF)).replace(' ', '0');
            String s4 = String.format("%8s", Integer.toBinaryString(four & 0xFF)).replace(' ', '0');
            instructions.add(s1 + s2 + s3 + s4);
        }

        boolean endOfBreak = false;

        for(String s : instructions){
            // Separate all the components
            String opcode = s.substring(0,6);
            String _s = s.substring(6,11);
            String _t = s.substring(11,16);
            String _d = s.substring(16,21);
            String shift = s.substring(21,26);
            String function = s.substring(26,32);

            String binary = buildBinary(opcode, _s, _t, _d, shift, function);
            String key = "";
            Instruction ins;

            /**
             * Types of Instructions
             * Load/Store Instructions (SW/LW)
             * Jump/Branch Instructions (J,BEQ,BNE,BGEZ,BGTZ,BLEZ,BLTZ)
             * Set Instructions (SLT,SLTI, SLTU)
             * Shift Instructions (SLL,SRL, SRA)
             * Arithmetic Instructions (ADD,ADDU,SUB,SUBU,ADDI,ADDIU)
             * Logical Instructions (AND,OR,XOR,NOR)
             * NOP,BREAK
             */
            if(!endOfBreak) {
                if (isRTypeInstruction(opcode)) {
                    // Build the required variables
                    String rs = buildRegister(_s);
                    String rt = buildRegister(_t);
                    String rd = buildRegister(_d);

                    // SLT
                    if ("2a".equals(binToHex(function))) {
                        key = "SLT";
                    }
                    // SLTU
                    else if ("2b".equals(binToHex(function))) {
                        key = "SLTU";
                    }
                    else if ("0".equals(binToHex(function))) {
                        if("0".equals(binToHex(_s)) && "0".equals(binToHex(_t))  &&
                                "0".equals(binToHex(_d))  && "0".equals(binToHex(shift))){
                            // NOP
                            key = "NOP";
                        }else {
                            // SLL
                            key = "SLL";
                        }
                    }
                    // SRL
                    else if ("2".equals(binToHex(function))) {
                        key = "SRL";
                    }
                    // SRA
                    else if ("3".equals(binToHex(function))) {
                        key = "SRA";
                    }
                    // SUB
                    else if ("22".equals(binToHex(function))) {
                        key = "SUB";
                    }
                    // SUBU
                    else if ("23".equals(binToHex(function))) {
                        key = "SUBU";
                    }
                    // ADD
                    else if ("20".equals(binToHex(function))) {
                        key = "ADD";
                    }
                    // ADDU
                    else if ("21".equals(binToHex(function))) {
                        key = "ADDU";
                    }
                    // AND
                    else if ("24".equals(binToHex(function))) {
                        key = "AND";
                    }
                    // OR
                    else if ("25".equals(binToHex(function))) {
                        key = "OR";
                    }
                    //XOR
                    else if ("26".equals(binToHex(function))) {
                        key = "XOR";
                    }
                    //NOR
                    else if ("27".equals(binToHex(function))) {
                        key = "NOR";
                    }
                    //BREAK
                    else if ("d".equals(binToHex(function))) {
                        key = "BREAK";;
                        endOfBreak = true;
                    }

                    /**
                     * Build the rType instruction and add to the
                     * list of instructions
                     */
                    ins = new RType(binary,this.PC,opcode,key,rs,rt,rd,shift,function);
                }
                //J
                // Multiply by four to convert bytes into bits
                else if ("2".equals(binToHex(opcode))) {
                    String target = _s + _t + _d + shift + function + "00";
                    ins = new JType(binary,this.PC,opcode,"J",signedBintoDec(target));
                }
                // Rest are I type instructions
                else {
                    // Build the Required Variables
                    String rs = buildRegister(_s);
                    String rt = buildRegister(_t);
                    String _i = _d + shift + function;
                    String immediate = "";

                    //SW
                    if ("2b".equals(binToHex(opcode))) {
                        key = "SW";
                        immediate = signedBintoDec(_i);

                    }
                    //LW
                    else if ("23".equals(binToHex(opcode))) {
                        key = "LW";
                        immediate = signedBintoDec(_i);
                    }
                    //BEQ
                    else if ("4".equals(binToHex(opcode))) {
                        key = "BEQ";
                        _i = _i + "00";
                        immediate = signedBintoDec(_i);
                    }
                    //BNE
                    else if ("5".equals(binToHex(opcode))) {
                        key = "BNE";
                        _i = _i + "00";
                        immediate = signedBintoDec(_i);
                    }

                    else if("1".equals(binToHex(opcode))){
                        //BGEZ
                        if("0".equals(binToHex(_d))){
                            key = "BGEZ";
                            _i = _i + "00";
                            immediate = signedBintoDec(_i);
                        }
                        //BLTZ
                        else if("1".equals(binToHex(_d))){
                            key = "BLTZ";
                            _i = _i + "00";
                            immediate = signedBintoDec(_i);
                        }
                    }
                    //BGTZ
                    else if("7".equals(binToHex(opcode))){
                        key = "BGTZ";
                        _i = _i + "00";
                        immediate = signedBintoDec(_i);
                    }
                    //BLEZ
                    else if("6".equals(binToHex(opcode))){
                        key = "BLEZ";
                        _i = _i + "00";
                        immediate = signedBintoDec(_i);
                    }
                    //ADDI
                    else if ("8".equals(binToHex(opcode))) {
                        key = "ADDI";
                        immediate = signedBintoDec(_i);
                    }
                    // ADDIU
                    else if ("9".equals(binToHex(function))) {
                        key = "ADDIU";
                        immediate = binToDec(_i);
                    }

                    //SLTI
                    else if ("a".equals(binToHex(opcode))) {
                        key = "SLTI";
                        immediate = signedBintoDec(_i);
                    }

                    ins = new IType(binary,this.PC,opcode,key,rs,rt,immediate);

                }
            }else{
                if(this.PC >= 716){
                    ins = new Data(s,this.PC,signedBintoDec(s));
                }else{
                    ins = new Blank(s,this.PC,signedBintoDec(s));
                }
            }

            // Add the instruction to the list
            assemblyInstructions.add(ins);

            // Increase the PC for every instruction
            this.PC += 4;
        }

        return assemblyInstructions;
    }

}
