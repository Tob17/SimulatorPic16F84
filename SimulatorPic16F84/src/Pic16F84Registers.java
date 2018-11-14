import java.util.HashMap;
import java.util.Map;

public class Pic16F84Registers {

	/* TODO: Adding every register available in the Pic16F84 */
	static Map <Integer, String> aMap = new HashMap<Integer, String>();
	static {
		aMap.put(0x00, "Indirect addr.");
		aMap.put(0x01, "TMR0");
		aMap.put(0x02, "PCL");
		aMap.put(0x03, "STATUS");
		aMap.put(0x04, "FSR");
		aMap.put(0x05, "PORTA");
		aMap.put(0x06, "PORTB");
		aMap.put(0x07, "");
		aMap.put(0x08, "EEDATA");
		aMap.put(0x09, "EEADR");
		aMap.put(0x0A, "PCLATH");
		aMap.put(0x0B, "INTCON");

		aMap.put(0x80, "Indirect addr.");
		aMap.put(0x81, "OPTION");
		aMap.put(0x82, "PCL");
		aMap.put(0x83, "STATUS");
		aMap.put(0x84, "FSR");
		aMap.put(0x85, "TRISA");
		aMap.put(0x86, "TRISB");
		aMap.put(0x87, "");
		aMap.put(0x88, "EECON1");
		aMap.put(0x89, "EECON2");
		aMap.put(0x8A, "PCLATH");
		aMap.put(0x8B, "INTCON");
	}
	
	/* >>> REGISTERS <<< */
	
	static byte W_REGISTER;
	// 8 Bit Working-Register connected with the ALU
	static short PC;
	// 13 Bit Program-Counter mapped onto the Program-Memory
	static byte PCL;
	// 8 Bit Program-Counter Lower-Byte
	static byte PCLATH;
	// 8 Bit Program-Counter Latch High
	static byte PSW; 
	// 8 Bit Status-Register storing information from the previous operation (bit0 = LSB, bit7 = MSB)
	// bit0 = C-Flag
	// bit1 = DC-Flag
	// bit2 = Z-Flag
	// bit3 = Power-Down bit
	// bit4 = Time-out bit
	// bit5 = RP0 Bank Select (direct adressing) (00 = Bank 0, 01 = Bank 1, 10 = Bank 3, 11 = bank 4)
	// bit6 = RP1 Bank Select (direct adressing)
	// bit7 = IRP Bank Select (indirect adressing) (0 = Bank0/Bank1, 1 = Bank2/Bank3)
	static byte STACKPOINTER;
	// 3 Bit Stackpointer
	static short INSTRUCTION_REGISTER;
	// 14 Bit Instruction Register;
	static byte FSR;
	// 8 Bit File-Select-Register
	
	
	
	/* >>> RAM, ROM  AND STACK <<< */
	
	static short[] PROGRAM_MEMORY = new short[8192];
	// 1K of 14-Bit Program-Memory
	
	static short[] STACK = new short[8];
	// 8 Level Stack with 13 Bit PC each
	
	static short[] RAM_BANK_0 = new short[128];
	// 128 1-Byte-dataregister addresses, while only 68 are physically implemented general purpose RAM-Registers (SRAM)
	// >>>Data (Ram) starts at: 0Ch
	// >>>Data (Ram) ends at: 4Fh
	
	// Adresses of Registers:
	// >>>00h: indirect adressing
	// >>>01h: Timer-0-Register
	// >>>02h: PCL-Register
	// >>>03h: Status-Register
	// >>>04h: File-Select-Register
	// >>>05h: Port-A-Register
	// >>>06h: PORT-B-Register
	// >>>07h: not used...
	// >>>08h: EEPROM-Data-Register
	// >>>09h: EEPROM-Adress-Register
	// >>>0Ah: PCLATH-Register
	// >>>0Bh: Interrupt-Control-Register
	
	static short[] RAM_BANK_1 = new short[128];
	// Follow-up to the adress-mapping of RAM_BANK_0 
	// RAM-Registers(68) of Bank 2 are mapped in Bank 0
	// >>>Data (Ram) starts at: 8Ch
	// >>>Data (Ram) ends at: CFh
	
