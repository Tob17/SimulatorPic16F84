
import java.io.IOException;
import javax.swing.*;

public class MainClass {

	public static void main(String[] abc) throws IOException 
	{
		/* TODO: Initializing Simulator-GUI */
		
		System.out.println("====================================================================");
		System.out.println("==================Starting Simulator Pic16F84=======================");
		System.out.println("====================================================================");
		
		//Creating Simulator
		Simulator simulator = new Simulator();
		
		//Init Simulator with file path, page number andnumber of CPU-Cycles
		simulator.initSimulator("./src/LSTFiles/TPicSim4.LST", 0, 200); 
		
		//Starting Simulator
		simulator.startSimulator();
	}

}
