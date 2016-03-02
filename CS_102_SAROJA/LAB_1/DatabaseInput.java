/**
*Author: Jacob S. Howarth
*Assignment A1
*DatabaseInput.java
*
*This class contains methods for loading the input file and converting the input to a database.
*
*/

import java.io.*;
import java.util.*;

public class DatabaseInput {
	
	private final int[] INPUT_ERROR_CODE = {0, 1, 2};
	private String fileName;
	private File inputFile;
	
	/**
	*
	*This is the class zero-arg constructor that initializes class instance variables
	*
	*/	
	public DatabaseInput() {
		fileName = "";
		inputFile = null;
	}
	
	/**
	*
	*Creates an input database instance that allows the a file path to be passed containing the input for the database
	*
	* @param fileName   The path of the input file
	* 
	*/
	public DatabaseInput(String fileName) {
		this.fileName = fileName;
		createDatabaseFile();
	}
	
	/**
	*
	*Mutator for setting the file path for a specific database input instance
	*
	* @param fileName   sets the fileName of the database input instance
	* 
	*/  
	public void setDatabaseFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	*
	*Accessor for getting the input file path for a specific database input instance
	*
	* @return   the input file path
	*
	*/
	public String getDatabaseFileName() {
		return fileName;
	}
	
	/**
	*
	*Creates a file object based off the input file path to process the input into a database object
	*
	*/
	public void createDatabaseFile() {
		inputFile = new File(this.fileName);
		
		if (!inputFile.exists())
			error(INPUT_ERROR_CODE[2]);	
	}
	
	/**
	*
	*Reads the information within the input file and creates a database for it.
	* 
	* @return   returns a database object that holds the data in the file.
	*
	*/
	public Database readDatabaseFile() {
		
		String[] inputLine;
		Scanner readFile;
		Database database = new Database();
		
		try { // try catch block to catch file I/O exceptions
			readFile = new Scanner(inputFile);
	
			while (readFile.hasNext()) {
				inputLine = (readFile.nextLine()).split(":"); // split the entries in the file by the delimiter ":"
				
				if (inputLine.length != 3) { // if the length of the split string array is not 3, then throw error because an entry has missining or extra information
					error(INPUT_ERROR_CODE[0]); // print the corresponding error code message in error() method
				}
				
				database.addEntry(new Entry(inputLine[0], inputLine[1], inputLine[2]));
			}
		}
		catch (Exception ex) { // catch block for errors
			if (ex instanceof FileNotFoundException) // print a different message if the exception is a file not found exception
				error(INPUT_ERROR_CODE[2]);
			else
				error(INPUT_ERROR_CODE[1]);		
		}
				 	
		return database;
	}
	
	/**
	*
	*Prints appropriate error messages when the processing of the database input file throws an error
	* 
	*/	
	private void error(int errorCode) {
		
		if (errorCode == 0) 
			System.out.println("An error has occurred with the file. Please check the input format of each entry in the\n" + 
										"file and make sure it has the appropriate number of fields.");
		else if (errorCode == 1) 
			System.out.println("An error has occurred with the file. Please meake sure the file only has a maximum of 100 entries");
		else
			System.out.println("An error has occurred. The file name does not exist or is not in the same directory.");
		
		System.exit(0);
	
	}	
}
		 