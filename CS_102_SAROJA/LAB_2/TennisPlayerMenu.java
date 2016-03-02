import ArrayADT.*;

/**
*
*Author: Jacob S. Howarth
*
*Assignment #2
*
*TennisPlayerMenu.java
*
*This class defines an interactive menu interface for a tennis player database 
*that a user can use to perform operations on the database
*
*/

import java.util.*;

public class TennisPlayerMenu {

	private ArrayADT<TennisPlayer> playerDatabase = new ArrayADT<TennisPlayer>();
	private Scanner input;
	private TennisPlayerMenuOptions options = new TennisPlayerMenuOptions();
	private String userInput;
	
	/**
	*
	*Default constructor that creates an instance of a TennisPlayerMenu with user input from the console
	*
	*/
	public TennisPlayerMenu() {
		input = new Scanner(System.in);
	}
	
	/**
	*
	*Creates and array ADT of tennis players based on the input data from a file
	*
	* @param storedData   data collected from a file as input
	*
	*/
	public void loadData(FileOperation storedData) {
		
		ArrayADT<String> data = storedData.getStoredInput();
		
		Iterator<String> scan = data.iterator();
		
		while(scan.hasNext()) 
			playerDatabase.add(new TennisPlayer(scan.next(), scan.next(), scan.next(), scan.next()));
	
	}
	
	/**
	*
	*Mutator to set the input given from the user at the tennis player database menu
	*
	*/
	public void setUserInput() {
		
		this.userInput = input.nextLine();
	
	}
	
	/**
	*
	*Accessor used to access input given from the user at the tennis player database menu
	*
	* @return   user console input
	*
	*/
	public String getUserInput() {
		
		return userInput;
	
	}
	
	/**
	*
	*Run's the menu interface and performs the operations of each menu command
	*
	*/
	public void run() {
		
		boolean reLoop = false;
		
		System.out.println(toString());
		
		while(!reLoop) {
			
			System.out.println("Please enter one of the above commands.");
			System.out.print("input: ");
			
			setUserInput(); // error occurs on UNIX machines during method call with scanner that prints the input to the output
									// when no code to print exists
		
			System.out.println();
		
			if((getUserInput()).equalsIgnoreCase("S")) {
				options.searchForPlayer(playerDatabase);
				reLoop = true;
			}
			else if((getUserInput()).equalsIgnoreCase("G")) {
				options.printMostGrandSlam(playerDatabase);
				reLoop = true;
			}
			else if((getUserInput()).equalsIgnoreCase("A")) {
				options.addPlayer(playerDatabase);
				reLoop = true;
			}
			else if((getUserInput()).equalsIgnoreCase("L")) {
				options.printLeastGrandSlam(playerDatabase);
				reLoop = true;
			}
			else if((getUserInput()).equalsIgnoreCase("P")) {
				System.out.println(options.printDatabase(playerDatabase));
				reLoop = true;
			}
			else if((getUserInput()).equalsIgnoreCase("F")) {
				options.printFemalePlayers(playerDatabase);
				reLoop = true;
			}
			else if((getUserInput()).equalsIgnoreCase("M")) {
				options.printMalePlayers(playerDatabase);
				reLoop = true;
			}
			else if((getUserInput()).equalsIgnoreCase("Q")) 
				System.exit(0);
			else 
				System.out.println("Invalid input. Please type only one of the commands above.\n");
		}
	
	}
	
	/**
	*
	*Displays the menu user interface
	*
	* @return   String containing the menu and commands for display
	*
	*/	
	public String toString() {
			
		String menuUI;
			
		menuUI = "***************************************\n" +
					"*                                      \n" +
					"*Tennis Player Database v1.0			 \n" +
					"*                                      \n" +
					"***************************************\n" +
					"* Option Commands:                     \n" +
					"*                                      \n" +
					"* S - search database for player		 \n" +    
					"*     by name                          \n" +
					"* A - add a player to the database	 \n" +
					"* G - list player(s) with most grand	 \n" +
					"*     slam titles                      \n" +
					"* L - list player(s) with least grand  \n" +
					"*     slam titles							 \n" +
					"* F - list all female players			 \n" +
					"* M - list all male players				 \n" +
					"* P - list all the players				 \n" +
					"* Q - quit program							 \n" +
					"*													 \n" +
					"***************************************\n\n";
		
		return menuUI;
			
	}
} 