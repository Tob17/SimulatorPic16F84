
public class ExecutionThread extends Thread {
	
	//Global variable to control the execution of its threated run()-Method
	volatile boolean executionFinished = false;
	
	//The Simulator-Panel is referenced by the threads executed run()-Method
	SimulatorGUI simpane;
	
	//Constructor
	ExecutionThread(SimulatorGUI simpane)
	{this.simpane = simpane;}
	
	
	/* ************************ Calling Main Routine ************************** */
	
	//Method that is called when creating a new Thread
	public void run()
	  {
	   executionFinished = false;
	   startMainExecutionCycle();
	   System.out.println("===================================================================="); 
	   System.out.println("=====================Execution Completed============================");  
	   System.out.println("====================================================================");  
	  }
	
	/* ************************ Calling Main Routine ************************** */
	
	
	public void stopExecution()
	{executionFinished = true;}
	
	
	/* ************************************************************************ */
	/* ****************************** MAIN ROUTINE **************************** */
	/* ************************************************************************ */	
	
    //Starts the execution of CPU-Cycles
	public void startMainExecutionCycle()
	  {
	   if(simpane.simulator.programMemoryContainsProgram)
		  {
			while(!executionFinished && Pic16F84Registers.PC < simpane.simulator.amountOfCPUCycles)
			  {
			   if(!simpane.checkForBreakPoint(Pic16F84Registers.PC, simpane.lstLabelList))
			     {
				  System.out.println("Initializing next Cycle...");
				  simpane.simulator.executeStep();
				  simpane.repaint();
				  try {Thread.sleep(500);} catch (InterruptedException e1) {}
				 }
			   else
			     {
				  System.out.println(">>>Breakpoint reached<<<");
				  try {Thread.sleep(500);} catch (InterruptedException e1) {}
				 }
			  }
		  }
	  }	
	
	/* ************************************************************************ */
	/* ****************************** MAIN ROUTINE **************************** */
	/* ************************************************************************ */
	
}
