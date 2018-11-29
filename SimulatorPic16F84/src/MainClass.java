
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
		simulator.initSimulator("./src/LSTFiles/TPicSim4.LST", 0, 200); //(file path, page number, number of CPU-Cycles)
		simulator.startSimulator();

	}

}
