
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class LSTParser {
	
	/* >>> Parser-Variables <<< */
	public FileReader reader;	// data reader
	public BufferedReader bufferedReader;	// for reading each line of data 
	// NOTE: can change to local variable
	public String[] fileText = new String[200];	// save data from bufferedReader when it read


	
	
	/* >>> CONSTRUCTOR <<< */

	
  public LSTParser(String fileName)
    {  
	 try
	   { 
		reader = new FileReader(fileName);
	    bufferedReader = new BufferedReader(reader);
	    System.out.println(">>File found and read!");
	   }
	 catch(FileNotFoundException e) 
	   {
		 System.out.println(">>ERROR: File not found!!");
		 System.out.println(e);
	   };
    }
  
  
  
  /* >>> METHODS <<< */
  
  
  // Reading file as text
  public String[] readFile() throws IOException
    {	  
	 if(reader != null && bufferedReader != null)	// When there is a data and not EOF
       {
		int i = 0;	// Count how many line does this data have
		
		// Reading lines into a 200 sized String-Array
		while((fileText[i] = bufferedReader.readLine()) != null)
			{i++;}
		

		// Cutting the output with the right size
		// Remove the line with no data in fileText

		String[] rightSizedOutput = new String[i];
		for(int j = 0; j < i; ++j)
			rightSizedOutput[j] = fileText[j];
		
		 return rightSizedOutput; 
	   }
	 else
	   {
		 String[] error = new String[1];
		 error[0] = ">>ERROR: Can�t read no file!!";
	 	return error; 
	   }
	  
    }
  

  // Extracting and cutting machine code from codeFile[]
  public String[] extractCodeline(String[] codeFile) 
     {
	  // new array with same length from codeFile
	  // save first 9 char from codeFile to codeString
      String[] codeString = new String[codeFile.length];	
      int codeLineCounter = 0;
    	
      // Initialize array codeString with ""
      for(int i = 0; i < codeString.length; i++)
        codeString[i] = "";
    	
        // reading codeFile line to line
        for(int i = 0; i < codeFile.length; i++)
          {
    	   // if the line starts with a space -> it does not contain actual code
           if(codeFile[i].charAt(0) != ' ')	
             {
        	  codeLineCounter ++;
        	  
        	  // Take first 9 char from each line and save to codeString
        	  // NOTE: use subString maybe
        	  // codeString[i] = codeFile[i].subString(0,9);
              for(int j =0; j < 9; j++)	
    		    codeString[i] += Character.toString(codeFile[i].charAt(j));		
             }
    	  }
    	

    	// cutting of those lines which do not contain any code
    	String[] rightSizedCodeString = new String[codeLineCounter];
    	int rightSizedCodeStringCounter = 0; // counter for rightSizedCodeString
    	
    	// Run through codeString
    	for(int i = 0; i< codeString.length; i++)
    	  {
    	   // When the first char is not null then take this line from codeString to rightSizedCodeString
    	   if(codeString[i] != "")	 
    	     {
    		  rightSizedCodeString[rightSizedCodeStringCounter] = codeString[i];
    		  ++rightSizedCodeStringCounter;
    		 }
    	  } 
    	
    	return rightSizedCodeString;
      }
  
  
   // Cuts an extracted sequence of machinecode into address- und commandsections
   public String[][] extractCommandsAndAddresses(String[] machineCodeLines)
     {
	   /* TODO: Cutting each line of machinecode into adress- and commandsection and putting them side by side in an array */	   
	   return new String[0][0];
     }
  
  
   // Takes a text-file as an argument and prints each line of it
   public void printFile(String[] textFile)
     {
		for(int i = 0; i < textFile.length; i++)
			System.out.println(textFile[i]);
     }
  
   
   // Closes file that has been opened
   public void closeFile()
     {
	  try 
	    {
		 bufferedReader.close();
	     System.out.println(">>File closed!");
	    } 
	  catch (IOException e) 
	    {
		 System.out.println(">>ERROR: Can�t close File!!");
		 e.printStackTrace();
		}
	 }

}
