
package lab0;
import java.io.IOException;
import java.util.*;
import lab1.Database;

/**
 *************************************************************************
 * <B>Login ID:</B> howa1643 <BR>                                                         
 * CS-102, Fall 2008 <BR>                                                   
 * <B>Programming Assignment 2</B> <BR>                                             
 * <B>UserInterface class:</B> A class for displaying the options menu <BR>
 * and represents the methods that the user interacts with. <BR>
 * @author Jacob Howarth
 * @version 1.0                                                                                                          
 *************************************************************************
 */
public class UserInterface {
    
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     * Objects (static):                                                       
     * database - A database of Airport objects.              
     * userInput - A Scanner storing the input the user types.
     * 
     * Constants (static):
     * ONE, TWO, THREE, FOUR, FIVE, NINE - Constants representing the options
     *                                      from the menu.         
     ************************************************************************
     */
    private static Database<Airport> database;
    private static Scanner userInput;
    private static final int ONE = 1, TWO = 2, THREE = 3, FOUR = 4, FIVE = 5,
                                NINE = 9;
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> run <BR>                                                   
     * <B>Description:</B> This method calls the loadFile() method in <BR>
     *                     the DatabaseInput class to load the data from <BR>
     *                     the file into the database as well as displays <BR>
     *                     the interactive menu and controls the option selection <BR>
     *                     and keeps the programming running until the exit <BR>
     *                     option is selected. <BR>
     * </P>
     * @throws lab0.FileDataFormatException thrown if the data in the file
     *                                      is of incorrect format.                                                                              
     * <!--Parameters: -->                                                    
     *  @param fileName A string representing the file name containing the
     *                   data to load into the database.
     **********************************************************************
     */ 
    public static void run(String fileName) throws FileDataFormatException {
        String userInputString = "";
        boolean checkInput, contProgram = true;
        
        try {
            database = new Database(fileName); // try loading the file into the
                                                // database.
        }
        catch (IOException ex) { // if the file didn't exist, throw exception.
            System.out.println("The file being accessed does not exist.\n" +
                    "Please create an existing file with the included data\n" +
                    "and rerun the program.");
            System.exit(0);
        }
        
        userInput = new Scanner(System.in);
        System.out.println("**WELCOME TO THE AIRPORT DATABASE PROGRAM**");
        
        while (contProgram) { // display the menu and continually run the
                              // program until exiting.
            checkInput = true; // boolean loop control variable for checking
                                // input
            System.out.print(displayUI()); // display menu and ask user to
                                            // choose an option.
            System.out.print("Please choose an option number from the above.\n" +
                "Your choice: ");
            userInputString = userInput.nextLine();
        
            while (checkInput) { // input check loop start
            
                if (userInputString.matches("[123459]{1}")) {
                    // if the number the user types is one of the options available
                    // in the menu, then exit the input check loop.
                    checkInput = false;
                }
                else { // else display invalid input message and request new 
                        // input.
                    System.out.println("Invalid input. Please choose ONLY the" +
                                     " numbers given in the menu.\n");
                    System.out.print("Please choose an option number from the above.\n" +
                    "Your choice: ");
                    userInputString = userInput.nextLine();
                }      
            }
        
            int choice = Integer.parseInt(userInputString);
        
            switch (choice) { // call methods based on option choice.
                case ONE: optionOne();
                          break;
                case TWO: optionTwo();
                          break;
                case THREE: optionThree();
                            break;
                case FOUR: optionFour();
                           break;
                case FIVE: optionFive();
                           break;
                case NINE: exitProgram();
                           break;
            }
        }       
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> optionOne <BR>                                                   
     * <B>Description:</B> A method that performs option one of the menu, <BR>
     *                     which is to search the database for an Airport 
     *                     instances abbreviation and prints that exact instance. <BR>
     * </P>                                                                                                                                  
     **********************************************************************
     */ 
    private static void optionOne() {
        String searchParam = "";
        String result;
        
        System.out.print("Enter an airport abbreviation: ");
        searchParam = userInput.nextLine();
        
        result = (database.getListAbbrev()).search(searchParam);
        
        if (result.equals(""))
            System.out.println("Airport not found.");
        else 
            System.out.println(result);
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> optionTwo <BR>                                                   
     * <B>Description:</B> A method that performs option two of the menu, <BR>
     *                     which is to print all records of Airport instances <BR>
     *                     with the same airport name given a search parameter. <BR> 
     * </P>                                                                                                                                  
     **********************************************************************
     */ 
    private static void optionTwo() {
        String searchParam = ""; // parameter to search database for
        String result; // result of search
        
        System.out.print("Enter an airport name: "); // search by name
        searchParam = userInput.nextLine();
        
        result = (database.getListName()).search(searchParam);
        
        if (result.equals("")) // if the result is an empty string, no
                                // instances matching the search parameter 
                                // where found.
            System.out.println("Airport not found.");
        else 
            System.out.println(result); // print result.
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> optionThree <BR>                                                   
     * <B>Description:</B> A method that performs option three of the menu, <BR>
     *                     which prints the entire Airport database record.<BR>
     *                     There are two ways to print the database:
     *                     <DL>                                           
     *                       <DT> <B><I>Option 1</I></B> <DD> - print the 
     *                       database alphabetically by airport abbreviation.            
     *                       <DT> <B><I>Option 2</I></B> <DD> - print the 
     *                       database alphabetically by airport name. 
     *                     </DL>
     * </P>                                                                                                                                  
     **********************************************************************
     */ 
    private static void optionThree() {
        boolean checkInput = true; // boolean control variable for loop
        
        System.out.println("Would you like to print the list sorted" +
                " aplphabetically by:\n" + "1. Airport abbreviation\n" +
                "2. Airport name"); // ask the user to choose how to print the
                                    // the list, 1 = by abbreviation,
                                    // 2 = by name
        System.out.print("Your choice: ");
        
        String userInputString = userInput.nextLine();
        
        while (checkInput) { // loop to check for bogus input.
            
            if (userInputString.matches("[12]{1}")) { // if the user types
                checkInput = false;                    // 1 or 2, exit loop.
            }
            else { // if the user types something other than the options
                    // print message asking for reinput.
                System.out.println("Invalid input. Please choose from ONLY the" +
                                     " numbers given.\n");
                System.out.print("Please choose an option number from the above.\n" +
                    "Your choice: ");
                userInputString = userInput.nextLine();
            }      
        }
        
        if (userInputString.equals("1")) // if the user typed 1, print list
                                        // alphabetically by airport abbreviation
            System.out.println(database.toStringByAbbreviation());
        if (userInputString.equals("2"))// if the user typed 2, print list
                                        // alphabetically by airport name
            System.out.println(database.toStringByName());
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> optionFour <BR>                                                   
     * <B>Description:</B> A method that performs option four of the menu, <BR>
     *                     which involves adding a new airport record to the <BR>
     *                     database. <BR> 
     * </P>                                                                                                                                  
     **********************************************************************
     */ 
    public static void optionFour() {
        Airport airport = new Airport(); // airport object to be added.
        String userInputString; // user input string
        int indexToAddAt = 0; // relative index based on alphabetical sorting
                                // of each linked list in the database.
        String key; // key to sort each node by
        
        System.out.print("Enter the abbreviation of the airport (must be 3" +
                " uppercase characters): "); // user inputs abbreviation
        userInputString = userInput.nextLine();
        
        while(!(userInputString.matches("[A-Z]{3}"))) { // check the abbreviation
                 // to see if the input is capital and a maximum of 3 chars.  
                                                   
            System.out.println("Invalid input. The aiport abbreviation can" +
                    " only be 3 uppercase characters."); // if not, ask for new
                                                        // input
            System.out.print("Please enter a new airport abbreviation: ");
            userInputString = userInput.nextLine();
        }
        
        airport.setAbbreviation(userInputString); // set the abbreviation of
                                                // the airport object
                                                // to be added
        
        System.out.print("Enter the name of the airport: ");
        userInputString = userInput.nextLine();
        
        airport.setName(userInputString); // ask for the airport name and 
                                        // set the name of the airport object.
        
        key = airport.getAbbreviation(); // add the new airport record to
                                        // the database, sorted by abbreviation.
        indexToAddAt = (database.getListAbbrev()).compareToKey(key);
        (database.getListAbbrev()).add(airport, indexToAddAt, key);
        
        key = airport.getName(); // add the new airport record to
                                // the database, sorted by name.
        indexToAddAt = (database.getListName()).compareToKey(key);
        (database.getListName()).add(airport, indexToAddAt, key);
        
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> optionFive <BR>                                                   
     * <B>Description:</B> A method that performs option five of the menu, <BR>
     *                     which involves deleting an airport record from the <BR>
     *                     database based on abbreviation. <BR> 
     * </P>                                                                                                                                  
     **********************************************************************
     */ 
    public static void optionFive() {
        String searchParam = ""; // search parameter to see if the element
                                // of removal is in the list
        String result = "";
        String[] split; // if the item desired to be removed is in the list
                        // split the string returned by the search method
                        // and create a duplicate of the object desired to
                        // be removed so the index of that object can be found
                        // and removed through calls to indexOf and remove.
        int indexOfRemoval; // relative index calculated for removal.
        
        System.out.print("Enter an airport abbreviation: ");
        searchParam = userInput.nextLine(); // search for the record by 
                                            // abbreviation.
        
        result = (database.getListAbbrev()).search(searchParam);
        
        if (result.equals("")) // if the record wasn't found, print message and
                                // exit method.
            System.out.println("Airport not found.");
        else { 
            split = result.split(":"); // split the string and create a 
                                    // duplicate airport object for comparison.
            split[1] = split[1].trim(); // trim extra whitespace (ie new line
                                        // char off name)
            Airport itemOfRemoval = new Airport(split[0], split[1]);
            
            // find the airports index in the list sorted by abbreviation and
            // remove the element
            indexOfRemoval = (database.getListAbbrev()).indexOf(itemOfRemoval);
            (database.getListAbbrev()).remove(indexOfRemoval);
            
            // find the airports index in the list sorted by name and
            // remove the element.
            indexOfRemoval = (database.getListName()).indexOf(itemOfRemoval);
            (database.getListName()).remove(indexOfRemoval);
        }
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> exitProgram <BR>                                                   
     * <B>Description:</B> A method that performs option nine of the menu <BR>
     *                     which exits the program. <BR>
     * </P>                                                                                                                                  
     **********************************************************************
     */ 
    private static void exitProgram() {
        System.out.println("Program End.");
        System.exit(0);
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> displayUI <BR>                                                   
     * <B>Description:</B> A method that prints the interactive menu for <BR>
     *                     the database program. <BR>
     * </P>
     * <!--Returns: -->
     *  @return userMenu - A string representing the user menu.                                                                                                                                  
     **********************************************************************
     */ 
    private static String displayUI() {
        String userMenu = "********************************************\n" +
                          "* Available Options:                       *\n" +
                          "* 1 --> Search for an airport abbreviation *\n" +
                          "* 2 --> Search for an airport name         *\n" +
                          "* 3 --> Print the entire airport record    *\n" +
                          "* 4 --> Add an airport record              *\n" +
                          "* 5 --> Delete an airport record           *\n" +
                          "* 9 --> Exit the program                   *\n" +
                          "********************************************\n\n";
        return userMenu;
    }
}
