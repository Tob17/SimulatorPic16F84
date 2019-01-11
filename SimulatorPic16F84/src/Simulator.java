import java.io.IOException;
import java.util.concurrent.*;

public class Simulator {
	
	
	/* TODO: Implement interruption of an execution and dealing with an interupt separativly */
	/* TODO: Implement Timer0 Interrupt */
	/* Implement RB4-RB7 interrupts */
	
	
	/* >>> STATIC-SIMULATOR-VARIABLES <<< */
	
	static double runtimeCounter = 0;
	static double timePerCycle = 1;
	static double clockFrequency = 4_000_000;
	//Clock frequency in Hertz
	//Ranging from 32kHz to 20Mhz
	// 32.000 - 20.000.000
	// 4.000.000 = 1 uS
	// 8.000.000 = 0.5 uS
	
	
	/* >>> STATIC-SIMULATOR-METHODS <<< */
	
	//Resets the runtime counter
	static public void resetRuntimeCounter()
	{runtimeCounter = 0.0;}
	
	//Resets the runtime counter
	static public void increaseRuntimeCounter()
	{runtimeCounter += timePerCycle;}
	
	//Calculate the current timePerCacle
	static void calculateCycleTime(long clockFrequency)
	{Simulator.clockFrequency = clockFrequency;
	 timePerCycle = ((double)4_000_000/clockFrequency);}
	
	

	/* >>> SIMULATOR-VARIABLES <<< */
	
	boolean programMemoryContainsProgram = false;
	int amountOfCPUCycles = 0;
	
	
	/* >>> SYNCHRONIZATION-VARIABLES <<< */

	//Binary Semaphore (initialization with 1)
	Semaphore semaphore = new Semaphore(1);
	
	
	/* >>> SIMULATION <<< */
	
	//Inits the Simulator by parsing a file that contains code and writes it to "program". Returns the raw Text-file for other purposes to avoid another parser-call!
	public String[] initSimulator(String filePath) throws IOException 
	  {
	   //Putting everything into the program-memory
	   LSTParser parser; // Parser that reads and extracts code from text-files
	   SimulatorGUI.consoleOutput.append("====================================================================\n");	   
	   parser = new LSTParser(filePath); 
	   SimulatorGUI.consoleOutput.append("====================================================================\n");	   
	   String[] text = parser.readFile();
	   parser.printFile(text);
	   SimulatorGUI.consoleOutput.append("====================================================================\n");
	   String[] codeText = parser.extractCodeline(text);
	   parser.printFile(codeText);
	   SimulatorGUI.consoleOutput.append("====================================================================\n");
	   String[] programAsStrings = parser.extractCommandsFromStrings(codeText);
	   parser.printFile(programAsStrings);
	   SimulatorGUI.consoleOutput.append("====================================================================\n");
	   //Copying extracted program into a static Program-Memory
	   loadProgramIntoProgramMemory(parser.convertStringCommandsToInteger(programAsStrings));
	   parser.closeFile();
	   SimulatorGUI.consoleOutput.append("====================================================================\n");
	   
	   //Setting the amount of Cycles the CPU should run on the code parsed above
	   amountOfCPUCycles = programAsStrings.length;
	   
	   //Resetting every Register as part of the initialization
	   resetController();
	   
	   //Resetting runtimeCounter
	   resetRuntimeCounter();
	   
	   SimulatorGUI.consoleOutput.append("====================================================================\n");
	   SimulatorGUI.consoleOutput.append("==================== Waiting for User... ===========================\n");
	   SimulatorGUI.consoleOutput.append("====================================================================\n");
	   
	   return text;
	  }

	
	//Performs a single-step-execution!! (atomically)
	public void executeStep() throws InterruptedException
	  {
	   semaphore.acquire();
	     CPU_Cycle();
	   semaphore.release();
	  }
	
	
	//Resets every Register found in the Pic16F84-Controller
	public void resetController()
	  {	
	   SimulatorGUI.consoleOutput.append("Resetting Registers...\n");
	   SimulatorGUI.consoleOutput.append("====================================================================\n");
	   Pic16F84Registers.initRegisters();
	   Pic16F84Registers.initMemory();
	   SimulatorGUI.consoleOutput.append("========================= Memory whiped! ===========================\n");
	   SimulatorGUI.consoleOutput.append("====================================================================\n");
		   
	   Pic16F84Registers.printAllFlags();
	   Pic16F84Registers.printAllRegisters();
	   SimulatorGUI.consoleOutput.append("====================================================================\n");   
	  }
	
	
	
	/* >>> METHODS <<< */
	
	//Starts the next Cycle of the CPU-Pipeline depending on the condition of registers and flags
	public void CPU_Cycle()
	  {
	   //Execute PC-1 (Pipe 1.)
	   if(Pic16F84Registers.INSTRUCTION_REGISTER != -1)
	     {
		  SimulatorGUI.consoleOutput.append("1.Execute " + String.format("%2X", Pic16F84Registers.PROGRAM_MEMORY[Pic16F84Registers.PC]) + "h" + " from " + "Program-Memory[" + Pic16F84Registers.PC + "]\n");
		  SimulatorGUI.consoleOutput.append("====================================================================\n");  
	      CommandExecution.execute(Pic16F84Registers.INSTRUCTION_REGISTER);
		  Pic16F84Registers.printAllFlags();
		  SimulatorGUI.consoleOutput.append("====================================================================\n"); 
		  Pic16F84Registers.printDataMemory();
		  SimulatorGUI.consoleOutput.append("====================================================================\n");  
	     }
	   else
	     {
		  SimulatorGUI.consoleOutput.append("====================================================================\n");
		  SimulatorGUI.consoleOutput.append("1.Instruction-Register is empty!\n");  
		  SimulatorGUI.consoleOutput.append("====================================================================\n");  
	     }
	   //Fetch PC (Pipe 2.)
	   Pic16F84Registers.INSTRUCTION_REGISTER = Pic16F84Registers.PROGRAM_MEMORY[Pic16F84Registers.PC]; 
	   SimulatorGUI.consoleOutput.append("2.Fetched " + String.format("%2X", Pic16F84Registers.PROGRAM_MEMORY[Pic16F84Registers.PC]) + "h" + " from " + "Program-Memory[" + Pic16F84Registers.PC + "]\n");  
	   SimulatorGUI.consoleOutput.append("====================================================================\n"); 
	   
	   //Increasing runtimeCounter
	   increaseRuntimeCounter();
	  }
	
	
	//Copies the program extracted from the textfile into the static Program-Memory
	public void loadProgramIntoProgramMemory(int[] program)
	  {
	   for(int i = 0; i < program.length; i++)
		   //Transfering program into page defined by bit3 and bit4 of PCLATH
		   Pic16F84Registers.PROGRAM_MEMORY[(Pic16F84Registers.PC & 0b1100000000000) + i] = (short)program[i];
	   programMemoryContainsProgram = true;
	  }
	
	
	//Erases the content of the Program-Memory
	public void whipeProgramMemory()
	  {
	   for(int i = 0; i < Pic16F84Registers.PROGRAM_MEMORY.length; i++)
		   //Erasing program from page defined by bit3 and bit4 of PCLATH
	      Pic16F84Registers.PROGRAM_MEMORY[(Pic16F84Registers.PC & 0b1100000000000) + i] = 0;
	   programMemoryContainsProgram = false;
	  }
	
}
