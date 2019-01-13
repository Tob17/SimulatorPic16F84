import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;


public class SimulatorGUI extends JPanel implements ActionListener, MouseListener, AdjustmentListener, ItemListener {
	
	
  /* >>> Class-Methods <<< */
	
	
  public static void init(String filePath)  throws IOException 
    {
     //Initializing Frame and Main-Panel, while grabbing the Main-Panel
     SimulatorGUI simPane = initFrame();
     //Initializing the Simulator
     simPane.lstText = simPane.simulator.initSimulator(filePath); 
     //Setting Layout of the Main-Panel
     simPane.initLayout();
     //Repainting everything after everything has been loaded!
     simPane.repaint();
    }
	
	
  // Creating and initializing a Frame that hold our GUI
  public static SimulatorGUI initFrame()
    {
	 JFrame simulatorWindow = new JFrame("Simulator Pic16F84");
	 SimulatorGUI simulatorContentPane = new SimulatorGUI();
		
	 //Adding a main-pane into the Frame and setting sizes and visibility
	 simulatorWindow.setContentPane(simulatorContentPane);
	 simulatorWindow.setSize(1600,900);
	 simulatorWindow.setVisible(true);
	 simulatorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	 return simulatorContentPane;
	}
  
  
  /* >>> Class-Objects <<< */
  
  //Global, static JTextArea which catches console-outputs (System.out.print(...))
  public static JTextArea consoleOutput = new JTextArea();
  
  
  
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
  
  JCheckBox portAPin0 = new JCheckBox("portAPin0");
  JCheckBox portAPin1 = new JCheckBox("portAPin1");
  JCheckBox portAPin2 = new JCheckBox("portAPin2");
  JCheckBox portAPin3 = new JCheckBox("portAPin3");
  JCheckBox portAPin4 = new JCheckBox("portAPin4");
  
  JCheckBox portBPin0 = new JCheckBox("portBPin0");
  JCheckBox portBPin1 = new JCheckBox("portBPin1");
  JCheckBox portBPin2 = new JCheckBox("portBPin2");
  JCheckBox portBPin3 = new JCheckBox("portBPin3");
  JCheckBox portBPin4 = new JCheckBox("portBPin4");
  JCheckBox portBPin5 = new JCheckBox("portBPin5");
  JCheckBox portBPin6 = new JCheckBox("portBPin6");
  JCheckBox portBPin7 = new JCheckBox("portBPin7");
  
  JLabel wRegisterLabel = new JLabel("W-Register");
  JLabel pcLabel = new JLabel("PC");
  JLabel pclLabel = new JLabel("PCL");
  JLabel pcLathLabel = new JLabel("PCLATH");
  JLabel pswLabel = new JLabel("PSW");
  JLabel stackpointerLabel = new JLabel("Stackpointer");
  JLabel instructionRegisterLabel = new JLabel("Instruction-Register");
  JLabel fsrLabel = new JLabel("FSR");
  JLabel portALabel = new JLabel("PORTA");
  JLabel portBLabel = new JLabel("PORTB");
  JLabel trisALabel = new JLabel("TRISA");
  JLabel trisBLabel = new JLabel("TRISB");
  JLabel pinsALabel = new JLabel("PINSA");
  JLabel pinsBLabel = new JLabel("PINSB");
  JLabel timer0Label = new JLabel("TIMER0");
  JLabel eedataLabel = new JLabel("EEDATA");
  JLabel eeadrLabel = new JLabel("EEADR");
  JLabel intconLabel = new JLabel("INTCON");
  JLabel optionLabel = new JLabel("OPTION");
  JLabel eecon1Label = new JLabel("EECON1");
  JLabel eecon2Label = new JLabel("EECON2");
  
 
  
  
  
