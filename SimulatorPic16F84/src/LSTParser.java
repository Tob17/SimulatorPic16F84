
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
  
	public void closeFile()
	  {
	   try 
	     {bufferedReader.close();
	     System.out.println(reader + "File closed!");} 
	   catch (IOException e) 
	     {
		  System.out.println("Can´t close File!!");
		  e.printStackTrace();
		 }
	  }
	

}
