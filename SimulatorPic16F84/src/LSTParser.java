
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class LSTParser {
	
	public FileReader reader;
	public BufferedReader bufferedReader;
	public String[] fileText = new String[200];
	
	
	/* >>> CONSTRUCTOR <<< */

  public LSTParser(String fileName)
    {  
	 try
	   { 
		reader = new FileReader(fileName);
	    bufferedReader = new BufferedReader(reader);
	   }
	 catch(FileNotFoundException e) 
	   {
		 System.out.println("File not found!!");
		 System.out.println(e);
	   };
    }
  
  
  /* >>> METHODS <<< */
  
  //reading file as text
  public String[] readFile() throws IOException
    {
	  
	 if(reader != null && bufferedReader != null)
       {
		int i = 0;
		
		// reading lines into a 200 sized String-Array
		while((fileText[i] = bufferedReader.readLine()) != null)
			{i++;}
		
		//Cutting the output with the right size
		String[] rightSizedOutput = new String[i];
		for(int j = 0; j < i; ++j)
			rightSizedOutput[j] = fileText[j];
		
		 return rightSizedOutput; 
	   }

	 else
	   {
		 String[] error = new String[1];
		 error[0] = "Can´t read no file!!";
	 	return error; 
	   }
	  
    }
  
  //extracting and cutting machinecode from textfiles
  public String[] extractCodeline(String[] codeFile) 
     {
      String[] codeString = new String[codeFile.length];
      int codeLineCounter = 0;
    	
      for(int i = 0; i < codeString.length; i++)
        codeString[i] = "";
    	
      // reading codeFile line to line
        for(int i = 0; i < codeFile.length; i++)
          {
    	   // if the line starts with a space -> it doesn´t contain actual code
           if(codeFile[i].charAt(0) != ' ')	
             {
        	  codeLineCounter ++;
              for(int j =0; j < 9; j++)	
    		    codeString[i] += Character.toString(codeFile[i].charAt(j));		
             }
    	  }
    	
    	// cutting of those lines which doesnt contain any code
    	String[] rightSizedCodeString = new String[codeLineCounter];
    	int rightSizedCodeStringCounter = 0; // Amount of line of code that have machine code
    	
    	for(int i = 0; i< codeString.length; i++)
    	  {
    	   if(codeString[i] != "")
    	     {
    		  rightSizedCodeString[rightSizedCodeStringCounter] = codeString[i];
    		  rightSizedCodeStringCounter++;
    		 }
    	  } 
    	
    	return rightSizedCodeString;
      }
  
   //close file that has been opened
   public void closeFile()
     {
	  try 
	    {
		 bufferedReader.close();
	     System.out.println("File closed!");
	    } 
	  catch (IOException e) 
	    {
		 System.out.println("Can´t close File!!");
		 e.printStackTrace();
		}
	 }

}
