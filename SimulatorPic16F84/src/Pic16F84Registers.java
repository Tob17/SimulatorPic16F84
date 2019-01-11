public class Pic16F84Registers {

	
	/* TODO: Adding Option-Register and operations */
	/* TODO: Adding Timer0 Register and functionality */
	
	/* >>> REGISTERS <<< */

	static byte W_REGISTER;
	// 8 Bit Working-Register connected with the ALU
	static short PC;
	// 13 Bit Program-Counter mapped onto the Program-Memory
	static byte PCL;
	// 8 Bit Program-Counter Lower-Byte
	static byte PCLATH;
	// 8 Bit Program-Counter Latch High
	static byte PSW; //STATUS REGISTER (ADDRESS 03h, 83h)
	// 8 Bit Status-Register storing information from the previous operation (bit0 = LSB, bit7 = MSB)
	// bit0 = C-Flag
	// bit1 = DC-Flag
	// bit2 = Z-Flag
	// bit3 = Power-Down bit1
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
	// (bit0 = LSB, bit7 = MSB)
	// bit 7 (+IRP) = bank select
	// bit 0-6 = location select
	static byte PORT_A_REGISTER;
	// 5 Bit Port Register
	static byte TRIS_A_REGISTER;
	// 5 Bit Direction-Register of Port A
	static byte PORT_A_PINS;
	// 5 Bit value for every output-pin on Port A
	static byte PORT_B_REGISTER;
	// 8 Bit Port Register
	static byte TRIS_B_REGISTER;
	// 8 Bit Direction-Register of Port B
	static byte PORT_B_PINS;
	// 8 Bit value for every output-pin on Port B


	
	/* >>> RAM, ROM  AND STACK <<< */

	static short[] PROGRAM_MEMORY = new short[8192];
	// 1K of 14-Bit Program-Memory

	static short[] STACK = new short[8];
	// 8 Level Stack with 13 Bit PC each

	static byte[] RAM_BANK_0 = new byte[128];
	// 128 1-byte-file-Registers in bank 0
	// 116 of which are general purpose registers
	// 12 of which are special function registers

	static byte[] RAM_BANK_1 = new byte[128];
	// 128 1-byte-file-Registers in bank 1
	// 116 of which are general purpose registers
	// 12 of which are special function registers


	

	/* >>> INIT REGISTERS <<< */

	//Initializes each register with 0
	static void initRegisters()
	{
		W_REGISTER = 0;
		PC = 0;
		PCL = 0;
		PCLATH = 0;
		PSW = 0;
		STACKPOINTER = -1;
		INSTRUCTION_REGISTER = -1;
		FSR = 0;
		PORT_A_REGISTER = 0;
		PORT_B_REGISTER = 0;
		TRIS_A_REGISTER = 0;
		TRIS_B_REGISTER = 0;
		PORT_A_PINS = 0;
		PORT_B_PINS = 0;
	}

	
	/* >>> INIT RAM, ROM AND STACK <<< */
	
	//Initializes every data register with 0
	static void initMemory()
	{
		for(int i = 0; i < STACK.length; i++)
			STACK[i] = 0;
		for(int i = 0; i < RAM_BANK_0.length; i++)
			RAM_BANK_0[i] = 0;
		for(int i = 0; i < RAM_BANK_1.length; i++)
			RAM_BANK_1[i] = 0;
	}
	
	
	
	/* >>> RAM, ROM  AND STACK - Methods <<< */
	

	//Accesses Data-Registers either in Bank 0 or Bank 1
	static int readFileRegisterValue(int fileRegisterAdress, int bankSelection)
	  {
		//Special Function Registers...
		if((fileRegisterAdress % 128) < 0x0C && (fileRegisterAdress % 128) >= 0x00)
		  {   
			//...in Bank 0
			if(bankSelection == 0)
				switch(fileRegisterAdress % 128)
				{
				case 0x00: //Indirect adresse read as 0
					return  0;
				case 0x01: // TMR0   TODO: IMPLEMENT!!
					return -1;
				case 0x02: // PCL
					return PCL;
				case 0x03: // STATUS
					return PSW;
				case 0x04: // FSR
					return FSR;
				case 0x05: // PORTA (reads the pins instead of the port-latch)
					return PORT_A_PINS;
				case 0x06: // PORTB (reads the pins instead of the port-latch))
					return PORT_B_PINS;
				case 0x07: //UNDEFINED
					return 0;
				case 0x08: //EEDATA  TODO: IMPLEMENT!!
					return -1;
				case 0x09: //EEADR   TODO: IMPLEMENT!!
					return -1;
				case 0x0A: //PCLATH
					return PCLATH;
				case 0x0B: //INTCON  TODO: IMPLEMENT!!
					return -1;
				default:
					return -1;
				}       
			//... in Bank 1
			if(bankSelection == 1)
				switch(fileRegisterAdress % 128)
				{
				case 0x00: //Indirect adresse read as 0
					return  0;
				case 0x01: // OPTION   TODO: IMPLEMENT!!
					return -1;
				case 0x02: // PCL
					return PCL;
				case 0x03: // STATUS
					return PSW;
				case 0x04: // FSR
					return FSR;
				case 0x05: // TRISA
					return TRIS_A_REGISTER;
				case 0x06: // TRISB
					return TRIS_B_REGISTER;
				case 0x07: //UNDEFINED
					return -1;
				case 0x08: //EECON1  TODO: IMPLEMENT!!
					return -1;
				case 0x09: //EECON2 read as 0
					return  0;
				case 0x0A: //PCLATH
					return PCLATH;
				case 0x0B: //INTCON  TODO: IMPLEMENT!!
					return -1;
				default:
					return -1;
				}
		} 
		//General Purpose Registers...
		else if (((fileRegisterAdress % 128) >= 0x0C && (fileRegisterAdress % 128) <= 0x4F) || ((fileRegisterAdress % 128) >= 0x8C && (fileRegisterAdress % 128) <= 0xCF))
		  {
			//...always accessed from Bank 0 (Bank 1 is identical to Bank 0) 
				return RAM_BANK_0[fileRegisterAdress % 128];
		  }
		else
			SimulatorGUI.consoleOutput.append(">>>FATAL ERRROR: Tried to access non implemented RAM!\n");
		
		//If no register has been addressed
		return -1;   	
	}
	
	//Accesses Data-Registers either in Bank 0 or Bank 1
	static void writeFileRegisterValue(int fileRegisterAdress, int bankSelection, int value)
	  {
		//Special Function Registers...
		if((fileRegisterAdress % 128) < 0x0C && (fileRegisterAdress % 128) >= 0x00)
		  {
			//...in Bank 0
			if(bankSelection == 0)
				switch(fileRegisterAdress % 128)
				{
				case 0x00: //Indirect adresse read as 0, never overwritten
					break;
				case 0x01: // TMR0   TODO: IMPLEMENT!!
					break;
				case 0x02: // PCL
					PCL = (byte) value;
					loadPCLToPC();
					break;
				case 0x03: // STATUS
					PSW = (byte) value;
					break;
				case 0x04: // FSR
					FSR = (byte) value;
					break;
				case 0x05: // PORTA
					PORT_A_REGISTER = (byte)value;
					writePortsToPins();
					break;
				case 0x06: // PORTB
					PORT_B_REGISTER = (byte)value;
					writePortsToPins();
					break;
				case 0x07: //undefined
					break;
				case 0x08: //EEDATA  TODO: IMPLEMENT!!
					break;
				case 0x09: //EEADR   TODO: IMPLEMENT!!
					break;
				case 0x0A: //PCLATH
					PCLATH = (byte) value;
				case 0x0B: //INTCON  TODO: IMPLEMENT!!
					break;
				}
			//... in Bank 1
			if(bankSelection == 1)
				switch(fileRegisterAdress % 128)
				{
				case 0x00: //Indirect adresse read as 0
					break;
				case 0x01: // OPTION   TODO: IMPLEMENT!!
					break;
				case 0x02: // PCL
					PCL = (byte) value;
					break;
				case 0x03: // STATUS
					PSW = (byte) value;
					break;
				case 0x04: // FSR
					FSR = (byte) value;
					break;
				case 0x05: // TRISA
					TRIS_A_REGISTER = (byte)value;
					writePortsToPins();
					break;
				case 0x06: // TRISB
					TRIS_B_REGISTER = (byte)value;
					writePortsToPins();
					break;
				case 0x07: //undefined
					break;
				case 0x08: //EECON1  TODO: IMPLEMENT!!
					break;
				case 0x09: //EECON2 read as 0
					break;
				case 0x0A: //PCLATH
					PCLATH = (byte) value;
				case 0x0B: //INTCON  TODO: IMPLEMENT!!
					break;
				}
		}
		//General Purpose Registers...
		else if (((fileRegisterAdress % 128) >= 0x0C && (fileRegisterAdress % 128) <= 0x4F)  || ((fileRegisterAdress % 128) >= 0x8C && (fileRegisterAdress % 128) <= 0xCF))
		  {
			//...are allways written to in Bank 0 and Bank 1 simultanously
				RAM_BANK_0[fileRegisterAdress % 128] = (byte)value;    
				RAM_BANK_1[fileRegisterAdress % 128] = (byte)value; 
		  }
		else
		  SimulatorGUI.consoleOutput.append(">>>FATAL ERRROR: Tried to access non implemented RAM!\n");
	}
	
	//Increments the stackpointer and sets it to 0 if it exceeds the 8-Level-Stack (circular puffer)
	static void incrementStackPointer()
	{
		STACKPOINTER++;
		
		if(STACKPOINTER > 7)
			STACKPOINTER = 0;
	}
	
	//Decrements the stackpointer and sets it to 7 if it falls below 0 (circular puffer)
	static void decrementStackPointer()
	{
		STACKPOINTER--;
		
		if(STACKPOINTER < 0)
			STACKPOINTER = 7;
	}
	
	//Pushes a value onto the stack
	static void push(int value)
	{
		//Checks if stackpointer is not pointing to a value on the stack yet
		if(STACKPOINTER == -1)
		{
			STACKPOINTER = 0;
			STACK[STACKPOINTER] = (short)value;
		}
		else
		{
			incrementStackPointer();
			STACK[STACKPOINTER] = (short)value;
		}
	}
	
	//Pops a value from the stack
	static short pop()
	{
		short returnValue = STACK[STACKPOINTER];
		decrementStackPointer();
		return returnValue;
	}
	
	
	
	
	/* >>> Bit - Operation - Methods <<< */
	
	//Get the lower 7 Bits of FSR if indirekt adressing is used
	static int getIndirectAdressFromFSR()
	{
	    return (FSR & 0b01111111);
	}
	
	//Get Bankselection-Bit from FSR
	static int getBankBitFromFSR()
	{
	    return ((FSR & 0b10000000) >> 7);
	}
	
	//Get the RP0-Bank-Selection-Bit from PSW 
	static int getRP0Bit()
	{
	    return ((PSW & 0b00100000) >> 5);
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
			SimulatorGUI.consoleOutput.append("====================================================================\n");  
			SimulatorGUI.consoleOutput.append("Warning: PC overflowed and resetted to 0\n");
			SimulatorGUI.consoleOutput.append("Starting with first line again...\n");
			SimulatorGUI.consoleOutput.append("====================================================================\n");  
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
	
	
	
	/* >>>  I/O-OPERATIONS <<< */
	
	
	//Updates any PORT-Bit (if pin is declared as input) or Pin (if pin is declared as output)
	static void writePortsToPins()
	{
		for(int i = 0; i < 5; i ++)
		  {int portBitValue = (PORT_A_REGISTER & (1 << i)) >> i;
			writePortBitToPin(portBitValue, i, 'A');}
		for(int i = 0; i < 8; i ++)
		  {int portBitValue = (PORT_B_REGISTER & (1 << i)) >> i;
			writePortBitToPin(portBitValue, i, 'B');}
		
		 SimulatorGUI.consoleOutput.append("============================================================\n");
		 printPORTS();
		 printTRIS();
		 printPINS();
		 SimulatorGUI.consoleOutput.append("============================================================\n");
	}
	
	
	//Sets a selected pin of either PORT-A or PORT-B
	static void setPin(int pinChanged, char portName)
	  {
	   if(portName == 'A' || portName=='a')
		  PORT_A_PINS |= 1 << pinChanged;
	   if(portName == 'B' || portName=='b')
		 PORT_B_PINS |= 1 << pinChanged;
	   
	   writePinToPortBit(1, pinChanged, portName);
	  }
	
	
	//Resets a selected pin of either PORT-A or PORT-B
	static void resetPin(int pinChanged, char portName)
	  {
	   if(portName == 'A' || portName=='a')
		 PORT_A_PINS &= ~(1 << pinChanged);
	   if(portName == 'B' || portName=='b')
		 PORT_B_PINS &= ~(1 << pinChanged);
	   
	   writePinToPortBit(0, pinChanged, portName);
	  }
	
	//If a Pin is changed, we write its value into the corresponding PORT-Bit, if the Pin is declared as an input
	//pinChanged: 000 = Pin0, 001 = Pin1,...
	//pinValue: pin checked or not
	//portName: PortA or PortB?	
	static void writePinToPortBit(int pinValue, int pinChanged, char portName)
	{
	 if(portName == 'A' || portName=='a')
	   {
		 //Check if TRIS-A-Pin has a 1 at selected pin -> Input-Pin
		 if(((TRIS_A_REGISTER & (1 << pinChanged)) >> pinChanged) == 1)
           {
			//Write value 1 to PORT-REGISTER-Bit
			if(pinValue == 1)
			   PORT_A_REGISTER |= 1 << pinChanged;
			//Write value 0 to PORT-REGISTER-Bit
			else
			   PORT_A_REGISTER &= ~(1 << pinChanged); 	
           }
		 //If not, then TRIS-A-Bit has been set to 0 and declared as an Output-Pin, rewriting the value set on the pin with the PORT-A-Bit
		 else
		   {
		    int portBitValue = (PORT_A_REGISTER & (1 << pinChanged)) >> pinChanged;
		    writePortBitToPin(portBitValue, pinChanged, portName);
		   }
	   }
	 else if(portName == 'B' || portName=='b')
	   {
		 //Check if TRIS-B-Pin has a 1 at selected pin -> Input-Pin
		 if(((TRIS_B_REGISTER & (1 << pinChanged)) >> pinChanged) == 1)
           {
			//Write value 1 to Port-Pin
			if(pinValue == 1)
				PORT_B_REGISTER |= 1 << pinChanged;
			//Write value 0 to Port-Pin
			else
				PORT_B_REGISTER &= ~(1 << pinChanged); 			
           }
		 //If not, then TRIS-B-Bit has been set to 0 and declared as an Output-Pin, rewriting the value set on the pin with the PORT-B-Bit
		 else
		   {
		    int portBitValue = (PORT_B_REGISTER & (1 << pinChanged)) >> pinChanged;
		    writePortBitToPin(portBitValue, pinChanged, portName);
		   }
	   }
	 else
		 SimulatorGUI.consoleOutput.append("ERROR: Wrong Port Selected!\n");
	}
	
	
	//If a Port-Bit is changed, we write its value into the corresponding Pin, if the Pin is declared as an output
	//pinChanged: 000 = Pin0, 001 = Pin1,...
	//pinValue: pin checked or not
	//portName: PortA or PortB?	
	static void writePortBitToPin(int pinValue, int pinChanged, char portName)
	{
	 if(portName == 'A' || portName=='a')
	   {
	    //Check if TRIS-A-Pin has a 0 at selected pin -> Output-Pin
		if(((TRIS_A_REGISTER & (1 << pinChanged)) >> pinChanged) == 0)
          {
		   //Write value 1 to PORT-REGISTER-Bit
		   if(pinValue == 1)
		     PORT_A_PINS |= 1 << pinChanged;
		   //Write value 0 to PORT-REGISTER-Bit
		   else
			 PORT_A_PINS &= ~(1 << pinChanged); 	
          }
		//If not, then TRIS-A-Bit has been set to 1 and declared as an Input-Pin, rewriting the value set on the Port-Bit with the value on the corresponding pin
		else
		  {
		   int pinBitValue = (PORT_A_PINS & (1 << pinChanged)) >> pinChanged;
		   writePinToPortBit(pinBitValue, pinChanged, portName);
		  }
	   }
	 else if(portName == 'B' || portName=='b')
	   {
		//Check if TRIS-B-Pin has a 0 at selected pin -> Output-Pin
		if(((TRIS_B_REGISTER & (1 << pinChanged)) >> pinChanged) == 0)
	      {
		   //Write value 1 to PORT-REGISTER-Bit
		   if(pinValue == 1)
			 PORT_B_PINS |= 1 << pinChanged;
		   //Write value 0 to PORT-REGISTER-Bit
		   else
			 PORT_B_PINS &= ~(1 << pinChanged); 	
	      }
		//If not, then TRIS-A-Bit has been set to 1 and declared as an Input-Pin, rewriting the value set on the Port-Bit with the value on the corresponding pin
		else
		  {
		   int pinBitValue = (PORT_B_PINS & (1 << pinChanged)) >> pinChanged;
		   writePinToPortBit(pinBitValue, pinChanged, portName);
		  }
	   }
	 else
		 SimulatorGUI.consoleOutput.append("ERROR: Wrong Port Selected!\n");
	}



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
		case "RP0":
			PSW = (byte) (PSW | 0b00100000);
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
		case "RP0":
			PSW = (byte) (PSW & 0b11011111);
			break;
		}
	}
	
	

	
	/* >>> PRINTING FLAGS & REGISTERS <<< */

	//Prints all flags
	static void printAllFlags()
	{SimulatorGUI.consoleOutput.append("*C-Flag = " + (PSW & (1)) + " *DC-Flag = " + (PSW >> 1 & (1))+ " *Z-Flag = " + (PSW >> 2 & (1)) + "\n");}

	//Prints PSW
	static void printPSW()
	{SimulatorGUI.consoleOutput.append("PSW = " + String.format("%2X", PSW) + "h\n");}

	//Prints Working-Register (W)
	static void printWRegister()
	{SimulatorGUI.consoleOutput.append("W-Register = " + String.format("%2X", W_REGISTER) + "h\n");}	

	//Prints PC
	static void printPC()
	{SimulatorGUI.consoleOutput.append("PC = " + String.format("%2X", PC) + "h\n");}	

	//Prints Stackpointer
	static void printStackpointer()
	{SimulatorGUI.consoleOutput.append("STACKPOINTER = " + String.format("%2X", STACKPOINTER) + "h\n");}	

	//Prints PCL
	static void printPCL()
	{SimulatorGUI.consoleOutput.append("PCL = " + String.format("%2X", PCL) + "h\n");}	

	//Prints PCLATH
	static void printPCLATH()
	{SimulatorGUI.consoleOutput.append("PCLATH = " + String.format("%2X", PCLATH) + "h\n");}

	//Prints FSR
	static void printFSR()
	{SimulatorGUI.consoleOutput.append("FSR = " + String.format("%2X", FSR) + "h\n");}	

	//Prints Instruction-Register
	static void printInstructionRegister()
	{SimulatorGUI.consoleOutput.append("Instruction-Register = " + String.format("%2X", INSTRUCTION_REGISTER) + "h\n");}
	
	//Prints both PORTS
	static void printPORTS()
	{SimulatorGUI.consoleOutput.append("PORTA-Register = " + String.format("%2X", PORT_A_REGISTER) + "h\n");
	 SimulatorGUI.consoleOutput.append("PORTB-Register = " + String.format("%2X", PORT_B_REGISTER) + "h\n");}
	
	//Prints both TRIS-Registers
	static void printTRIS()
	{SimulatorGUI.consoleOutput.append("TRISA-Register = " + String.format("%2X", TRIS_A_REGISTER) + "h\n");
	 SimulatorGUI.consoleOutput.append("TRISB-Register = " + String.format("%2X", TRIS_B_REGISTER) + "h\n");}
	
	//Prints both PORT-OUTPUT-PINS
	static void printPINS()
	{SimulatorGUI.consoleOutput.append("PORTA-PINS = " + String.format("%2X", PORT_A_PINS) + "h\n");
	 SimulatorGUI.consoleOutput.append("PORTB-PINS = " + String.format("%2X", PORT_B_PINS) + "h\n");}
	
	static void printAllIORegisters()
	  {
	   SimulatorGUI.consoleOutput.append("====================================================================\n");
	   SimulatorGUI.consoleOutput.append("======================= Current I/O State ==========================\n");	
	   SimulatorGUI.consoleOutput.append("====================================================================\n");	
       printPORTS();
	   printTRIS();
	   printPINS();
	   SimulatorGUI.consoleOutput.append("====================================================================\n");	
	  }

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
		printPORTS();
		printTRIS();
		printPINS();
		SimulatorGUI.consoleOutput.append("====================================================================\n"); 
	}	




	/* >>> PRINTING RAM, ROM AND STACK <<< */

	//Prints program memory
	static void printProgramMemory()
	{
		SimulatorGUI.consoleOutput.append(">>> Content of the Program-Memory\n");
		SimulatorGUI.consoleOutput.append("====================================================================\n"); 
		for(int i = 0; i < PROGRAM_MEMORY.length; i++)
		{
		 SimulatorGUI.consoleOutput.append(i + ". " + String.format("%2X", PROGRAM_MEMORY[i]) + "h" + "      \n");
		 if((i % 10) == 0)
			 SimulatorGUI.consoleOutput.append("\n");
		}
		SimulatorGUI.consoleOutput.append("\n");
	}

	//prints data memory
	static void printDataMemory()
	{	
		SimulatorGUI.consoleOutput.append(">>> Content of the Data-Memory in Bank 0 and Bank 1\n");
		SimulatorGUI.consoleOutput.append("====================================================================\n"); 
		
		String printedLine = "";
		for(int i = 12; i < 80; i++)
		  {
		    
		   printedLine += i + ". " + String.format("%2X", RAM_BANK_0[i]) + "h" + "      ";
		   if(((i - 11) % 6) == 0)
		     {
			  SimulatorGUI.consoleOutput.append(printedLine + "\n");
			  printedLine = "";
		     }
		  }	
		SimulatorGUI.consoleOutput.append("\n");
	}

	//prints stack
	static void printStack()
	{
		SimulatorGUI.consoleOutput.append(">>> Content of the Stack\n");
		for(int i = 0; i < STACK.length; i++)
			SimulatorGUI.consoleOutput.append(i + ". " + String.format("%2X", STACK[i]) + "h\n");
	}
}
