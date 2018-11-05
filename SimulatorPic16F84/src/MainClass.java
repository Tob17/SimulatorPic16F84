
import java.io.IOException;
import javax.swing.*;

public class MainClass {
	
  public static void main(String[] abc) throws IOException 
   {
	System.out.println("====================================================================");
	System.out.println("==================Starting Simulator Pic16F84=======================");
	System.out.println("====================================================================");
	
	//SimulatorGUI.initFrame();   
	Simulator simulator = new Simulator();
	//1.Parameter = File-Path, 2.Parameter = Program-Page of Program-Memory, 3.Parameter = Custom amount of CPU-Cycles executed
	simulator.initSimulator("./src/LSTFiles/TPicSim1.LST", 0, 50);
	simulator.startSimulator();
	
	Pic16F84Registers.printProgramMemory();
   }

}
