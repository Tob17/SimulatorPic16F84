
public class CommandInterpreter {

	/* TODO: Taking a line of machine code and translating it into an equivalent command specified by the Pic16F84-Instruction set and -architecture */
	/* NOTE: This class does not provide any executional features!! it´s meant to be a simple mapper of machinecode > command! */
	/* TODO: Also takes the address of the command found in the RAM to make sure that markers are properly mapped! */
	
	
	//Takes a command and extracts OPC and arguments (Harvard-Architecture)
	static int[] decode(int command)
      {
	   //integer-array holding opc and arguments
	   int[] opcAndArguments = new int[3];
		
	   //masks for OPC and arguments	
	   int maskCommand = 0x3F00;
	   int maskArgument = 0xFF;
	   
	   int opc = command & maskCommand;
	   int argument_1 = command & maskArgument;
		
	  /* System.out.println("Mask:            " + Integer.toBinaryString( maskCommand));
	   System.out.println("Command:         " + Integer.toBinaryString(command ));
	   System.out.println("Mask and Command:" + Integer.toBinaryString(opc));
	   System.out.println("Mask and Command:" + opc);*/
	   
	   //Writing opc and arguments into the array
	   opcAndArguments[0] = opc;
	   opcAndArguments[1] = argument_1;
		
	   return opcAndArguments;
	  }
	
	
}

