
public class CommandInterpreter {


	/* TODO: Extract arguments from commands! */


	/* >>> DECODE <<< */

	//Takes a command and extracts OPC and arguments (Harvard-Architecture)
	static int[] decode(int command)
	{
		//Integer-array holding OPC and arguments
		//opcAndArguments[0]: OPCODE
		//opcAndArguments[1]: Destination (Argument 1)
		//opcAndArguments[2]: FSR/Register Address (Argument 2).
		//opcAndArguments[2] == -1 When OPC has only 1 Argument
		int[] opcAndArguments = new int[3];

		//Masks for OPC	
		int maskOperationType =   				0b11000000000000;
		int maskBitorientedType = 				0b00110000000000;
		int maskLiteral_10_Type = 				0b00100000000000;
		int maskLiteral_11_Type = 				0b00111100000000;
		int maskLiteralOrByteorientedType = 	0b00111111111111;
		int maskByteOPCPartType = 				0b00111100000000;
		int maskByteDestinationPartType = 		0b00000010000000;

		//Extracted Bits for OPC determination
		int opcType = 					(command & maskOperationType) >> 12;
		int bitorientedType = 			(command & maskBitorientedType) >> 10;
		int literal_10_oriented_Type = 	(command & maskLiteral_10_Type) >> 11;
		int literal_11_oriented_Type = 	(command & maskLiteral_11_Type) >> 8;
		int literalOrByteorientedType = (command & maskLiteralOrByteorientedType) >> 0;  
		int byteOPCType = 				(command & maskByteOPCPartType) >> 8;
		int byteDestinationType = 		(command & maskByteDestinationPartType) >> 7;

		//Masks for argument 1 (Destination-Bit, 3-Bit-Adress or Literal)
		int maskDestinationBitArgument =     0b00000010000000;
		int mask_3_Bit_Adress =              0b00001110000000;
		int maskLiteral_8_Bit =              0b00000011111111;
		int maskLiteral_11_Bit =             0b00011111111111;

		//Masks for argument 2 (File-Register)
		int maskFileRegister =               0b00000001111111;

		//Determines which instruction is used and saves in in opcAndArguments[0]
		switch(opcType)
		{
		case 0:	//Byte-oriented file register operations
			opcAndArguments[0] = determineLiteralOrByteorientedType(literalOrByteorientedType, byteOPCType, byteDestinationType);
			break;
		case 1: //Bit-oriented file register operations
			opcAndArguments[0] = determineBitOrientedType(bitorientedType);
			break;	    	   
		case 2:	//Literal and control operations
			opcAndArguments[0] = determineLiteral_10_OrientedType(literal_10_oriented_Type);
			break;
		case 3: //Literal and control operations
			opcAndArguments[0] = determineLiteral_11_OrientedType (literal_11_oriented_Type);
			break;
		}

		//Afterwards extracts arguments from said instruction and saves them into opcAndArguments[1] and opcAndArguments[2]
		switch(opcAndArguments[0])
		{
		//Byte-oriented file register operations
		case 0x0700: //ADDWF
		case 0x0500: //ANDWF
		case 0x0180: //CLRF
		case 0x0100: //CLRW
		case 0x0900: //COMF
		case 0x0300: //DECF
		case 0x0B00: //DECFSZ
		case 0x0A00: //INCF
		case 0x0F00: //INCFSZ
		case 0x0400: //IORWF
		case 0x0800: //MOVF
		case 0x0080: //MOVWF
		case 0x0000: //NOP
		case 0x0D00: //RLF
		case 0x0C00: //RRF
		case 0x0200: //SUBWF
		case 0x0E00: //SWAPF
		case 0x0600: //XORWF
			opcAndArguments[1] = (command & maskDestinationBitArgument) >> 7;
			opcAndArguments[2] = (command & maskFileRegister);
			break;
			
		//Bit-oriented file register operations
		case 0x1000://BCF
		case 0x1400: //BSF
		case 0x1800: //BTFSC
		case 0x1C00: //BTFSS
			opcAndArguments[1] = (command & mask_3_Bit_Adress) >> 7;
			opcAndArguments[2] = (command & maskFileRegister);
			break;

		//Literal and control operations
		case 0x3E00: //ADDLW
		case 0x3900: //ANDLW
		case 0x0064: //CLRWDT
		case 0x3800: //IORLW
		case 0x3000: //MOVLW
		case 0x0009: //RETFIE
		case 0x3400: //RETLW
		case 0x0008: //RETURN
		case 0x0063: //SLEEP
		case 0x3C00: //SUBLW
		case 0x3A00: //XORLW
			opcAndArguments[1] = (command & maskLiteral_8_Bit);
			opcAndArguments[2] = -1;
			break;	

		//Literal and control operations
		case 0x2800: //GOTO
		case 0x2000: //CALL
			opcAndArguments[1] = (command & maskLiteral_11_Bit);
			opcAndArguments[2] = -1;
			break;

		case -1: //Undefined
			opcAndArguments[1] = -1;
			opcAndArguments[2] = -1;
			break;
		}

		return opcAndArguments;
	}



