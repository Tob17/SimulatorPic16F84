import java.io.IOException;

public class MainClass {

	public static void main(String[] abc)  throws IOException 
	{		
		System.out.println("====================================================================");
		System.out.println("==================Starting Simulator Pic16F84=======================");
		System.out.println("====================================================================");
			
		//Init GUI (GUI inits Simulator aswell)
		SimulatorGUI.init("./src/LSTFiles/TPicSim1.LST", 0, 10);
	}

}
