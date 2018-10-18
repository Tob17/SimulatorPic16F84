
import java.io.IOException;

import javax.swing.*;

public class MainClass {
	
  public static void main(String[] abc) throws IOException 
   {
	//SimulatorGUI.initFrame();  
	LSTParser parser = new LSTParser("./src/LSTFiles/TPicSim1.LST");
	
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
	
	int program[] = parser.convertStringCommandsToInteger(programAsStrings);
	
	for(int i = 0; i < program.length -1; i++)
	{
	CommandExecution.execute(program[i]);
	Pic16F84Registers.printWRegister();
	Pic16F84Registers.printAllFlags();
	Pic16F84Registers.printPSW();
	System.out.println("====================================================================");
	}
	
	
	
	parser.closeFile();
   }
  
}