	/* >>> INSTRUCTION DETERMINATION <<< */

	//Determines which of the 10-Bit-Oriented instructions is used
	static int determineBitOrientedType(int bit)
	{
		switch(bit)
		{
		case 0:
			return 0x1000;
		case 1:
			return 0x1400;
		case 2:
			return 0x1800;
		case 3:
			return 0x1C00;
		default:
			return -1;
		}
	}


	//Determines which of the 01-Literal instructions  is used
	static int determineLiteral_10_OrientedType(int bits) 
	{
		switch(bits)
		{
		case 0:
			return 0x2000;
		case 1:
			return 0x2800;
		default:
			return -1;
		}
	}

	//Determines which of the 11-Literal instructions  is used
	static int determineLiteral_11_OrientedType(int bits)
	{
		switch (bits)
		{
		case 0: case 1: case 2: case 3:
			return 0x3000;
		case 4: case 5: case 6: case 7:
			return 0x3400;
		case 8:
			return 0x3800;
		case 9:
			return 0x3900;
		case 10: 
			return 0x3A00;
		case 12: case 13:
			return 0x3C00;
		case 14: case 15:
			return 0x3E00;
		default:
			return -1;
		}
	} 

	//Determines whether a 00-Literal instruction or a 00-Byte instruction is used
	static int determineLiteralOrByteorientedType(int bits, int opcBits, int destinationBit)
	{
		switch(bits) 
		{
		case 0x0064:
			return 0x0064;
		case 0x0009:
			return 0x0009;
		case 0x0008:
			return 0x0008;
		case 0x0063:
			return 0x0063;
		default:
			return determineByteOPCType(opcBits, destinationBit ) ;
		}
	}

	//Determines which of the 00-Byte instructions is used
	static int determineByteOPCType(int opcBits, int destinationBit)
	{
		switch(opcBits) 
		{
		case 0:
			if(destinationBit == 1)
			{return 0x0080;}
			else
			{return 0x0000;}
		case 1:
			if(destinationBit == 1)
			{return 0x0180;}
			else
			{return 0x0100;}
		case 2:
			return 0x0200;
		case 3:
			return 0x0300;
		case 4:
			return 0x0400;
		case 5:
			return 0x0500;
		case 6:
			return 0x0600;
		case 7:
			return 0x0700;
		case 8:
			return 0x0800;
		case 9:
			return 0x0900;
		case 10:
			return 0x0A00;
		case 11:
			return 0x0B00;
		case 12:
			return 0x0C00;
		case 13:
			return 0x0D00;
		case 14:
			return 0x0E00;
		case 15:
			return 0x0F00;
		default:
			return -1;		
		}
	}


}

