import ArrayADT.*;

/**
*
*Author: Jacob S. Howarth
*
*Assignment #2
*
*FileOperation.java
*
*This class defines methods and variables for generic file operations
*
*/

import java.io.*;
import java.util.*;

public class FileOperation {
	
	private String delimiter;
	private String fileName;
	private File inputFile;
	private ArrayADT<String> storedInput = new ArrayADT<String>();
	
	/**
	*
	*Constructor that initializes instance variables
	*
	*/
	public FileOperation() {
		
		delimiter = "";
		fileName = "";
	
	}
	
	/**
	*
	*Constructor that creates an instance with a specific file name to work on
	*
	* @param fileName   file path of input file
	*
	*/
	public FileOperation(String fileName) {
		
		this.fileName = fileName;
	
	}
	
	/**
	*
	*Constructor that creates an instance with a specifc file name and delimiter for working
	*on formatted files
	*
	* @param fileName   file path of input file
	* @param delimiter   file format delimiter that seperates certain input fields/data
	*
	*/
	public FileOperation(String fileName, String delimiter) {
		
		this.fileName = fileName;
		this.delimiter = delimiter;
		
	}
	
	/**
	*
	*Creates a file object to perform operations on
	*
	*/
	public void createFile() throws Exception {
		
		inputFile = new File(this.fileName);
		
		if(!inputFile.exists())
			throw (new FileNotFoundException());
	
	}
	
	/**
	*
	*Mutator method that sets the delimiter for files with formatted input
	*
	* @param delimiter   String regular expression specifying a input delimit pattern
	*
	*/
	public void setDelimiter(String delimiter) {
		
		this.delimiter = delimiter;
	
	}
	
	/**
	*
	*Accessor method to get the value of the delimiter
	*
	* @return   String regular expression containing the delimiter pattern
	*
	*/
	public String getDelimiter() {
		
		return delimiter;
	
	}
	
	/**
	*
	*Returns an array ADT containing the input data from the file
	*
	* @return   array ADT of type String containing each line of the file 
	*
	*/
	public ArrayADT<String> getStoredInput() {
		
		return storedInput;
		
	}
	
	/**
	*
	*Reads a text file for input
	*
	*/
	public void readTextData() throws Exception {
		
		String inputData = "";
		Scanner readFile = new Scanner(inputFile);
		
		while(readFile.hasNext()) {
			inputData = readFile.nextLine();
			storedInput.add(inputData);
		}
		
		readFile.close();
		
	}
	
	/**
	*
	*Reads a formatted file for input where each line of the file is broken up into input fields
	*specified by a delimiter pattern
	*
	*/
	public void readTextDataUsingDelimiter() throws Exception {
		
		String[] inputData;
		Scanner readFile = new Scanner(inputFile);
		
		while(readFile.hasNext()) {
			inputData = (readFile.nextLine()).split(this.delimiter);
			
			for (int i = 0; i <= inputData.length - 1; i++)
				storedInput.add(inputData[i]);
		}
			
		readFile.close();
	
	}
	
}