import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSplitPane;



public class SimulatorGUI extends JPanel implements ActionListener, MouseListener, AdjustmentListener {
	
	
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

  //Thread for executing commands
  ExecutionThread mainExecutionThread;
  
  //GUI-Elements
  JButton runButton = new JButton("Run");
  JButton stopButton = new JButton("Stop");
  JButton stepButton = new JButton("Step");
  JButton resetButton = new JButton("Reset");
  
  TaggedLabel lstLabelList[] = null;
  
  JLabel runtimeCounterLabel = new JLabel();
  JLabel clockFrequencyLabel = new JLabel();
  JScrollBar clockFrequencyScrollbar = new JScrollBar();
 
  
  
  
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
	  JPanel contentPanel9 = new JPanel();
	  JPanel contentPanel10 = new JPanel();
	  
	  contentPanel1.setBackground(Color.RED);
	  contentPanel2.setBackground(Color.YELLOW);
	  contentPanel3.setBackground(Color.GREEN);
	  contentPanel4.setBackground(Color.BLUE);
	  contentPanel5.setBackground(Color.WHITE);
	  contentPanel6.setBackground(Color.BLACK);
	  contentPanel7.setBackground(Color.WHITE);
	  contentPanel8.setBackground(Color.PINK);  
	  contentPanel9.setBackground(Color.ORANGE); 
	  contentPanel10.setBackground(Color.BLUE); 
	  
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
	  layoutPanel4.setLayout(new GridLayout(1,3,5,5));
	  
	  layoutPanel1.add(contentPanel1);
	  layoutPanel1.add(contentPanel2);
	  layoutPanel1.add(contentPanel3);
	  layoutPanel1.add(contentPanel4);
	  layoutPanel2.add(contentPanel5);
	  layoutPanel3.add(contentPanel6);
	  layoutPanel3.add(contentPanel7);
	  layoutPanel4.add(contentPanel8);
	  layoutPanel4.add(contentPanel9);
	  layoutPanel4.add(contentPanel10);
	  
	  contentPanel1.setLayout(new GridLayout(1,1,5,5));
	  contentPanel2.setLayout(new GridLayout(1,1,5,5));
	  contentPanel3.setLayout(new GridLayout(1,1,5,5));
	  contentPanel4.setLayout(new GridLayout(1,1,5,5));
	  contentPanel5.setLayout(new GridLayout(1,1,5,5));
	  contentPanel6.setLayout(new GridLayout(1,1,5,5));
	  contentPanel7.setLayout(new GridLayout(1,1,5,5));
	  contentPanel8.setLayout(new GridBagLayout());
	  contentPanel9.setLayout(new GridBagLayout());
	  contentPanel10.setLayout(new GridLayout(1,1,5,5));
	  
	  /* Adding GUI-Content */
	  
	  contentPanel1.add(runButton);
	  contentPanel2.add(stopButton);
	  contentPanel3.add(stepButton);
	  contentPanel4.add(resetButton);
	  
	  contentPanel8.add(runtimeCounterLabel);
	  runtimeCounterLabel.setFont(new Font("Arial", 30, 30));
	  runtimeCounterLabel.setForeground(Color.WHITE);	
	  
	  contentPanel9.add(clockFrequencyLabel);
	  clockFrequencyLabel.setFont(new Font("Arial", 30, 30));
	  clockFrequencyLabel.setForeground(Color.WHITE);
	  clockFrequencyLabel.setText("Example");	  
	  
	  
	  contentPanel10.add(this.clockFrequencyScrollbar);
	  clockFrequencyScrollbar.setOrientation(JScrollBar.HORIZONTAL);
	  clockFrequencyScrollbar.setMinimum(32);
	  clockFrequencyScrollbar.setMaximum(20010);
	  clockFrequencyScrollbar.setValue(4000);
	  

	  
	  initLSTGrid(contentPanel5);
	  
	  /* Activating Signal-Listener */
	  
	  runButton.addActionListener(this);
	  stopButton.addActionListener(this);
	  stepButton.addActionListener(this);
	  resetButton.addActionListener(this);  
	  
