
import java.io.IOException;

import javax.swing.*;

public class MainClass {
	
  public static void main(String[] abc) throws IOException 
   {
	//SimulatorGUI.initFrame();  
	LSTParser parser = new LSTParser("./src/LSTFiles/TPicSim2.LST");
	
	System.out.println("====================================================================");
	
	String[] text = parser.readFile();
	parser.printFile(text);
	
	System.out.println("====================================================================");
	
	String[] codeText = parser.extractCodeline(text);
	parser.printFile(codeText);
	
	System.out.println("====================================================================");
	
	parser.closeFile();
   }
  
}
