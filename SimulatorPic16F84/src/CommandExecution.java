
public class CommandExecution {
	
	/* TODO: Taking an interpreted command and starts a sequence of operation depending on the command */
	/* TODO: Reading arguments and operands on the adresses stated in the commands */
	
	static void execute(int command)
	{
		int[] opcAndArguments = CommandInterpreter.decode(command);
		
		   switch(opcAndArguments[0]) //opc
		     { 
			  case 0x3000: //MOVLW
			    Pic16F84Registers.W_REGISTER = opcAndArguments[1];
			   	break;	
			 } 
	}
}
