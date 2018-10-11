
import javax.swing.*;
import java.awt.*;

public class SimulatorGUI extends JPanel {
	
	
  /* >>> Class-Methods <<< */
	
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
  
  /* Drawing GUI in the Frame */	
  public void paint(Graphics g)
    {
	 Graphics2D g2d = (Graphics2D) g;
	 g.fillRect(50,50,50,50);
	}

}
