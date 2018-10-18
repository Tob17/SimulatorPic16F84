import java.io.IOException;

public class Simulator {
	
	/* TODO: Integrate fetch, decode and execution-loop */
	/* TODO: Enable step-by-step-execution */
	
	
	/* >>> SIMULATOR-VARIABLES <<< */
	
	LSTParser parser; // Parser that reads and extracts code from text-files
	int[] program; // contains the code that has to be executed
	
	
	/* >>> SIMULATION <<< */
	
	//Inits the Simulator by parsing a file that contains code und writes it to "program"
	public void initSimulator(String filePath) throws IOException 
	  {
	   Pic16F84Registers.PC = 0; // Init PC with adress 0
	   
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
	   program = parser.convertStringCommandsToInteger(programAsStrings);
	   parser.closeFile();
	   System.out.println("====================================================================");
	  }
	
	//Starts the Simulator by executing all the lines contained in "program"
	public void startSimulator()
	  {	
	   System.out.println("Starting execution...");
	   System.out.println("====================================================================");
	   
	   //MAIN-EXECUTION-ROUTINE!!
	   for(int i = 0; i < 50; i++) //50 just as an examble 
	     {
		  CommandExecution.execute(program[Pic16F84Registers.PC]); // using the PC before the inkrement
		  Pic16F84Registers.printWRegister();
		  Pic16F84Registers.printAllFlags();
		  Pic16F84Registers.printPSW();
		  System.out.println("====================================================================");  
		 }
	   
	   System.out.println("Execution finished!");
	   System.out.println("====================================================================");
	  }	
	
	
}
