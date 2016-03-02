/**
*Author: Jacob S. Howarth
*Assignment A1
*DatabaseInput.java
*
*This class contains methods for loading the input file and converting the input to a database.
*
*/

public class MainClass {
	public static void main(String[] args) {
		
		try {
			DatabaseInput phoneDirectory = new DatabaseInput(args[0]);
			DatabaseMenu menuUI = new DatabaseMenu();
		
			menuUI.processDatabaseInput(phoneDirectory.readDatabaseFile());
		
			while (true) {
			
				System.out.println(menuUI.toString());
				menuUI.optionsSelect();
		
			}
		}
		catch (Exception ex) {
			System.out.println("An error has occurred. Please specify a file to read at the command line\n" +
										"when the program is run.");
		} 
		
	}
}