
public class CommandInterpreter {

	
	/* TODO: Implement universal decoding of any of the 35 instructions */
	
	
	/* >>> DECODE <<< */
	
	//Takes a command and extracts OPC and arguments (Harvard-Architecture)
	static int[] decode(int command)
      {
	   //Integer-array holding OPC and arguments
	   int[] opcAndArguments = new int[3];
		
	   //Masks for OPC	
	   int maskOperationType =   			0b11000000000000;
	   int maskBitorientedType = 			0b00110000000000;
	   int maskLiteral_10_Type = 			0b00100000000000;
	   int maskLiteral_11_Type = 			0b00111100000000;
	   int maskLiteralOrByteorientedType = 	0b00111111111111;
	   int maskByteOPCPartType = 			0b00111100000000;
	   int maskByteDestinationPartType = 	0b00000010000000;
	     
       //Extracted Bits to determine instruction
	   int opcType = (command & maskOperationType) >> 12;
	   int bitorientedType = (command & maskBitorientedType) >> 10;
	   int literal_10_oriented_Type = (command & maskLiteral_10_Type) >> 11;
	   int literal_11_oriented_Type = (command & maskLiteral_11_Type) >> 8;
	   int literalOrByteorientedType = (command & maskLiteralOrByteorientedType) >> 0;  
	   int byteOPCType = (command & maskByteOPCPartType) >> 8;
	   int byteDestinationType = (command & maskByteDestinationPartType) >> 7;
	     
	  
	   switch(opcType)
	     {
	    	case 0:
	    	   opcAndArguments[0] = determineLiteralOrByteorientedType(literalOrByteorientedType, byteOPCType, byteDestinationType);
	           break;
	        case 1:
	        	opcAndArguments[0] = determineBitOrientedType(bitorientedType);
	    	   break;	    	   
	        case 2:
	        	opcAndArguments[0] = determineLiteral_10_OrientedType(literal_10_oriented_Type);
		       break;
	        case 3:
	        	opcAndArguments[0] = determineLiteral_11_OrientedType (literal_11_oriented_Type);
		       break;
	       }
		
	   return opcAndArguments;
	  }
	
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
	
   static int determineLiteral_10_OrientedType(int bits) {
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
   
   static int determineByteOPCType(int opcBits, int destinationBit)
   {
  	 switch(opcBits) 
	   {
		case 0:
		  if(destinationBit == 1)
		    {
			  return 0x0080;
		    }
		  else
		    {
			  return 0x0000;
		    }
		case 1:
		  if(destinationBit == 1)
		    {
			  return 0x0180;
		    }
		  else
		    {
			  return 0x0100;
		    }
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

