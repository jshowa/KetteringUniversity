import ArrayADT.*;
import java.util.*;

/**
*
*Author: Jacob S. Howarth
*
*Assignment #2
*
*TennisPlayerMenuOptions.java
*
*This class performs the operations as requested from a command available to the user through the menu. 
*This class performs all the back=end operation to the menu class.
*
*/

public class TennisPlayerMenuOptions {
	
	/**
	*
	*Default constructor for a TennisPlayerMenuOptions instance.
	*
	*/
	public TennisPlayerMenuOptions() {
	}
	
	/**
	*
	*Search for a tennis player given a player name in an array ADT of generic type TennisPlayer
	*
	* @param database   array ADT of type TennisPlayer that contains tennis player objects
	*
	*/
	public void searchForPlayer(ArrayADT<TennisPlayer> database) {
		
		int i = 0;
		String searchElement = "";
		String result = "";
		
		Iterator<TennisPlayer> scanner = database.iterator(); // use iterator in array ADT class to iterator through
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter a player name to search for: ");
		searchElement = input.nextLine();
	
		while(scanner.hasNext() && i < database.size()) { // use iterator to scan through the array ADT 
			
			if (((database.getElement(i)).getPlayerName()).equalsIgnoreCase(searchElement))
				result += (database.getElement(i)).toString() + "\n"; // concatenate string when the element searched for = element in the array by player name
																						// the concatenation also allows for repeated data (ie entries with the same name printed twice)
			i++;
		}
		
		if (result == "")
			result = "Player not found.\n";
		
		System.out.println("\n" + result);
	}
	
	/**
	*
	*Finds the tennis players with the most grand slam titles won
	*
	* @param database   generic array ADT of type TennisPlayer that contains tennis player objects
	*
	*/
	public void printMostGrandSlam(ArrayADT<TennisPlayer> database) {
	
		String result = "";
		int maximum = Integer.parseInt((database.getElement(0)).getGrandSlamTitleNum()); // set maximum to first array element
			
		for (int i = 0; i < database.size(); i++) {
			
			int comparisonNumber;
			comparisonNumber = Integer.parseInt((database.getElement(i)).getGrandSlamTitleNum()); // convert number of string type to 
																															// integer type in order to perform comparison
			if (comparisonNumber > maximum) {
				maximum = Integer.parseInt((database.getElement(i)).getGrandSlamTitleNum());
				result = (database.getElement(i)).toString(); // everytime a new maximum is found, result is reassigned
			}
			else if (comparisonNumber == maximum && i != 0) // if any other tennis players have grand slam titles won = to max, print them
				result += "\n" + (database.getElement(i)).toString(); // by concatenating to string.
			else {
			}
		
		}
		
		System.out.println("\n" + result + "\n"); 
	
	}
	
	/**
	*
	*Finds the tennis players with the least grand slam titles won
	*
	* @param database   generic array ADT of type TennisPlayer that contains tennis player objects
	*
	*/
	public void printLeastGrandSlam(ArrayADT<TennisPlayer> database) {
	
		String result = "";
		int smallest = Integer.parseInt((database.getElement(0)).getGrandSlamTitleNum()); // set smallest to first array element
			
		for (int i = 0; i < database.size(); i++) {
			
			int comparisonNumber;
			comparisonNumber = Integer.parseInt((database.getElement(i)).getGrandSlamTitleNum()); // convert number of string type to 
																															// integer type in order to perform comparison
			if (smallest > comparisonNumber) {
				smallest = Integer.parseInt((database.getElement(i)).getGrandSlamTitleNum());
				result = (database.getElement(i)).toString(); // everytime a new smallest is found, result is reassigned
			}
			else if (comparisonNumber == smallest && i != 0) // if any other tennis players have grand slam titles won = to smallest, print them
				result += "\n" + (database.getElement(i)).toString(); // by concatenating to string.
			else {
			}
		
		}
		
		System.out.println("\n" + result + "\n");
	
	}
	
	/**
	*
	*add a tennis player to the array ADT
	*
	* @param database   generic array ADT of type TennisPlayer that contains tennis player objects
	*
	*/
	public void addPlayer(ArrayADT<TennisPlayer> database) {
		
		final int MAX_TENNIS_PLAYER_INSTANCE_DATA = 4;
		String[] tennisPlayerData = new String[MAX_TENNIS_PLAYER_INSTANCE_DATA]; // since each tennis player has only 4 instance variables and array
		Scanner input = new Scanner(System.in);    // that can hold each one is sufficient
		boolean reLoop = true;
		
		System.out.print("Enter a name for the player: ");
		
		tennisPlayerData[0] = input.nextLine();
		
		while(reLoop) { // keep looping util user input is correct
		
			System.out.print("Enter the number of grand slam titles won: ");
			tennisPlayerData[1] = input.nextLine();
			
			if((tennisPlayerData[1]).matches("\\d+")) // use regular expression to check user input and avoid parsing
				reLoop = false;                        // errors in find maximum and least grand slam titles won methods
			else
				System.out.println("\nPlease enter only a sequence of digits\n");
		
		}
		
		reLoop = true;
		
		System.out.print("Enter the country of origin for the player: ");
		
		tennisPlayerData[2] = input.nextLine();
		
		while(reLoop) { // keep looping util user input is correct
			
			System.out.print("Enter the gender of the player, type M for male, F for female: ");
			tennisPlayerData[3] = input.nextLine();
			
			if((tennisPlayerData[3]).matches("[MF]{1}"))	// again, regular expressions to check user input for a new added players gender
				reLoop = false;
			else
				System.out.print("\nInvalid input for player gender, please enter a M\n" +
												"for male, F for female.\n\n"); 
		
		}
		
		database.add(new TennisPlayer(tennisPlayerData[0], tennisPlayerData[1], tennisPlayerData[2], tennisPlayerData[3]));
			
	}
	
	/**
	*
	*Prints all tennis players in array ADT database to standard out
	*
	* @param database   generic array ADT of type TennisPlayer that contains tennis player objects
	*
	*/
	public String printDatabase(ArrayADT<TennisPlayer> database) {
	
		String result = "";
		result = database.toString();
		return result;
	
	}
	
	/**
	*
	*Prints all female tennis players in array ADT database to standard out
	*
	* @param database   generic array ADT of type TennisPlayer that contains tennis player objects
	*
	*/
	public void printFemalePlayers(ArrayADT<TennisPlayer> database) {
	
		int i = 0;
		String result = "";
		
		Iterator<TennisPlayer> scanner = database.iterator();
		
		while (scanner.hasNext() && i < database.size()) {
			
			if (((database.getElement(i)).getPlayerGender()).equals("F"))
				result += (database.getElement(i)).toString() + "\n";
		
			i++;
		
		}
		
		if (result == "")
			result = "No female players found.\n";
		
		System.out.println("\n" + result);
	
	}
	
	/**
	*
	*Prints all male tennis players in array ADT database to standard out
	*
	* @param database   generic array ADT of type TennisPlayer that contains tennis player objects
	*
	*/
	public void printMalePlayers(ArrayADT<TennisPlayer> database) {
	
		int i = 0;
		String result = "";
		
		Iterator<TennisPlayer> scanner = database.iterator();
		
		while (scanner.hasNext() && i < database.size()) {
			
			if (((database.getElement(i)).getPlayerGender()).equals("M"))
				result += (database.getElement(i)).toString() + "\n";
		
			i++;
		
		}
		
		if (result == "")
			result = "No male players found.\n";
		
		System.out.println("\n" + result);
	
	}

}
