
public class CommandInterpreter {

	
	/* TODO: Implement universal decoding of any of the 35 instructions */
	
	
	/* >>> DECODE <<< */
	
	//Takes a command and extracts OPC and arguments (Harvard-Architecture)
	static int[] decode(int command)
      {
	   //Integer-array holding OPC and arguments
	   int[] opcAndArguments = new int[3];
		
	   //Masks for OPC and arguments	
	   int maskCommand = 0x3F00;
	   int maskArgument = 0x00FF;
	   
	   int opc = command & maskCommand;
	   int argument_1 = command & maskArgument;
	   
	   //Writing OPC and arguments into the array
	   opcAndArguments[0] = opc;
	   opcAndArguments[1] = argument_1;
		
	   return opcAndArguments;
	  }
	
	
}

