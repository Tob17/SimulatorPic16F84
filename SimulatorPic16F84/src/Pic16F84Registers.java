
public class Pic16F84Registers {

	/* TODO: Adding every register available in the Pic16F84 */
	
	
	/* >>> REGISTERS <<< */
	
	static byte W_REGISTER = 0;
	static short PC = 0;
	static byte PSW = 0; 
	// LSB Sequence
	// bit0 = C-Flag
	// bit1 = DC-Flag
	// bit2 = Z-Flag
	
	
	/* >>> FLAG-SETTERS <<< */
	
	//Sets a flag identified by a name
	static void set_Flag(String flag)
	  {
	   switch(flag)
	     {
		  case "C_FLAG":
			  PSW =(byte) (PSW | 0b00000001);
			  break;
		  case "DC_FLAG":
			  PSW =(byte) (PSW | 0b00000010);
			  break;
		  case "Z_FLAG":
			  PSW =(byte) (PSW | 0b00000100);
			  break;
		 }
	  }
	
	//Resets a flag identified by a name
	static void reset_Flag(String flag)
	  {
	   switch(flag)
		 {
		  case "C_FLAG":
		  	  PSW =(byte) (PSW & 0b11111110);
		  	break;
		  case "DC_FLAG":
			  PSW =(byte) (PSW & 0b11111101);
			  break;
		  case "Z_FLAG":
			  PSW =(byte) (PSW & 0b11111011);
			  break;
		 }
	  }
	
	
	
	/* >>> PRINTING FLAGS & REGISTERS <<< */
	
	//Prints all flags
	static void printAllFlags()
	{System.out.println("C-Flag = " + (PSW & (1)) + " DC-Flag = " + (PSW >> 1 & (1))+ " Z-Flag = " + (PSW >> 2 & (1)) );}
	
	//Prints the psw
	static void printPSW()
	{System.out.println("PSW = " + Integer.toBinaryString(PSW));}
	
	//Prints the W-Register
	static void printWRegister()
	{System.out.println("W-Register = " + String.format("0x%2X", Pic16F84Registers.W_REGISTER) + "H");}	
	
	
}
