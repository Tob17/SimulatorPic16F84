
import javax.swing.*;
import java.awt.*;

public class SimulatorGUI extends JPanel {
	
	
  /* >>> Class-Methods <<< */
	

  // Creating and initializing a Frame that hold our GUI
  public static void initFrame()
    {
	 JFrame simulatorWindow = new JFrame("Simulator");
	 SimulatorGUI simulatorContentPane = new SimulatorGUI();
		
	 /* Adding a main-pane into the Frame and setting sizes and visibility */
	 simulatorWindow.setContentPane(simulatorContentPane);
	 simulatorWindow.setSize(1920,1080);
	 simulatorWindow.setVisible(true);
	 simulatorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
  
  
  
  /* >>> Object-Methods <<< */
  
  /* Drawing our GUI in the Frame */	
  public void paint(Graphics g)
    {
	 /* TODO: Drawing GUI-Elements */
	 /* TODO: Visualizing data on our GUI */
	}
  
  
  /* TODO: specify user events! */

}
