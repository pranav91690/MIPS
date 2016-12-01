JCC = javac
default: all
all:
	$(JCC) -d ./ src/edu/ufl/pranav/Main.java src/edu/ufl/pranav/deAssembler/deAssembler.java src/edu/ufl/pranav/Instructions/Blank.java src/edu/ufl/pranav/Instructions/Data.java src/edu/ufl/pranav/Instructions/Instruction.java src/edu/ufl/pranav/Instructions/InstructionType.java src/edu/ufl/pranav/Instructions/IType.java src/edu/ufl/pranav/Instructions/JType.java src/edu/ufl/pranav/Instructions/RType.java src/edu/ufl/pranav/simulator/stages/Commit.java src/edu/ufl/pranav/simulator/stages/Issue.java src/edu/ufl/pranav/simulator/stages/Execute.java src/edu/ufl/pranav/simulator/stages/InstructionFetch.java src/edu/ufl/pranav/simulator/stages/WriteResult.java src/edu/ufl/pranav/simulator/units/BranchTargetBuffer.java src/edu/ufl/pranav/simulator/units/InstructionQueue.java src/edu/ufl/pranav/simulator/units/Memory.java src/edu/ufl/pranav/simulator/units/RegisterFile.java src/edu/ufl/pranav/simulator/units/ReOrderBuffer.java src/edu/ufl/pranav/simulator/units/ReservationStations.java src/edu/ufl/pranav/simulator/Simulator.java src/edu/ufl/pranav/utils/fileExtractor.java src/edu/ufl/pranav/simulator/entities/BTBEntry.java src/edu/ufl/pranav/simulator/entities/InstructionStage.java src/edu/ufl/pranav/simulator/entities/Register.java src/edu/ufl/pranav/simulator/entities/ROBEntry.java src/edu/ufl/pranav/simulator/entities/RS.java
clean:
	rm edu/ufl/pranav/*.class
	rm edu/ufl/pranav/deAssembler/*.class
	rm edu/ufl/pranav/utils/*.class
	rm edu/ufl/pranav/instructions/*.class
	rm edu/ufl/pranav/simulator/stages/*.class
	rm edu/ufl/pranav/simulator/units/*.class
	rm edu/ufl/pranav/simulator/*.class
	rm edu/ufl/pranav/simulator/entities/*.class