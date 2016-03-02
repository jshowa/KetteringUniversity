import ArrayADT.*;

/**
*
*Author: Jacob S. Howarth
*
*Assignment #2
*
*Main.java
*
*This is the main application class that runs during execution.
*
*/

public class Main {
	public static void main(String[] args) {
		
		FileOperation inputFile = null;
		
		if (args.length == 1) 
			inputFile = new FileOperation(args[0]);
		else {
			System.out.println("Usage: java (Program Name) (fileName). Please add the \n" +
										"input filename at the command line during program execution.");
			System.exit(0);
		}
		
		try {
			
			inputFile.createFile();
			inputFile.readTextData();
		
		}
		catch (Exception excep) {
			
			System.out.println("File error. The file does not exist or has incorrent format\n" +
											"please check the file format and type.");
		}
			
		TennisPlayerMenu test = new TennisPlayerMenu();
		test.loadData(inputFile);
		
		while (true) {
			
			try {
				test.run();	
			}
			catch (Exception ex) {
				if (ex instanceof IndexOutOfBoundsException) 
					System.out.println("\nPlayer not found.\n");
			}
			
		}
	}
}