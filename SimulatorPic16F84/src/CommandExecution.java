
public class CommandExecution {
	
	/* TODO: implement all of the 35 instructions */
	/* TODO: Reading arguments and operands on the adresses stated in the commands */
	
	
	 /* >>> EXECUTION <<< */
	
	//Most important routine of Command execution! (does an DECODE-call to CommandInterpreter)
    static void execute(int command)
	  {
	   int[] opcAndArguments = CommandInterpreter.decode(command);
	   
	   Pic16F84Registers.incrementPC(); // inkrementing PC as part of the execution!
	   
	   switch(opcAndArguments[0]) //OPC
	     { 			
	     case 0x0700:
	    	 System.out.println("ADDWF");
	    	 break;
	     case 0x0500:
	    	 System.out.println("ANDWF");
	    	 break;
	     case 0x0180:
	    	 System.out.println("CLRF");
	    	 break;
	     case 0x0100:
	    	 System.out.println("CLRW");
	    	 break;
	     case 0x0900:
	    	 System.out.println("COMF");
	    	 break;
	     case 0x0300:
	    	 System.out.println("DECF");
	    	 break;
	     case 0x0B00:
	    	 System.out.println("DECFSZ");
	    	 break;
	     case 0x0A00:
	    	 System.out.println("INCF");
	    	 break;
	     case 0x0F00:
	    	 System.out.println("INCFSZ");
	    	 break;
	     case 0x0400:
	    	 System.out.println("IORWF");
	    	 break;
	     case 0x0800:
	    	 System.out.println("MOVF");
	    	 break;
	     case 0x0080:
	    	 System.out.println("MOVWF");
	    	 break;
	     case 0x0000:
	    	 System.out.println("NOP");
	    	 break;
	     case 0x0D00:
	    	 System.out.println("RLF");
	    	 break;
	     case 0x0C00:
	    	 System.out.println("RRF");
	    	 break;
	     case 0x0200:
	    	 System.out.println("SUBWF");
	    	 break;
	     case 0x0E00:
	    	 System.out.println("SWAPF");
	    	 break;
	     case 0x0600:
	    	 System.out.println("XORWF");
	    	 break;
	    
	     case 0x1000:
	    	 System.out.println("BCF");
	    	 break;
	     case 0x1400:
	    	 System.out.println("BSF");
	    	 break;
	     case 0x1800:
	    	 System.out.println("BTFSC");
	    	 break;
	     case 0x1C00:
	    	 System.out.println("BTFSS");
	    	 break;
	    	 
	     case 0x3E00: //ADDLW (A1 + W to W)
			  System.out.println("ADDLW");
//			System.out.println("ADDLW: " + String.format("%2X", opcAndArguments[1]) + "h" + " + " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W" );
//		    checkRegisterForDigitCarry((byte)opcAndArguments[1], Pic16F84Registers.W_REGISTER);
//		    checkForOverflow((byte)opcAndArguments[1], Pic16F84Registers.W_REGISTER); 
//			Pic16F84Registers.W_REGISTER = (byte) (opcAndArguments[1] + Pic16F84Registers.W_REGISTER); 				  
//			checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			
	     case 0x3900: //ANDLW (A1 AND W to W)
			  System.out.println("ANDLW");
			//System.out.println("ANDLW: " + String.format("%2X", opcAndArguments[1]) + "h" + " AND " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W" );
			//Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER & opcAndArguments[1]); 				  
			//checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	 
	    	 
	     case 0x2000:
	    	 System.out.println("CALL");
			break;
	     
	     case 0x0064:
	    	 System.out.println("CLRWDT");
			break;
			
	     case 0x2800: //GOTO (Jump to adress A1)
			  System.out.println("GOTO");
			  //			System.out.println("GOTO: " + "Jumping to adress: " + String.format("%2X", opcAndArguments[1]) + "h");
//			Pic16F84Registers.flushInstructionRegister();
//			Pic16F84Registers.computedGOTO((short)opcAndArguments[1]);
			break;	
			
	     case 0x3800: //IORLW (A1 IOR W to W)
			  System.out.println("IORLW");
			//System.out.println("IORLW: " + String.format("%2X", opcAndArguments[1]) + "h" + " IOR " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W" );
		    //Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER | opcAndArguments[1]); 				  
		    //checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			   	
			
	     case 0x3000: //MOVLW (Move A1 to W)
			  System.out.println("MOVLW");
			//System.out.println("MOVLW: " + "Moving " + String.format("%2X", opcAndArguments[1]) + "h" + " to W" );
		    //Pic16F84Registers.W_REGISTER = (byte) opcAndArguments[1];
		    //checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
	     
	     case 0x0009: //IORLW (A1 IOR W to W)
			  System.out.println("RETFIE");
			break;	
			   	
	     case 0x3400: //IORLW (A1 IOR W to W)
			  System.out.println("RETLW");
			break;	
			   		
	     case 0x0008: //IORLW (A1 IOR W to W)
			  System.out.println("RETURN");
			break;	
			   	
	     case 0x0063: //IORLW (A1 IOR W to W)
			  System.out.println("SLEEP");
			break;	
			   	
	     case 0x3C00: //SUBLW (A1 - W to W)
			  System.out.println("SUBLW");
			//System.out.println("SUBLW: " + String.format("%2X", opcAndArguments[1]) + "h" + " - " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W" );
			//checkRegisterForDigitCarry((byte)opcAndArguments[1],(byte)(~Pic16F84Registers.W_REGISTER+1));
			//checkForUnderflow(Pic16F84Registers.W_REGISTER, (byte)opcAndArguments[1]);
		    //Pic16F84Registers.W_REGISTER = (byte) (opcAndArguments[1] + (~Pic16F84Registers.W_REGISTER + 1)); 				  
			//checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
				 
		  case 0x3A00: //XORLW (A1 XOR W to W)
			 System.out.println("XORLW");
			//System.out.println("XORLW: " + String.format("%2X", opcAndArguments[1]) + "h" + " XOR " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W" );
		    //Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER ^ opcAndArguments[1]); 				  
		    //checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			  
		
			
		  
		 } 
	  }
    
    
    
    /* >>> FLAG-CHECKER <<< */
	
    //Checks a certain register if it hold the value 0
	static void checkRegisterForZero(byte register)
	  {
	    if(register == 0)
		  Pic16F84Registers.set_Flag("Z_FLAG");
		else
		  Pic16F84Registers.reset_Flag("Z_FLAG");
	  }
	
	//Checks if a Digit Carry is occuring during an arithmetik(+,-,*,:) operation
	static void checkRegisterForDigitCarry(byte value1, byte value2)
	  {
		if((value1 & 0b00001111) + (value2 & 0b00001111)  > 0xF)
          Pic16F84Registers.set_Flag("DC_FLAG");
        else
          Pic16F84Registers.reset_Flag("DC_FLAG");   	  
	  }
	
	//Checks for overflows when adding
	static void checkForOverflow(byte value1, byte value2)
	  {
		int result = (int)value1 + (int)value2;
		
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
