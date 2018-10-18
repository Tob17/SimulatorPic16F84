
public class CommandExecution {
	
	/* TODO: Taking an interpreted command and starts a sequence of operation depending on the command */
	/* TODO: Reading arguments and operands on the adresses stated in the commands */
	
	
	/* >>> EXECUTION <<< */
	
	//Most important routine of Command execution! (does an DECODE-call to CommandInterpreter)
    static void execute(int command)
	  {
	   int[] opcAndArguments = CommandInterpreter.decode(command);
	   
	   Pic16F84Registers.PC++; // inkrementing PC as part of the execution!
		
	   switch(opcAndArguments[0]) //OPC
	     { 			   
		  case 0x3000: //MOVLW (Move A1 to W)
			System.out.println("Moving " + String.format("0x%2X", opcAndArguments[1]) + "H" + " to W" );
		    Pic16F84Registers.W_REGISTER = (byte) opcAndArguments[1];
		    checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			   	
		  case 0x3900: //ANDLW (A1 AND W to W)
			System.out.println(String.format("0x%2X", opcAndArguments[1]) + " AND " + String.format("0x%2X", Pic16F84Registers.W_REGISTER)  + " to W" );
			Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER & opcAndArguments[1]); 				  
			checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
				
		  case 0x3800: //IORLW (A1 IOR W to W)
			System.out.println(String.format("0x%2X", opcAndArguments[1]) + " IOR " + String.format("0x%2X", Pic16F84Registers.W_REGISTER)  + " to W" );
		    Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER | opcAndArguments[1]); 				  
		    checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			   	
		  case 0x3C00: //SUBLW (A1 - W to W)
			System.out.println(String.format("0x%2X", opcAndArguments[1]) + " - " + String.format("0x%2X", Pic16F84Registers.W_REGISTER)  + " to W" );
			checkRegisterForDigitCarry((byte)opcAndArguments[1],(byte)(~Pic16F84Registers.W_REGISTER+1));
			checkForUnderflow(Pic16F84Registers.W_REGISTER, (byte)opcAndArguments[1]);
		    Pic16F84Registers.W_REGISTER = (byte) (opcAndArguments[1] + (~Pic16F84Registers.W_REGISTER + 1)); 				  
			checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
				 
		  case 0x3A00: //XORLW (A1 XOR W to W)
			System.out.println(String.format("0x%2X", opcAndArguments[1]) + " XOR " + String.format("0x%2X", Pic16F84Registers.W_REGISTER)  + " to W" );
		    Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER ^ opcAndArguments[1]); 				  
		    checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			  
		  case 0x3E00: //ADDLW (A1 + W to W)
			System.out.println(String.format("0x%2X", opcAndArguments[1]) + " + " + String.format("0x%2X", Pic16F84Registers.W_REGISTER)  + " to W" );
		    checkRegisterForDigitCarry((byte)opcAndArguments[1], Pic16F84Registers.W_REGISTER);
		    checkForOverflow((byte)opcAndArguments[1], Pic16F84Registers.W_REGISTER); 
			Pic16F84Registers.W_REGISTER = (byte) (opcAndArguments[1] + Pic16F84Registers.W_REGISTER); 				  
			checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			
		  case 0x2800: //GOTO (Jump to adress A1)
			System.out.println("Jumping to: " + String.format("0x%2X", opcAndArguments[1]));
			Pic16F84Registers.PC = (short) opcAndArguments[1];
			break;	
		 } 
	  }
    
    
    
    /* >>> FLAG-CHECKER <<< */
	
    //Checks a certain register if it hold the value 0
	static void checkRegisterForZero(byte register)
	  {
	    if(register == 0)
		  Pic16F84Registers.set_Flag("Z_FLAG");
		else Pic16F84Registers.reset_Flag("Z_FLAG");
	  }
	
	//Checks if a Digit Carry is occuring during an arithmetik(+,-,*,:) operation
	static void checkRegisterForDigitCarry(byte value1, byte value2)
	  {
		if((value1 & 0b00001111) + (value2 & 0b00001111)  > 0xF)
          Pic16F84Registers.set_Flag("DC_FLAG");
        else
          Pic16F84Registers.reset_Flag("DC_FLAG");   	  
	  }
	
	//Checks for onderflows when adding
	static void checkForOverflow(byte value1, byte value2)
	  {
		int result = value1 + value2;
		
		if(result > 255)
		  Pic16F84Registers.set_Flag("C_FLAG");
		else
		  Pic16F84Registers.reset_Flag("C_FLAG");
	  }
	
	//Checks for underflow when subtracting
	static void checkForUnderflow(byte value1, byte value2)
	  {
		if(value1 < value2)
		  Pic16F84Registers.set_Flag("C_FLAG");
		else
		  Pic16F84Registers.reset_Flag("C_FLAG");
	  }
	
	
}
