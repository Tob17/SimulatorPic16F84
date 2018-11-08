
import java.io.IOException;
import javax.swing.*;

public class MainClass {
	
  public static void main(String[] abc) throws IOException 
   {
	System.out.println("====================================================================");
	System.out.println("==================Starting Simulator Pic16F84=======================");
	System.out.println("====================================================================");
	
	//SimulatorGUI.initFrame();   
	//Simulator simulator = new Simulator();
	//1.Parameter = File-Path, 2.Parameter = Program-Page of Program-Memory, 3.Parameter = Custom amount of CPU-Cycles executed
	//simulator.initSimulator("./src/LSTFiles/TPicSim1.LST", 0, 50);
	//simulator.startSimulator();
	
	//Pic16F84Registers.printProgramMemory();
	//Pic16F84Registers.printPCL();
	
	int[] commandList = {
			0x07FF, 0x05FF, 0x01FF, 0x017F, 0x09FF, 0x03FF, 0x0BFF, 0x0AFF, 0x0FFF, 0x04FF, 0x08FF, 0x00FF, 0x0060, 0x0DFF, 0x0CFF, 0x02FF, 0x0EFF, 0x06FF, 
			0x13FF, 0x17FF, 0x1BFF, 0x1FFF,
			0x3FFF, 0x39FF, 0x27FF, 0x0064, 0x2FFF, 0x38FF, 0x33FF, 0x0009, 0x37FF, 0x0008, 0x0063, 0x3DFF, 0x3AFF
			};
	
	CommandInterpreter inter = new CommandInterpreter();
	
	for(int i = 0; i < commandList.length; i++)
		{
			inter.decode(commandList[i]);
			System.out.println();
		}
	
	

	   LSTParser parser; // Parser that reads and extracts code from text-files
	   System.out.println("====================================================================");	   
	   parser = new LSTParser("./src/LSTFiles/TPicSim13.LST"); 
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
	   int[] program = parser.convertStringCommandsToInteger(programAsStrings);
	   parser.closeFile();
	   System.out.println("====================================================================");
	
	   for(int i = 0; i < program.length; i++)
	   {
		   CommandExecution.execute(program[i]);
	   }
	
   }

}
