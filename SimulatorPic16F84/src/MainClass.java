
import java.io.IOException;

import javax.swing.*;

public class MainClass {
	
	
  public static void main(String[] abc) throws IOException 
   {
	//SimulatorGUI.initFrame();  
	LSTParser parser = new LSTParser("./src/LSTFiles/TPicSim2.LST");
	
	String[] text = parser.readFile();
	for(int i = 0; i < text.length; i++)
		System.out.println(text[i]);
	
	
	String[] codeText = parser.extractCodeline(text);
	for(int i = 0; i < codeText.length; i++)
		System.out.println(codeText[i]);
	parser.closeFile();

   
   }
		

}
