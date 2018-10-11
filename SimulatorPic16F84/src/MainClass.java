
import javax.swing.*;

public class MainClass {
	
	
  public static void main(String[] abc)
   {
	JFrame simulatorWindow = new JFrame("Simulator");
	SimulatorGUI simulatorContentPane = new SimulatorGUI();
	
	simulatorWindow.setContentPane(simulatorContentPane);
	simulatorWindow.setSize(1920,1080);
	simulatorWindow.setVisible(true);
	simulatorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

}
