import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class LSTParser {
	
	
	/* >>> PARSER-VARIABLES <<< */
	
	public FileReader reader;	// data reader
	public BufferedReader bufferedReader;	// for reading each line of data 
	public String[] fileText = new String[500];	// save data from bufferedReader when it read // NOTE: can change to local variable

	
	/* >>> CONSTRUCTOR <<< */

    public LSTParser(String fileName)
      {  
	   try
	    { 
		  reader = new FileReader(fileName);
	      bufferedReader = new BufferedReader(reader);
	      SimulatorGUI.consoleOutput.append(">>File found and read!\n");
	     }
	   catch(FileNotFoundException e) 
	     {
		   SimulatorGUI.consoleOutput.append(">>ERROR: File not found!!\n");
		   SimulatorGUI.consoleOutput.append(e + "\n");
	     };
      }
  
  
    /* >>> METHODS <<< */
  
    // Reading file as text
    public String[] readFile() throws IOException
    {	 
	 SimulatorGUI.consoleOutput.append("Reading Textfile...\n");
	 if(reader != null && bufferedReader != null)	// When a file has been found
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

    // Extracting and cutting machine code from codeFile[] (a plain text)
    public String[] extractCodeline(String[] textFile) 
     {
	  SimulatorGUI.consoleOutput.append("Extracting Codelines...\n");
      String[] codeString = new String[textFile.length]; // first cutting of the text
      int codeLineCounter = 0; // counts the lines which contain actual code
    	
      //Initialize array codeString with ""
      for(int i = 0; i < codeString.length; i++)
        codeString[i] = "";
    	
      //Reading textFile line to line
      for(int i = 0; i < textFile.length; i++)
        {
    	 //If the line starts with a space -> it does not contain actual code
         if(textFile[i].charAt(0) != ' ')	
           {
            codeLineCounter ++;  
        	//Take first 9 char from each line and save to codeString
      	    codeString[i] = textFile[i].substring(0,9);
           }
        }
    	
    	//Cutting off those lines which do not contain any code
    	String[] rightSizedCodeString = new String[codeLineCounter];
    	int rightSizedCodeStringCounter = 0; // counter for rightSizedCodeString
    	
    	// Run through codeString
    	for(int i = 0; i < codeString.length; i++)
    	  {
    	   // When a String-line is not null then take this line from codeString to rightSizedCodeString
    	   if(codeString[i] != "")	 
    	     {
    		  rightSizedCodeString[rightSizedCodeStringCounter] = codeString[i];
    		  rightSizedCodeStringCounter++;
    		 }
    	  } 
    	return rightSizedCodeString;
      }
  
    //Extracts commands in an assembly line of machinecode
    public String[] extractCommandsFromStrings(String[] codeLines)
     {
	   SimulatorGUI.consoleOutput.append("Extracting Commands...\n");
	   //Index of Array = Adresses, Value = Commands
	   String[] programStorage = new String[codeLines.length];
	   
	   for(int i = 0; i < codeLines.length; i++)
	     programStorage[i] = codeLines[i].substring(5,9);
	      
	   return programStorage;
     }

    //Converts Sequence of String-Commands to Sequence of Interger-Commands
    public int[] convertStringCommandsToInteger(String[] stringCommands)
     {
	   int[] integerCommands = new int[stringCommands.length];
	   
	   for(int i = 0; i < stringCommands.length; i++)
		 integerCommands[i] =  Integer.decode("0x" + stringCommands[i]);

	  return integerCommands; 
     }
   
    // Takes a text-file as an argument and prints each line of it
    public void printFile(String[] textFile)
     {
		for(int i = 0; i < textFile.length; i++)
			SimulatorGUI.consoleOutput.append(textFile[i] + "\n");
     }
   
    // Closes file that has been opened
    public void closeFile()
     {
	  try 
	    {
		 bufferedReader.close();
	     SimulatorGUI.consoleOutput.append(">>File closed!\n");
	    } 
	  catch (IOException e) 
	    {
		 SimulatorGUI.consoleOutput.append(">>ERROR: Can�t close File!!\n");
		 e.printStackTrace();
		}
	 }

}