	// Adresses of Registers:
	// >>>80h: indirect adressing
	// >>>81h: Option-Register
	// >>>82h: PCL-Register
	// >>>83h: Status-Register
	// >>>84h: File-Select-Register
	// >>>85h: TRIS-A-Register
	// >>>86h: TRIS-B-Register
	// >>>87h: not used...
	// >>>88h: EEPROM-Control-Register-1
	// >>>89h: EEPROM-Control-Register-2
	// >>>8Ah: PCLATH-Register
	// >>>8Bh: Interrupt-Control-Register
	

	
	/* >>> INIT REGISTERS <<< */
	
    static void initRegisters()
	  {
	   W_REGISTER = 0;
	   PC = 0;
	   PCL = 0;
	   PCLATH = 0;
	   load5BitPCLATHToPC();
	   loadPCLToPC();
	   PSW = 0;
       STACKPOINTER = 0;
	   INSTRUCTION_REGISTER = -1;
	   FSR = 0;
	  }
    
    
    
	/* >>> REGISTER-METHODS <<< */
    
    static void settingProgramPage(int pageNumber)
      {
       switch(pageNumber)
         {
          case 0:
    	      PCLATH = 0;
    	      load2BitPCLATHToPC();
          	  System.out.println("====================================================================");	  
        	  System.out.println("Loading program into page 0");	  
        	  System.out.println("====================================================================");	  
    	      break;
          case 1:
    	      PCLATH = 8;
    	      load2BitPCLATHToPC();
         	   System.out.println("====================================================================");	  
       	       System.out.println("Loading program into page 1");	  
       	       System.out.println("====================================================================");	  
    	      break;
          case 2:
    	      PCLATH = 16;
    	      load2BitPCLATHToPC();
         	  System.out.println("====================================================================");	  
       	      System.out.println("Loading program into page 2");	  
       	      System.out.println("====================================================================");	  
    	      break;
          case 3:
    	      PCLATH = 24;
    	      load2BitPCLATHToPC();
         	  System.out.println("====================================================================");	  
       	      System.out.println("Loading program into page 3");	  
       	      System.out.println("====================================================================");	  
    	      break;
          default:
       	      System.out.println("====================================================================");	  
    	      System.out.println("No valid page selected! Loading program into page 0...");	  
    	      System.out.println("====================================================================");	
    	      break;
         }
      }
    
    //Loads bit0 to bit4 of PCLATH into upper 5 Bits of PC
    static void load5BitPCLATHToPC()
      {
       //Storing bit0, bit1, bit2, bit3 and bit4 of PCLATH
       int temp = (PCLATH & 0b00011111);
       //Shifting 5 PCLATH-Bits to the upper 5 Bits of PC
       temp = temp << 8;
       //Cleaning upper 5 Bits of PC
       PC = (short)(PC & 0b0000011111111);
       //Adding PCLATH
       PC += temp;
      }
    
    //Loads bit3 and bit4 of PCLATH into upper 2 Bits of PC
    static void load2BitPCLATHToPC()
      {
       //Storing bit3 and bit4 of PCLATH
       int temp = (PCLATH & 0b00011000);
       //Shifting 2 PCLATH-Bits to the upper 2 Bits of PC
       temp = temp << 8;
       //Cleaning upper 2 Bits of PC
       PC = (short)(PC & 0b0011111111111);
       //Adding PCLATH
       PC += temp;
      }
    
    //Loads PCL-Byte into lower byte of PC
    static void loadPCLToPC()
      {
       //Cleaning lower 8 Bit of PC
       PC = (short)(PC & 0b1111100000000);
       //Adding PCL
       PC += PCL;
       load5BitPCLATHToPC();
      }
    
    static void loadPCToPCL()
      {PCL = (byte) (PC & 0b11111111);}
    
    //Increments PC and synchronizes PCL accordingly
    static void incrementPC()
      {
    	//Checking if PC is not overflowing uppon the next incrementation
       if(!((PC & 0b0011111111111) == 0b11111111111))
          {
    	   PC++;
    	   loadPCToPCL();
          }
       //If PC is overflowing uppon the next incrementation
       else
         {
    	  PC = (short)(PC & 0b1100000000000);
    	  loadPCToPCL();
 		  System.out.println("====================================================================");  
    	  System.out.println("Warning: PC overflowed and resetted to 0");
    	  System.out.println("Starting with first line again...");
		  System.out.println("====================================================================");  
         }    	
      }
    

