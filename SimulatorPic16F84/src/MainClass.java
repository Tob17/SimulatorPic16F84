
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
	//for(int i = 0; i < program.length; i++)
		//System.out.println(program[i]);
	
	System.out.println("====================================================================");
	
	
	System.out.println("Value in W Register before command: " + String.format("0x%2X", Pic16F84Registers.W_REGISTER) + "H");
	CommandExecution.execute(program[0]);
	System.out.println("Value in W Register after command: " + String.format("0x%2X", Pic16F84Registers.W_REGISTER) + "H");
	
	parser.closeFile();
   }
  
}
