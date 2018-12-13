import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;



public class SimulatorGUI extends JPanel implements ActionListener, MouseListener {
	
	/* TODO: Implement Breakpoints */
	/* TODO: Implement Runtime-Counter */
	/* TODO: Implement Clock-Generator */
    /* TODO: specify user events! */
	
	
  /* >>> Class-Methods <<< */
	
	
  public static void init(String filePath, int programPageNumber)  throws IOException 
  {
   //Initializing Frame and Main-Panel, while grabbing the Main-Panel
   SimulatorGUI simPane = initFrame();
   //Initializing the Simulator
   simPane.lstText = simPane.simulator.initSimulator(filePath, programPageNumber); 
   //Setting Layout of the Main-Panel
   simPane.initLayout();
   //Repainting everything after everything has been loaded!
   simPane.repaint();
  }
	
	
  // Creating and initializing a Frame that hold our GUI
  public static SimulatorGUI initFrame()
    {
	 JFrame simulatorWindow = new JFrame("Simulator");
	 SimulatorGUI simulatorContentPane = new SimulatorGUI();
		
	 //Adding a main-pane into the Frame and setting sizes and visibility
	 simulatorWindow.setContentPane(simulatorContentPane);
	 simulatorWindow.setSize(1600,900);
	 simulatorWindow.setVisible(true);
	 simulatorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	 return simulatorContentPane;
	}
  
  
  
  
  /* >>> Object-Variables <<< */
  
  //Simulator-Object
  Simulator simulator = new Simulator();
  
  //Copy of the lst-file-text
  String lstText[] = null;
  
  //Currently stopped at Breakpoint
  boolean jumpThroughBreakpoint = false;
  
  //Thread for executing commands
  Thread mainExecutionThread = new Thread(new Runnable() {public void run() {startMainExecutionCycle();}});
  
  //GUI-Elements
  JButton runButton = new JButton("Run");
  JButton stopButton = new JButton("Stop");
  JButton stepButton = new JButton("Step");
  JButton resetButton = new JButton("Reset");
  
  TaggedLabel lstLabelList[] = null;
  
  
  
  /* >>> Object-Methods <<< */

  
  //Initializes the main-panel´s layout
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
	  
	  initLSTGrid(contentPanel5);
	  
	  /* Activating Signal-Listener */
	  
	  runButton.addActionListener(this);
	  stopButton.addActionListener(this);
	  stepButton.addActionListener(this);
	  resetButton.addActionListener(this);  
	  
	  for(int a = 0; a < lstLabelList.length; a++)
		  lstLabelList[a].addMouseListener(this);
	  
  }
  
  
  //Inits a Panel with an array of labels with the stored last-file-text
  public void initLSTGrid(JPanel LSTPanel)
    {
	  LSTPanel.setLayout(new GridLayout(lstText.length, 1,1,1));
	  lstLabelList = new TaggedLabel[lstText.length];
	  
	  for(int i = 0; i < lstLabelList.length; i++)
	    {
		  lstLabelList[i] = new TaggedLabel(lstText[i]);
		  lstLabelList[i].setOpaque(true);
		  lstLabelList[i].setBackground(Color.GREEN);
		  
		  if(!lstText[i].substring(0,4).equals("    "))
		      lstLabelList[i].lineNumber = Integer.parseInt(lstText[i].substring(0,4));
		  else
			  lstLabelList[i].lineNumber = -1; 
	      
	      LSTPanel.add(lstLabelList[i]);
	    }
    }
  
  
	//Checking if next next command contains a breakpoint by comparing PC with every Label available in the GUI´s Taggedlabel-list
	public boolean checkForBreakPoint(int pc, TaggedLabel labelList[])
	{
	  for(int a = 0; a < labelList.length; a++)
	    if((Pic16F84Registers.PC == labelList[a].lineNumber) && labelList[a].breakpointActive)	
	    	return true;

	  return false;
	}
	
	
	//Checks if a line in the label-list is currently executed and marks it with a colour
	public void markLine(int pc, TaggedLabel labelList[])
	{
		for(int a = 0; a < labelList.length; a++)
		{
			if(labelList[a] != null)
			{
				if ((Pic16F84Registers.PC == labelList[a].lineNumber) && lstLabelList[a].breakpointActive)
					labelList[a].setBackground(Color.MAGENTA);
				else 
				{
					if(Pic16F84Registers.PC == labelList[a].lineNumber)	
						labelList[a].setBackground(Color.BLUE);

					if(lstLabelList[a].breakpointActive)
						labelList[a].setBackground(Color.RED);

					if(!(Pic16F84Registers.PC == labelList[a].lineNumber) && !(lstLabelList[a].breakpointActive))
						labelList[a].setBackground(Color.GREEN);  
				} 
			}
			else
				continue;
		} 
	}


	
	/* ************************************************************************ */
	/* ****************************** MAIN ROUTINE **************************** */
	/* ************************************************************************ */
	
	//Starts the execution of CPU-Cycles
	public void startMainExecutionCycle()
	  {
	   if(simulator.programMemoryContainsProgram)
		  {
			while(Pic16F84Registers.PC < simulator.amountOfCPUCycles)
				if(!checkForBreakPoint(Pic16F84Registers.PC, lstLabelList) || jumpThroughBreakpoint)
				  {
				   System.out.println("Initializing next Cycle...");
				   simulator.step();
                   repaint();
				   try {Thread.sleep(500);} catch (InterruptedException e1) {}
				   jumpThroughBreakpoint = false;
				  }
			
			if(checkForBreakPoint(Pic16F84Registers.PC, lstLabelList))
				jumpThroughBreakpoint = true;
		  }
	  }
	
	/* ************************************************************************ */
	/* ****************************** MAIN ROUTINE **************************** */
	/* ************************************************************************ */
  
  
  
  /* >>> Drawing onto the Panel <<< */
  
  
  @Override
  public void paint(Graphics g)
    {
	  super.paint(g);
	  markLine(Pic16F84Registers.PC, lstLabelList);
	}

  
  
  /* >>> Interface implementations <<< */

  
@Override
public void actionPerformed(ActionEvent e) 
    {
	 //Starting Simulator
	 if(e.getSource() == runButton)
	    mainExecutionThread.start();
	 
	 //Stoping Simulator
	 if(e.getSource() == stopButton)
	   mainExecutionThread.stop();

	 
	 if(e.getSource() == stepButton)
		 lstLabelList[6].setBackground(Color.PINK);
	 
	 if(e.getSource() == resetButton)
		 lstLabelList[6].setBackground(Color.BLUE);

	}

@Override
public void mouseClicked(MouseEvent e) {}


@Override
public void mouseEntered(MouseEvent e) {}


@Override
public void mouseExited(MouseEvent e) {}


@Override
public void mousePressed(MouseEvent e) 
{
 TaggedLabel clickedLabel = (TaggedLabel)e.getSource();
 clickedLabel.toggleBreakpoint();
 markLine(Pic16F84Registers.PC, lstLabelList);
 repaint();

}


@Override
public void mouseReleased(MouseEvent e) {}
  
}