    //Loads an 8-Bit ALU-Result into the lower byte of the PC plus adding 5 PCLATH Bits to it
    static void changePCL(byte aluResult)
      {
       //Loading PCL with aluResult
       PCL = aluResult;
       //Loading PC with PCL
       loadPCLToPC();
       //Loading 5-Bit PCLATH
       load5BitPCLATHToPC();
      }
    
    //Loads an 11-Bit goto-adress into the lower 11 Bits of the PC plus adding 2 PCLATH Bits to it
    static void computedGOTO(short gotoAdress)
      {
       //Loading PCL with lower 8 bits of gotoAdress
       PCL = (byte)(gotoAdress & 0b00011111111);
       //Cleaning lower 11 Bit of PC
       PC = (short)(PC & 0b1100000000000);
       //Adding goto-adress to PC
       PC += gotoAdress;
       //Loading 2-Bit PCLATH
       load2BitPCLATHToPC();
      }
    
    //Erasing content of the Instruction-Register e.g. as part of goto-instructions
    static void flushInstructionRegister()
      {INSTRUCTION_REGISTER = -1;}
	
    
    
	/* >>> FLAG-METHODS <<< */
	
	//Sets a flag identified by a name
	static void set_Flag(String flag)
	  {
	   switch(flag)
	     {
		  case "C_FLAG":
			  PSW = (byte) (PSW | 0b00000001);
			  break;
		  case "DC_FLAG":
			  PSW = (byte) (PSW | 0b00000010);
			  break;
		  case "Z_FLAG":
			  PSW = (byte) (PSW | 0b00000100);
			  break;
		 }
	  }
	
	//Resets a flag identified by a name
	static void reset_Flag(String flag)
	  {
	   switch(flag)
		 {
		  case "C_FLAG":
		  	  PSW = (byte) (PSW & 0b11111110);
		  	break;
		  case "DC_FLAG":
			  PSW = (byte) (PSW & 0b11111101);
			  break;
		  case "Z_FLAG":
			  PSW = (byte) (PSW & 0b11111011);
			  break;
		 }
	  }
	
	
	
	/* >>> PRINTING FLAGS & REGISTERS <<< */
	
	//Prints all flags
	static void printAllFlags()
	{System.out.println("*C-Flag = " + (PSW & (1)) + " *DC-Flag = " + (PSW >> 1 & (1))+ " *Z-Flag = " + (PSW >> 2 & (1)) );}
	
	//Prints PSW
	static void printPSW()
	{System.out.println("PSW = " + String.format("%2X", PSW) + "h");}
	
	//Prints Working-Register (W)
	static void printWRegister()
	{System.out.println("W-Register = " + String.format("%2X", W_REGISTER) + "h");}	
	
	//Prints PC
	static void printPC()
	{System.out.println("PC = " + String.format("%2X", PC) + "h");}	
	
	//Prints Stackpointer
	static void printStackpointer()
	{System.out.println("STACKPOINTER = " + String.format("%2X", STACKPOINTER) + "h");}	
	
	//Prints PCL
	static void printPCL()
	{System.out.println("PCL = " + String.format("%2X", PCL) + "h");}	
	
	//Prints PCLATH
	static void printPCLATH()
	{System.out.println("PCLATH = " + String.format("%2X", PCLATH) + "h");}
	
	//Prints FSR
	static void printFSR()
	{System.out.println("FSR = " + String.format("%2X", FSR) + "h");}	
	
	//Prints Instruction-Register
	static void printInstructionRegister()
	{System.out.println("Instruction-Register = " + String.format("%2X", INSTRUCTION_REGISTER) + "h");}
	
	//Prints all Registers
	static void printAllRegisters()
	  {
       printPSW();
       printWRegister();
       printPC();
       printPCL();
       printPCLATH();
       printStackpointer();
       printFSR();
       printInstructionRegister();
	  }	
	
	
	
	
	/* >>> PRINTING RAM, ROM AND STACK <<< */
	
	static void printProgramMemory()
	  {
	   System.out.println(">>> Content of the Program-Memory");
	   for(int i = 0; i < PROGRAM_MEMORY.length; i++)
		   System.out.println(i + ". " + String.format("%2X", PROGRAM_MEMORY[i]) + "h");
	  }
	
	static void printStack()
	  {
		System.out.println(">>> Content of the Stack");
	   for(int i = 0; i < STACK.length; i++)
		   System.out.println(i + ". " + String.format("%2X", STACK[i]) + "h");
	  }
}