  /* >>> Object-Methods <<< */

  
  //Initializes the main-panel´s layout
  public void initLayout()
  {	   
	  /* Setting up basic layout */
	  
	  
	          /* PANELS */
	  
	  JPanel layoutPanel1 = new JPanel();
	  JPanel layoutPanel2 = new JPanel();
	  JPanel layoutPanel3 = new JPanel();
	  JPanel layoutPanel4 = new JPanel();
	  JPanel layoutPanel5 = new JPanel();
	  
	  JScrollPane scrollPanel = new JScrollPane();
	  JScrollPane scrollPanel2 = new JScrollPane();
	  
	         /* SPLITTER */
	  
	  JSplitPane splitter1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	  splitter1.setResizeWeight(0.10);
	  splitter1.setEnabled(false);
	  splitter1.setDividerSize(0);
	  
	  JSplitPane splitter2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	  splitter2.setResizeWeight(0.95);
	  splitter2.setEnabled(false);
	  splitter2.setDividerSize(0);
	  
	  JSplitPane splitter3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	  splitter3.setResizeWeight(0.15);
	  splitter3.setEnabled(false);
	  splitter3.setDividerSize(0);
	  
	  JSplitPane splitter4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	  splitter4.setResizeWeight(0.15);
	  splitter4.setEnabled(false);
	  splitter4.setDividerSize(0);
	  
	  
	      /* INNER PANELS */
 
	  JPanel contentPanel1 = new JPanel();
	  JPanel contentPanel2 = new JPanel();
	  JPanel contentPanel3 = new JPanel();
	  JPanel contentPanel4 = new JPanel();
	  JPanel contentPanel5 = new JPanel();
	  JPanel contentPanel6 = new JPanel();
	  JPanel contentPanel7_1 = new JPanel();
	  JPanel contentPanel7_2 = new JPanel();
	  JPanel contentPanel8 = new JPanel();
	  JPanel contentPanel9 = new JPanel();
	  JPanel contentPanel10 = new JPanel();
	  
	  contentPanel1.setBackground(Color.RED);
	  contentPanel2.setBackground(Color.YELLOW);
	  contentPanel3.setBackground(Color.GREEN);
	  contentPanel4.setBackground(Color.BLUE);
	  contentPanel5.setBackground(Color.WHITE);
	  contentPanel6.setBackground(Color.CYAN);
	  contentPanel7_1.setBackground(Color.WHITE);
	  contentPanel7_2.setBackground(Color.ORANGE);
	  contentPanel8.setBackground(Color.PINK);  
	  contentPanel9.setBackground(Color.ORANGE); 
	  contentPanel10.setBackground(Color.BLUE); 
	  
	  
	      /* SETTING LAYOUT */
	  
	  this.setLayout(new GridLayout(1,2, 5, 5));
	  
	  splitter1.add(layoutPanel1);
	  splitter1.add(layoutPanel2);
	  add(splitter1);
	  
	  splitter2.add(layoutPanel3);
	  splitter2.add(layoutPanel4);
	  add(splitter2);
	  
	  splitter3.add(contentPanel6);
	  splitter3.add(layoutPanel5);
	  add(splitter3);
	  
	  splitter4.add(contentPanel7_1);
	  splitter4.add(scrollPanel2);
	  add(splitter4);
	  
	  layoutPanel1.setLayout(new GridLayout(1,4,5,5));
	  layoutPanel2.setLayout(new GridLayout(1,1,5,5));
	  layoutPanel3.setLayout(new GridLayout(1,1,5,5));
	  layoutPanel4.setLayout(new GridLayout(1,3,5,5));
	  layoutPanel5.setLayout(new GridLayout(1,1,5,5));
	  
	  scrollPanel.setViewportView(contentPanel5);
	  
	  layoutPanel1.add(contentPanel1);
	  layoutPanel1.add(contentPanel2);
	  layoutPanel1.add(contentPanel3);
	  layoutPanel1.add(contentPanel4);
	  layoutPanel2.add(scrollPanel);
	  layoutPanel3.add(splitter3);
	  layoutPanel4.add(contentPanel8);
	  layoutPanel4.add(contentPanel9);
	  layoutPanel4.add(contentPanel10);
	  layoutPanel5.add(splitter4);
	  
	  contentPanel1.setLayout(new GridLayout(1,1,5,5));
	  contentPanel2.setLayout(new GridLayout(1,1,5,5));
	  contentPanel3.setLayout(new GridLayout(1,1,5,5));
	  contentPanel4.setLayout(new GridLayout(1,1,5,5));
	  contentPanel5.setLayout(new GridLayout(1,1,5,5));
	  contentPanel6.setLayout(new GridLayout(2,8,5,5));
	  contentPanel7_1.setLayout(new GridLayout(3,7,5,5));
	  contentPanel7_2.setLayout(new GridLayout(1,1,5,5));
	  contentPanel8.setLayout(new GridBagLayout());
	  contentPanel9.setLayout(new GridBagLayout());
	  contentPanel10.setLayout(new GridLayout(1,1,5,5));
	  
	  
      /* ADDING AND CONFIGURATING GUI-ELEMENTS */
	  
	  contentPanel1.add(runButton);
	  contentPanel2.add(stopButton);
	  contentPanel3.add(stepButton);
	  contentPanel4.add(resetButton);
	  
	  initLSTGrid(contentPanel5);
	  
	  contentPanel6.add(portAPin0);
	  contentPanel6.add(portAPin1);
	  contentPanel6.add(portAPin2);
	  contentPanel6.add(portAPin3);
	  contentPanel6.add(portAPin4);
	  contentPanel6.add(new JPanel() {public void paint(Graphics g) {this.setVisible(false);}});
	  contentPanel6.add(new JPanel() {public void paint(Graphics g) {this.setVisible(false);}});
	  contentPanel6.add(new JPanel() {public void paint(Graphics g) {this.setVisible(false);}});
	  
	  contentPanel6.add(portBPin0);
	  contentPanel6.add(portBPin1);
	  contentPanel6.add(portBPin2);
	  contentPanel6.add(portBPin3);
	  contentPanel6.add(portBPin4);
	  contentPanel6.add(portBPin5);
	  contentPanel6.add(portBPin6);
	  contentPanel6.add(portBPin7);
	  
	  contentPanel7_1.add(wRegisterLabel);
	  wRegisterLabel.setHorizontalAlignment(wRegisterLabel.getWidth()/2);
	  contentPanel7_1.add(pcLabel);
	  pcLabel.setHorizontalAlignment(pcLabel.getWidth()/2);
	  contentPanel7_1.add(pclLabel);
	  pclLabel.setHorizontalAlignment(pclLabel.getWidth()/2);
	  contentPanel7_1.add(pcLathLabel);
	  pcLathLabel.setHorizontalAlignment(pcLathLabel.getWidth()/2);
	  contentPanel7_1.add(pswLabel);
	  pswLabel.setHorizontalAlignment(pswLabel.getWidth()/2);
	  contentPanel7_1.add(stackpointerLabel);
	  stackpointerLabel.setHorizontalAlignment(stackpointerLabel.getWidth()/2);
	  contentPanel7_1.add(instructionRegisterLabel);
	  instructionRegisterLabel.setHorizontalAlignment(instructionRegisterLabel.getWidth()/2);
	  contentPanel7_1.add(fsrLabel);
	  fsrLabel.setHorizontalAlignment(fsrLabel.getWidth()/2);
	  contentPanel7_1.add(portALabel);
	  portALabel.setHorizontalAlignment(portALabel.getWidth()/2);
	  contentPanel7_1.add(portBLabel);
	  portBLabel.setHorizontalAlignment(portBLabel.getWidth()/2);
	  contentPanel7_1.add(trisALabel);
	  trisALabel.setHorizontalAlignment(trisALabel.getWidth()/2);
	  contentPanel7_1.add(trisBLabel);
	  trisBLabel.setHorizontalAlignment(trisBLabel.getWidth()/2);
	  contentPanel7_1.add(pinsALabel);
	  pinsALabel.setHorizontalAlignment(pinsALabel.getWidth()/2);
	  contentPanel7_1.add(pinsBLabel);
	  pinsBLabel.setHorizontalAlignment(pinsBLabel.getWidth()/2);
	  contentPanel7_1.add(timer0Label);
	  timer0Label.setHorizontalAlignment(timer0Label.getWidth()/2);
	  contentPanel7_1.add(eedataLabel);
	  eedataLabel.setHorizontalAlignment(eedataLabel.getWidth()/2);
	  contentPanel7_1.add(eeadrLabel);
	  eeadrLabel.setHorizontalAlignment(eeadrLabel.getWidth()/2);
	  contentPanel7_1.add(intconLabel);
	  intconLabel.setHorizontalAlignment(intconLabel.getWidth()/2);
	  contentPanel7_1.add(optionLabel);
	  optionLabel.setHorizontalAlignment(optionLabel.getWidth()/2);
	  contentPanel7_1.add(eecon1Label);
	  eecon1Label.setHorizontalAlignment(eecon1Label.getWidth()/2);
	  contentPanel7_1.add(eecon2Label);
	  eecon2Label.setHorizontalAlignment(eecon2Label.getWidth()/2);
	  
	  scrollPanel2.setViewportView(contentPanel7_2);
	  contentPanel7_2.add(consoleOutput);
	  
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
	
	  
	  
	  
	  /* ADDING LISTENER TO GUI-ELEMENTS */
	  
	  runButton.addActionListener(this);
	  stopButton.addActionListener(this);
	  stepButton.addActionListener(this);
	  resetButton.addActionListener(this);  
	  
	  clockFrequencyScrollbar.addAdjustmentListener(this);
	  
	  for(int a = 0; a < lstLabelList.length; a++)
		  lstLabelList[a].addMouseListener(this);
	  
	  portAPin0.addItemListener(this);
	  portAPin1.addItemListener(this);
	  portAPin2.addItemListener(this);
	  portAPin3.addItemListener(this);
	  portAPin4.addItemListener(this);
	  
	  portBPin0.addItemListener(this);
	  portBPin1.addItemListener(this);
	  portBPin2.addItemListener(this);
	  portBPin3.addItemListener(this);
	  portBPin4.addItemListener(this);
	  portBPin5.addItemListener(this);
	  portBPin6.addItemListener(this);
	  portBPin7.addItemListener(this);
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
		    lstLabelList[i].lineNumber = Integer.decode("0x"+lstText[i].substring(0,4));  	 
		  else
			  lstLabelList[i].lineNumber = -1; 
	      
	      LSTPanel.add(lstLabelList[i]);
	    }
     }
  
  public void updateRegisterLabels()
    {
	  wRegisterLabel.setText("W: " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h");
	  pcLabel.setText("PC: " + String.format("%2X", Pic16F84Registers.PC) + "h");
	  pclLabel.setText("PCL: " + String.format("%2X", Pic16F84Registers.PCL) + "h");
	  pcLathLabel.setText("PCLATH: " + String.format("%2X", Pic16F84Registers.PCLATH) + "h");
	  pswLabel.setText("PSW: " + String.format("%2X", Pic16F84Registers.PSW) + "h");
	  stackpointerLabel.setText("STKPTR: " + String.format("%2X", Pic16F84Registers.STACKPOINTER) + "h");
	  instructionRegisterLabel.setText("IR: " + String.format("%2X", Pic16F84Registers.INSTRUCTION_REGISTER) + "h");
	  fsrLabel.setText("FSR: " + String.format("%2X", Pic16F84Registers.FSR) + "h");
	  portALabel.setText("PORTA: " + String.format("%2X", Pic16F84Registers.PORT_A_REGISTER) + "h");
	  portBLabel.setText("PORTB: " + String.format("%2X", Pic16F84Registers.PORT_B_REGISTER) + "h");
	  trisALabel.setText("TRISA: " + String.format("%2X", Pic16F84Registers.TRIS_A_REGISTER) + "h");
	  trisBLabel.setText("TRISB: " + String.format("%2X", Pic16F84Registers.TRIS_B_REGISTER) + "h");;
	  pinsALabel.setText("PINSA: " + String.format("%2X", Pic16F84Registers.PORT_A_PINS) + "h");
	  pinsBLabel.setText("PINSB: " + String.format("%2X", Pic16F84Registers.PORT_B_PINS) + "h");
	  timer0Label.setText("TMR0: " + "N/A"); //TODO
	  eedataLabel.setText("EEDATA: " + "N/A"); //TODO
	  eeadrLabel.setText("EEADR: " + "N/A"); //TODO
	  intconLabel.setText("INTCON: " + "N/A"); //TODO
	  optionLabel.setText("OPTION: " + "N/A"); //TODO
	  eecon1Label.setText("EECON1: " + "N/A"); //TODO
	  eecon2Label.setText("EECON2: " + "N/A"); //TODO
    }
  
  
  //Enables or disables Checkboxes according to those bits set in Tris-Registers A and B (1 = input = checkbox enabled, 0 = output = checkbox disabled)
  public void setCheckboxesToTrisState()
    {
	  markCheckboxAsInputOrOutput(portAPin0, 0, 'A');
	  markCheckboxAsInputOrOutput(portAPin1, 1, 'A');
	  markCheckboxAsInputOrOutput(portAPin2, 2, 'A');
	  markCheckboxAsInputOrOutput(portAPin3, 3, 'A');
	  markCheckboxAsInputOrOutput(portAPin4, 4, 'A');
	  markCheckboxAsInputOrOutput(portBPin0, 0, 'B');
	  markCheckboxAsInputOrOutput(portBPin1, 1, 'B');
	  markCheckboxAsInputOrOutput(portBPin2, 2, 'B');
	  markCheckboxAsInputOrOutput(portBPin3, 3, 'B');
	  markCheckboxAsInputOrOutput(portBPin4, 4, 'B');
	  markCheckboxAsInputOrOutput(portBPin5, 5, 'B');
	  markCheckboxAsInputOrOutput(portBPin6, 6, 'B');
	  markCheckboxAsInputOrOutput(portBPin7, 7, 'B');
	  
	  leadPortBitToCheckboxIfSetAsOutput(portAPin0, 0, 'A');
	  leadPortBitToCheckboxIfSetAsOutput(portAPin1, 1, 'A');
	  leadPortBitToCheckboxIfSetAsOutput(portAPin2, 2, 'A');
	  leadPortBitToCheckboxIfSetAsOutput(portAPin3, 3, 'A');
	  leadPortBitToCheckboxIfSetAsOutput(portAPin4, 4, 'A');
	  leadPortBitToCheckboxIfSetAsOutput(portBPin0, 0, 'B');
	  leadPortBitToCheckboxIfSetAsOutput(portBPin1, 1, 'B');
	  leadPortBitToCheckboxIfSetAsOutput(portBPin2, 2, 'B');
	  leadPortBitToCheckboxIfSetAsOutput(portBPin3, 3, 'B');
	  leadPortBitToCheckboxIfSetAsOutput(portBPin4, 4, 'B');
	  leadPortBitToCheckboxIfSetAsOutput(portBPin5, 5, 'B');
	  leadPortBitToCheckboxIfSetAsOutput(portBPin6, 6, 'B');
	  leadPortBitToCheckboxIfSetAsOutput(portBPin7, 7, 'B');
    }
  
  
  //Enables or disables Checkboxes according to those bits set in Tris-Registers A and B (1 = input = checkbox enabled, 0 = output = checkbox disabled)
  public void markCheckboxAsInputOrOutput(JCheckBox checkbox, int trisPin, char portName)
    {
	  if(portName == 'A' || portName == 'a')
	    {
		 if(((Pic16F84Registers.TRIS_A_REGISTER & (1 << trisPin)) >> trisPin) == 0)
		   {checkbox.setEnabled(false);
		    checkbox.setBackground(Color.decode("#E37C83"));}
		 if(((Pic16F84Registers.TRIS_A_REGISTER & (1 << trisPin)) >> trisPin) == 1)
		   {checkbox.setEnabled(true);
		    checkbox.setBackground(Color.decode("#6AEC7C"));}
	    }
	  else if(portName == 'B' || portName == 'b')
	    {
		 if(((Pic16F84Registers.TRIS_B_REGISTER & (1 << trisPin)) >> trisPin) == 0)
		   {checkbox.setEnabled(false);
		    checkbox.setBackground(Color.decode("#E37C83"));}
		 if(((Pic16F84Registers.TRIS_B_REGISTER & (1 << trisPin)) >> trisPin) == 1)
		   {checkbox.setEnabled(true);
		    checkbox.setBackground(Color.decode("#6AEC7C"));}
	    }
	  else
		SimulatorGUI.consoleOutput.append("ERROR: Wrong Port Selected!\n");
    }
  
  
  //Changes state of a checkbox-pin depending on it´s port-bit if set to output (visual update, no actual change on register values)
  public void leadPortBitToCheckboxIfSetAsOutput(JCheckBox checkbox, int trisPin, char portName)
    {
	  if(portName == 'A' || portName == 'a')
	    {
		 int portBitValue = (Pic16F84Registers.PORT_A_REGISTER & (1 << trisPin)) >> trisPin; 

		 if(portBitValue == 1)
		   checkbox.setSelected(true);
		 else
		   checkbox.setSelected(false);
	    }
	  else if(portName == 'B' || portName == 'b')
	    {
		 int portBitValue = (Pic16F84Registers.PORT_B_REGISTER & (1 << trisPin)) >> trisPin; 
			
		 if(portBitValue == 1)
		   checkbox.setSelected(true);
		 else
		   checkbox.setSelected(false);
	    }
	  else
		SimulatorGUI.consoleOutput.append("ERROR: Wrong Port Selected!\n");
    }
  
  
  //On a Checkbox event: sets the corresponding pin underneath to 1 or 0 depending if checkbox has just been checked or unchecked
  public void leadCheckboxEventToPin(JCheckBox checkbox, int pinNumber, char portName)
    {
	 if(checkbox.isSelected())
	   Pic16F84Registers.setPin(pinNumber, portName);
	 else
	    Pic16F84Registers.resetPin(pinNumber, portName);
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
	
	
	//Actualizes the Runtime-Counter and the displayed frequency
	void actualizeCounterAndFrequency()
	  {
	   DecimalFormat formating = new DecimalFormat();
	   formating.setMaximumFractionDigits(2);
	   runtimeCounterLabel.setText(String.valueOf(formating.format(Simulator.runtimeCounter) + " uS"));
	   clockFrequencyLabel.setText(String.valueOf((int)Simulator.clockFrequency + " Hz"));
	  }
	
	
	
	/* ************************************************************************ */
	/* *********************** MAIN ROUTINE (Threaded) ************************ */
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
				  SimulatorGUI.consoleOutput.append("Initializing next Cycle...\n");
				  simulator.executeStep();
				  repaint();
				  try {Thread.sleep(50);} catch (InterruptedException e1) {}
				 }
			   else
			     {
				  SimulatorGUI.consoleOutput.append(">>>Breakpoint reached<<<\n");
				  consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
				  try {Thread.sleep(50);} catch (InterruptedException e1) {}
				 }
			  }
		  }
	  }	
	
	/* ************************************************************************ */
	/* ************************ MAIN ROUTINE (Threaded) *********************** */
	/* ************************************************************************ */	

	
	
  /* >>> Drawing onto the Panel <<< */
  
  
  @Override
  public void paint(Graphics g)
    {
	 super.paint(g);
	 markLine(Pic16F84Registers.PC, lstLabelList);
	 actualizeCounterAndFrequency();
	 setCheckboxesToTrisState();
	 updateRegisterLabels();
	 consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
	}

  
  
  /* >>> Interface-Methods implementation <<< */

  