	  clockFrequencyScrollbar.addAdjustmentListener(this);
	  
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
	   if(labelList != null)
	     {
	      for(int a = 0; a < labelList.length; a++)
		    {
		     if(labelList[a] != null)
		       {
			    if((Pic16F84Registers.PC == labelList[a].lineNumber) && labelList[a].breakpointActive)
			      labelList[a].setBackground(Color.MAGENTA);
			    else 
			      {
				   if(Pic16F84Registers.PC == labelList[a].lineNumber)	
				     labelList[a].setBackground(Color.BLUE);

				   if(labelList[a].breakpointActive)
				     labelList[a].setBackground(Color.RED);

				   if(!(Pic16F84Registers.PC == labelList[a].lineNumber) && !(labelList[a].breakpointActive))
				     labelList[a].setBackground(Color.GREEN);  
				
				   if(labelList[a].lineNumber == -1)
				     labelList[a].setBackground(Color.LIGHT_GRAY); 
				  } 
			  }
		  else
		    continue;
         } 
	   }
	 }
	
	
	/* ************************************************************************ */
	/* ****************************** MAIN ROUTINE **************************** */
	/* ************************************************************************ */	
	
    //Starts the execution of CPU-Cycles
	public void startMainExecutionCycle(ExecutionThread executedByThisThreadObject) throws InterruptedException
	  {
	   if(simulator.programMemoryContainsProgram)
		  {
			while(!executedByThisThreadObject.executionFinished && Pic16F84Registers.PC < simulator.amountOfCPUCycles)
			  {
			   if(!checkForBreakPoint(Pic16F84Registers.PC, lstLabelList))
			     {
				  System.out.println("Initializing next Cycle...");
				  simulator.executeStep();
				  repaint();
				  try {Thread.sleep(50);} catch (InterruptedException e1) {}
				 }
			   else
			     {
				  System.out.println(">>>Breakpoint reached<<<");
				  try {Thread.sleep(50);} catch (InterruptedException e1) {}
				 }
			  }
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
	  
	  DecimalFormat formating = new DecimalFormat();
	  formating.setMaximumFractionDigits(2);
	  runtimeCounterLabel.setText(String.valueOf(formating.format(Simulator.runtimeCounter) + " uS"));
	  clockFrequencyLabel.setText(String.valueOf((int)Simulator.clockFrequency + " Hz"));
	}

  
  
  /* >>> Interface implementations <<< */

  
@Override
public void actionPerformed(ActionEvent e) 
    {
	 //Starting Simulator
	 if(e.getSource() == runButton && mainExecutionThread == null)
	   {	 
		 mainExecutionThread = new ExecutionThread(new Runnable() {public void run() {
			 
		/* ************************ Calling Main Routine in a new Thread ************************** */
			 
		mainExecutionThread.executionFinished = false;
		try {startMainExecutionCycle(mainExecutionThread);} catch (InterruptedException e) {}
		System.out.println("===================================================================="); 
		System.out.println("=====================Execution Completed============================");  
		System.out.println("====================================================================");}
		 
		/* ************************ Calling Main Routine in a new Thread ************************** */
		 
	   });
	    mainExecutionThread.start();
	   }
	 
	 //Stoping Simulator
	 if(e.getSource() == stopButton && mainExecutionThread != null)
	   {mainExecutionThread.stopExecution();
	    mainExecutionThread = null;}

	 //Execute a single-step cycle
	 if(e.getSource() == stepButton)
	   {
		if(simulator.programMemoryContainsProgram && Pic16F84Registers.PC < simulator.amountOfCPUCycles)
		  {
		   if(!checkForBreakPoint(Pic16F84Registers.PC, lstLabelList))
		     {
			  System.out.println("Initializing next Cycle...");
			  try {simulator.executeStep();} catch (InterruptedException e1) {}
			  repaint();
			 }
		   else
		      System.out.println(">>>Breakpoint reached<<<");	 
		  }  
	   }
	 
	 //Resets the controller by resetting every register
	 if(e.getSource() == resetButton)
	   {
		simulator.resetController();
	    Simulator.resetRuntimeCounter();
		repaint();
	   } 
	}


@Override
public void mousePressed(MouseEvent e) 
  {
   TaggedLabel clickedLabel = (TaggedLabel)e.getSource();
   clickedLabel.toggleBreakpoint();
   repaint();
  }


@Override
public void adjustmentValueChanged(AdjustmentEvent e) 
  {
   if(e.getSource() == this.clockFrequencyScrollbar)
   {Simulator.calculateCycleTime((clockFrequencyScrollbar.getValue()*1000));
    repaint();}
  }


@Override
public void mouseClicked(MouseEvent e) {}
@Override
public void mouseEntered(MouseEvent e) {}
@Override
public void mouseExited(MouseEvent e) {}
@Override
public void mouseReleased(MouseEvent e) {}

}
