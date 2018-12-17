
public class ExecutionThread extends Thread {
	
	//Constructor (uses overloaded version of constructor to dynamically overwrite the run-method on instantiation)
	ExecutionThread(Runnable threadMethod)
	{super(threadMethod);}
	
	//Global variable to control the execution of its threated run()-Method
	boolean executionFinished = false;
	
	public void stopExecution()
	{executionFinished = true;}
	
}