@Override // Control-Buttons
public void actionPerformed(ActionEvent e) 
    {
	 //Starting Simulator
	 if(e.getSource() == runButton && mainExecutionThread == null)
	   {	 
		 mainExecutionThread = new ExecutionThread(new Runnable() {public void run() {
			 
		/* ************************ Calling Main Routine in a new Thread ************************** */
			 
		mainExecutionThread.executionFinished = false;
		try {startMainExecutionCycle(mainExecutionThread);} catch (InterruptedException e) {}
		SimulatorGUI.consoleOutput.append("====================================================================\n"); 
		SimulatorGUI.consoleOutput.append("=====================Execution Completed============================\n");  
		SimulatorGUI.consoleOutput.append("====================================================================\n");}
		 
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
			  SimulatorGUI.consoleOutput.append("Initializing next Cycle...\n");
			  try {simulator.executeStep();} catch (InterruptedException e1) {}
			  repaint();
			 }
		   else
		      SimulatorGUI.consoleOutput.append(">>>Breakpoint reached<<<\n");	 
		  }  
		consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
	   }
	 
	 //Resets the controller by resetting every register
	 if(e.getSource() == resetButton)
	   {
		simulator.resetController();
	    Simulator.resetRuntimeCounter();
		repaint();
	   } 
	}


