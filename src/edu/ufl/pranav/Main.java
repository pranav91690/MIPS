package edu.ufl.pranav;

import edu.ufl.pranav.Instructions.*;
import edu.ufl.pranav.deAssembler.deAssembler;
import edu.ufl.pranav.simulator.Simulator;
import edu.ufl.pranav.utils.fileExtractor;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
	// write your code here
        if(args.length < 2){
            System.out.println("*****************************************");
            System.out.println("Invalid Arguments");
            System.out.println("*****************************************");
            System.out.println("Correct format is -> inputFileName outputFileName <startCycle> <endCycle>");
            System.out.println("*****************************************");
        }else {
            String inputFileName = args[0];
            String outputFileName = args[1];
            String operation = "sim";

            fileExtractor fileExtractor = new fileExtractor(inputFileName);
            deAssembler de = new deAssembler();
            List<Instruction> assemblyInstructions;
            try{
                byte[] instructions = fileExtractor.extractInstructions();
                assemblyInstructions = de.deassemble(instructions);
                    if (operation.equals("dis")) {
                    System.out.println("*****************************************");
                    System.out.println("Program Running");

                    try {
                        FileWriter fw = new FileWriter(outputFileName + ".txt");
                        for (Instruction ins : assemblyInstructions) {
                            fw.write(ins.buildOutput());
                            fw.write(System.getProperty("line.separator"));
                        }

                        fw.close();

                        System.out.println("*****************************************");
                        System.out.println("Finished Running");
                        System.out.println("Check output file at given path");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }else if(operation.equals("sim")){
                    System.out.println("*****************************************");
                    int startCycle = -1,endCycle  = -1;
                    // Get the Optional Arguments
                    if(args.length == 2){
                        startCycle = -1;
                        endCycle = -1;
                    }else if(args.length == 4){
                        startCycle = Integer.parseInt(args[2]);
                        endCycle = Integer.parseInt(args[3]);
                    }

                    System.out.println("Program Running");

                    try {
                        FileWriter fw = new FileWriter(outputFileName + ".txt");
                        Simulator simulator = new Simulator(assemblyInstructions,startCycle,endCycle);
                        fw.write(simulator.start());
                        fw.close();

                        System.out.println("*****************************************");
                        System.out.println("Finished Running");
                        System.out.println("Check output file at given path");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                else{
                    System.out.println("*****************************************");
                    System.out.println("Invalid Operation -> " + operation);
                    System.out.println("*****************************************");
                    System.out.println("Operations supported right now are");
                    System.out.println("1. dis");
                    System.out.println("1. MIPSsim");
                    System.out.println("*****************************************");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
