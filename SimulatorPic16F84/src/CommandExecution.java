
public class CommandExecution {
	
	/* TODO: Taking an interpreted command and starts a sequence of operation depending on the command */
	/* TODO: Reading arguments and operands on the adresses stated in the commands */
	
	static void execute(int command)
	{
		int[] opcAndArguments = CommandInterpreter.decode(command);
		
		   switch(opcAndArguments[0]) //opc
		     { 			   
			  case 0x3000: //MOVLW
			    Pic16F84Registers.W_REGISTER = (byte) opcAndArguments[1];
				  if(Pic16F84Registers.W_REGISTER == 0)
				    	Pic16F84Registers.set_Flag("Z_FLAG");
				  else Pic16F84Registers.reset_Flag("Z_FLAG");
			   	break;	
			   	
			  case 0x3900: //ANDLW
				  Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER & opcAndArguments[1]); 				  
				  if(Pic16F84Registers.W_REGISTER == 0)
				    	Pic16F84Registers.set_Flag("Z_FLAG");
				  else Pic16F84Registers.reset_Flag("Z_FLAG");
				 break;	
				
			  case 0x3800: //IORLW
				  Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER | opcAndArguments[1]); 				  
				  if(Pic16F84Registers.W_REGISTER == 0)
				    	Pic16F84Registers.set_Flag("Z_FLAG");
				 break;	
			   	
			  case 0x3C00: //SUBLW
				  if((opcAndArguments[1] & 0b00001111) + ((~Pic16F84Registers.W_REGISTER+1) & 0b00001111)  > 0xF)
				    { Pic16F84Registers.set_Flag("DC_FLAG");}
				  else { Pic16F84Registers.reset_Flag("DC_FLAG");}
				  
				  if(Pic16F84Registers.W_REGISTER < opcAndArguments[1])
				  {Pic16F84Registers.set_Flag("C_FLAG");}
				  else { Pic16F84Registers.reset_Flag("C_FLAG");}
				  
				  Pic16F84Registers.W_REGISTER = (byte) (opcAndArguments[1] + (~Pic16F84Registers.W_REGISTER + 1)); 				  
				  if(Pic16F84Registers.W_REGISTER == 0)
				    	Pic16F84Registers.set_Flag("Z_FLAG");
				  else Pic16F84Registers.reset_Flag("Z_FLAG");
				 break;	
				 
			  case 0x3A00: //XORLW
				  Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER ^ opcAndArguments[1]); 				  
				  if(Pic16F84Registers.W_REGISTER == 0)
				    	Pic16F84Registers.set_Flag("Z_FLAG");
				  else Pic16F84Registers.reset_Flag("Z_FLAG");
				 break;	
			  
			  case 0x3E00: //ADDLW
				  if((opcAndArguments[1] & 0b00001111) + ((Pic16F84Registers.W_REGISTER) & 0b00001111)  > 0xF)
				    { Pic16F84Registers.set_Flag("DC_FLAG");}
				  else { Pic16F84Registers.reset_Flag("DC_FLAG");}
				  
				  if(Pic16F84Registers.W_REGISTER < opcAndArguments[1])
				  {Pic16F84Registers.set_Flag("C_FLAG");}
				  { Pic16F84Registers.reset_Flag("C_FLAG");}
				  
				  Pic16F84Registers.W_REGISTER = (byte) (opcAndArguments[1] + Pic16F84Registers.W_REGISTER); 				  
				  if(Pic16F84Registers.W_REGISTER == 0)
				    	Pic16F84Registers.set_Flag("Z_FLAG");
				  else Pic16F84Registers.reset_Flag("Z_FLAG");
				 break;	
			   	
			  
			 } 
	}
}