@Override // Command-Labels
public void mousePressed(MouseEvent e) 
  {
   TaggedLabel clickedLabel = (TaggedLabel)e.getSource();
   clickedLabel.toggleBreakpoint();
   repaint();
  }


@Override // Frequency Scrollbar
public void adjustmentValueChanged(AdjustmentEvent e) 
  {
   if(e.getSource() == this.clockFrequencyScrollbar)
   {Simulator.calculateCycleTime((clockFrequencyScrollbar.getValue()*1000));
    repaint();}
  }


@Override //Checkboxes for Pincontrol
public void itemStateChanged(ItemEvent e) 
  {
	JCheckBox clickedCheckbox = (JCheckBox)e.getSource();
	
	if(clickedCheckbox == portAPin0)
		leadCheckboxEventToPin(clickedCheckbox, 0, 'A');
	if(clickedCheckbox == portAPin1)
		leadCheckboxEventToPin(clickedCheckbox, 1, 'A');
	if(clickedCheckbox == portAPin2)
		leadCheckboxEventToPin(clickedCheckbox, 2, 'A');
	if(clickedCheckbox == portAPin3)
		leadCheckboxEventToPin(clickedCheckbox, 3, 'A');
	if(clickedCheckbox == portAPin4)
		leadCheckboxEventToPin(clickedCheckbox, 4, 'A');
	if(clickedCheckbox == portBPin0)
		leadCheckboxEventToPin(clickedCheckbox, 0, 'B');
	if(clickedCheckbox == portBPin1)
		leadCheckboxEventToPin(clickedCheckbox, 1, 'B');
	if(clickedCheckbox == portBPin2)
		leadCheckboxEventToPin(clickedCheckbox, 2, 'B');
	if(clickedCheckbox == portBPin3)
		leadCheckboxEventToPin(clickedCheckbox, 3, 'B');
	if(clickedCheckbox == portBPin4)
		leadCheckboxEventToPin(clickedCheckbox, 4, 'B');
	if(clickedCheckbox == portBPin5)
		leadCheckboxEventToPin(clickedCheckbox, 5, 'B');
	if(clickedCheckbox == portBPin6)
		leadCheckboxEventToPin(clickedCheckbox, 6, 'B');
	if(clickedCheckbox == portBPin7)
		leadCheckboxEventToPin(clickedCheckbox, 7, 'B');
	
	Pic16F84Registers.printAllIORegisters();
  }



/* >>> Interfaces not used <<< */

@Override
public void mouseClicked(MouseEvent e) {}
@Override
public void mouseEntered(MouseEvent e) {}
@Override
public void mouseExited(MouseEvent e) {}
@Override
public void mouseReleased(MouseEvent e) {}

}
