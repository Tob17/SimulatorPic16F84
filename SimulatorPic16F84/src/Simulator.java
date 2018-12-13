import java.io.IOException;

public class Simulator {
	
	
	/* TODO: Implement interruption of an execution and dealing with an interupt separativly */
	/* TODO: Implement Breakpoints */
	/* TODO: Implement Runtime-Counter */
	/* TODO: Implement Clock-Generator */
	

	/* >>> SIMULATOR-VARIABLES <<< */
	
	boolean programMemoryContainsProgram = false;
	int amountOfCPUCycles = 0;

	
	/* >>> SIMULATION <<< */
	
	//Inits the Simulator by parsing a file that contains code and writes it to "program". Returns the raw Text-file for other purposes to avoid another parser-call!
	public String[] initSimulator(String filePath, int page) throws IOException 
	  {
	   //Putting everything into the program-memory
	   Pic16F84Registers.settingProgramPage(page); // selecting custom program-memory page
	   LSTParser parser; // Parser that reads and extracts code from text-files
	   System.out.println("====================================================================");	   
	   parser = new LSTParser(filePath); 
	   System.out.println("====================================================================");	   
	   String[] text = parser.readFile();
	   parser.printFile(text);
	   System.out.println("====================================================================");
	   String[] codeText = parser.extractCodeline(text);
	   parser.printFile(codeText);
	   System.out.println("====================================================================");
	   String[] programAsStrings = parser.extractCommandsFromStrings(codeText);
	   parser.printFile(programAsStrings);
	   System.out.println("====================================================================");
	   //Copying extracted program into a static Program-Memory
	   loadProgramIntoProgramMemory(parser.convertStringCommandsToInteger(programAsStrings));
	   parser.closeFile();
	   System.out.println("====================================================================");
	   
	   //Setting the amount of Cycles the CPU should run on the code parsed above
	   amountOfCPUCycles = programAsStrings.length;
	   
	   //Resetting every Register as part of the initialization
	   resetController();
	   
	   System.out.println("====================================================================");
	   System.out.println("==================== Waiting for User... ===========================");
	   System.out.println("====================================================================");
	   
	   
	   return text;
	  }

	
	//Performs a single-step-execution!!
	public void step()
	  {CPU_Cycle();}
	
	
	//Resets every Register found in the Pic16F84-Controller
	public void resetController()
	  {	
	   System.out.println("Resetting Registers...");
	   System.out.println("====================================================================");
	   Pic16F84Registers.initRegisters(); // always init Registers first
	   Pic16F84Registers.initMemory(); // always clear memory accordingly
	   System.out.println("========================= Memory whiped! ===========================");
	   System.out.println("====================================================================");
		   
	   Pic16F84Registers.printAllFlags();
	   Pic16F84Registers.printAllRegisters();
	   System.out.println("====================================================================");   
	  }
	
	
	//Stops the Simulator
	public void stopSimulator()
	{
		System.out.println("Implement stop-simulator");
	}
	
	
	
	/* >>> METHODS <<< */
	
	//Starts the next Cycle of the CPU-Pipeline depending on the condition of registers and flags
	public void CPU_Cycle()
	  {
	   //Execute PC-1 (Pipe 1.)
	   if(Pic16F84Registers.INSTRUCTION_REGISTER != -1)
	     {
		  System.out.println("1.Execute " + String.format("%2X", Pic16F84Registers.PROGRAM_MEMORY[Pic16F84Registers.PC]) + "h" + " from " + "Program-Memory[" + Pic16F84Registers.PC + "]");
		  System.out.println("====================================================================");  
	      CommandExecution.execute(Pic16F84Registers.INSTRUCTION_REGISTER);
		  Pic16F84Registers.printAllFlags();
		  Pic16F84Registers.printAllRegisters();
		  Pic16F84Registers.printDataMemory();
		  System.out.println("====================================================================");  
	     }
	   else
	     {
		  System.out.println("====================================================================");
		  System.out.println("1.Instruction-Register is empty!");  
		  System.out.println("====================================================================");  
	     }
	   //Fetch PC (Pipe 2.)
	   Pic16F84Registers.INSTRUCTION_REGISTER = Pic16F84Registers.PROGRAM_MEMORY[Pic16F84Registers.PC]; 
	   System.out.println("2.Fetched " + String.format("%2X", Pic16F84Registers.PROGRAM_MEMORY[Pic16F84Registers.PC]) + "h" + " from " + "Program-Memory[" + Pic16F84Registers.PC + "]");  
	   System.out.println("====================================================================");  
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
