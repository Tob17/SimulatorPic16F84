import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;



public class SimulatorGUI extends JPanel implements ActionListener {
	
	/* TODO: Implement Breakpoints */
	/* TODO: Implement Runtime-Counter */
	/* TODO: Implement Clock-Generator */
    /* TODO: specify user events! */
	
	
  /* >>> Class-Methods <<< */

  // Creating and initializing a Frame that hold our GUI
  public static void initFrame(Simulator simulator)
    {
	 JFrame simulatorWindow = new JFrame("Simulator");
	 SimulatorGUI simulatorContentPane = new SimulatorGUI();
		
	 /* Adding a main-pane into the Frame and setting sizes and visibility */
	 simulatorWindow.setContentPane(simulatorContentPane);
	 simulatorWindow.setSize(1920,1080);
	 simulatorWindow.setVisible(true);
	 simulatorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	 
	 simulatorContentPane.bindSimulatorToGUI(simulator);
	 simulatorContentPane.initLayout();
	}
  
  
  /* >>> Currently running simulator-objekt <<< */
  
  Simulator simulator = null;
  
  
  /* >>> GUI-Elements <<< */
  
  JButton runButton = new JButton("Run");
  JButton stopButton = new JButton("Stop");
  JButton stepButton = new JButton("Step");
  JButton resetButton = new JButton("Reset");
  
  TaggedLabel lstLabelList[] = null;
  
  
  /* >>> Object-Methods <<< */
  
  //Grabbing the currently running simulator and saving it local
  public void bindSimulatorToGUI(Simulator simulator)
  {
	  this.simulator = simulator;
  }
  
  
  /* Drawing our GUI in the Frame */	
  
  //Inits the main-panels layout
  public void initLayout()
  {	    	  
	  /* Setting up basic layout */
	  
	  JPanel layoutPanel1 = new JPanel();
	  JPanel layoutPanel2 = new JPanel();
	  JPanel layoutPanel3 = new JPanel();
	  JPanel layoutPanel4 = new JPanel();
	  
	  JSplitPane splitter1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	  splitter1.setResizeWeight(0.10);
	  splitter1.setEnabled(false);
	  splitter1.setDividerSize(0);
	  
	  JSplitPane splitter2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	  splitter2.setResizeWeight(0.90);
	  splitter2.setEnabled(false);
	  splitter2.setDividerSize(0);
 
	  JPanel contentPanel1 = new JPanel();
	  JPanel contentPanel2 = new JPanel();
	  JPanel contentPanel3 = new JPanel();
	  JPanel contentPanel4 = new JPanel();
	  JPanel contentPanel5 = new JPanel();
	  JPanel contentPanel6 = new JPanel();
	  JPanel contentPanel7 = new JPanel();
	  JPanel contentPanel8 = new JPanel();
	  
	  contentPanel1.setBackground(Color.RED);
	  contentPanel2.setBackground(Color.YELLOW);
	  contentPanel3.setBackground(Color.GREEN);
	  contentPanel4.setBackground(Color.BLUE);
	  contentPanel5.setBackground(Color.CYAN);
	  contentPanel6.setBackground(Color.BLACK);
	  contentPanel7.setBackground(Color.WHITE);
	  contentPanel8.setBackground(Color.ORANGE);  
	  
	  setLayout(new GridLayout(1,2, 5, 5));
	  
	  splitter1.add(layoutPanel1);
	  splitter1.add(layoutPanel2);
	  add(splitter1);
	  
	  splitter2.add(layoutPanel3);
	  splitter2.add(layoutPanel4);
	  add(splitter2);
	  
	  layoutPanel1.setLayout(new GridLayout(1,4,5,5));
	  layoutPanel2.setLayout(new GridLayout(1,1,5,5));
	  layoutPanel3.setLayout(new GridLayout(2,1,5,5));
	  layoutPanel4.setLayout(new GridLayout(1,1,5,5));
	  
	  layoutPanel1.add(contentPanel1);
	  layoutPanel1.add(contentPanel2);
	  layoutPanel1.add(contentPanel3);
	  layoutPanel1.add(contentPanel4);
	  layoutPanel2.add(contentPanel5);
	  layoutPanel3.add(contentPanel6);
	  layoutPanel3.add(contentPanel7);
	  layoutPanel4.add(contentPanel8);
	  
	  contentPanel1.setLayout(new GridLayout(1,1,5,5));
	  contentPanel2.setLayout(new GridLayout(1,1,5,5));
	  contentPanel3.setLayout(new GridLayout(1,1,5,5));
	  contentPanel4.setLayout(new GridLayout(1,1,5,5));
	  contentPanel5.setLayout(new GridLayout(1,1,5,5));
	  contentPanel6.setLayout(new GridLayout(1,1,5,5));
	  contentPanel7.setLayout(new GridLayout(1,1,5,5));
	  contentPanel8.setLayout(new GridLayout(1,1,5,5));
	  
	  
	  /* Adding GUI-Content */
	  
	  contentPanel1.add(runButton);
	  contentPanel2.add(stopButton);
	  contentPanel3.add(stepButton);
	  contentPanel4.add(resetButton);
	  
	  initLSTGrid(contentPanel5, simulator.textCopy);
	  
	  /* Activating Signal-Listener */
	  
	  runButton.addActionListener(this);
	  stopButton.addActionListener(this);
	  stepButton.addActionListener(this);
	  resetButton.addActionListener(this);  
  }
  
  //Inits array of labels with the copied text of the LST-File stored in simulator
  public void initLSTGrid(JPanel LSTPanel, String[] LSTText)
  {
	  LSTPanel.setLayout(new GridLayout(LSTText.length, 1,1,1));
	  lstLabelList = new TaggedLabel[LSTText.length];
	  
	  for(int i = 0; i < LSTText.length; i++)
	    {
		  lstLabelList[i] = new TaggedLabel(LSTText[i]);
		  lstLabelList[i].setOpaque(true);
		  lstLabelList[i].setBackground(Color.GREEN);
		  
		  if(!LSTText[i].substring(0,4).equals("    "))
		      lstLabelList[i].lineNumber = Integer.parseInt(LSTText[i].substring(0,4));
		  else
			  lstLabelList[i].lineNumber = -1; 
	      
	      LSTPanel.add(lstLabelList[i]);
	    }
  }
  
  
  
  @Override
  public void paint(Graphics g)
    {
	  super.paint(g);
	 /* TODO: Drawing GUI-Elements */
	 /* TODO: Visualizing data on our GUI */
	}

  
  
  /* >>> Interface implementations <<< */

  
@Override
public void actionPerformed(ActionEvent e) 
    {
	  //Starting Simulator
	 if(e.getSource() == runButton)
	   simulator.startSimulator();
	 
	 //Stoping Simulator
	 if(e.getSource() == stopButton)
		 simulator.stopSimulator();
	 
	 if(e.getSource() == stepButton)
		 lstLabelList[6].setBackground(Color.PINK);
	 
	 if(e.getSource() == resetButton)
		 lstLabelList[6].setBackground(Color.BLUE);

	}

  
}
