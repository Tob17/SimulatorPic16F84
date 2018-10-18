
import java.io.IOException;
import javax.swing.*;

public class MainClass {
	
  public static void main(String[] abc) throws IOException 
   {
	//SimulatorGUI.initFrame();   
	Simulator simulator = new Simulator();
	simulator.initSimulator("./src/LSTFiles/TPicSim1.LST");
	simulator.startSimulator();
   }

}
