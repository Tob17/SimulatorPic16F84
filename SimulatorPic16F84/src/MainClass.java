/* Simulator by Tobias Kibelka and Tuan Nam Truong  *
 * Latest Update: 13.12.2018                       */

import java.io.IOException;

public class MainClass {

	public static void main(String[] abc)  throws IOException 
	{		
		SimulatorGUI.consoleOutput.append("====================================================================\n");
		SimulatorGUI.consoleOutput.append("==================Starting Simulator Pic16F84=======================\n");
		SimulatorGUI.consoleOutput.append("====================================================================\n");
			
		//Init GUI (GUI inits Simulator aswell)
		SimulatorGUI.init("./src/LSTFiles/TPicSim10.LST");	
	}

}
