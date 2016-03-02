

import java.util.*;
import java.io.*;

/**
 *************************************************************************
 * <B>Login ID:</B> howa1643 <BR>                                                         
 * CS-102, Fall 2008 <BR>                                                   
 * <B>Programming Assignment 3</B> <BR>                                             
 * <B>DatabaseMenu class:</B> The class that controls user I/O and program I/O <BR>
 * and performs each of the menu options in the program. <BR>
 * @author Jacob Howarth                                                                                                        
 *************************************************************************
 */
public class DatabaseMenu {
    
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     * AirportDatabase:                                                       
     * database - A airport database containing two linked lists which have
     *            the airport records sorted by abbrev. and name.              
     * Scanner:
     * userInput - Takes in user input typed at the console.
     * fileInput - Takes in data from a file.
     * 
     * File:
     * file - The file to be loaded into the Airport database.
     * 
     * Constants:
     * SEARCH_ABBREV = 1, SEARCH_NAME = 2, PRINT_DATA = 3, ADD_RECORD = 4,
     * DELETE_RECORD = 5, EXIT = 9 - Constants that give meaningful names
     *                              to the menu options used in the switch
     *                              statement in deciding the operation to 
     *                              perform.    
     ************************************************************************
     */
    private static AirportDatabase database = new AirportDatabase();
    private static Scanner userInput = new Scanner(System.in);
    private static Scanner fileInput;
    private static File file;
    
    private final static int  SEARCH_ABBREV = 1, SEARCH_NAME = 2, PRINT_DATA = 3,
                        ADD_RECORD = 4, DELETE_RECORD = 5, EXIT = 9;
    
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> run <BR>                                                   
     * <B>Description:</B> This method calls the loadFile() method in <BR>
     *                     load the data from <BR>
     *                     the file into the database as well as displays <BR>
     *                     the interactive menu and controls the option selection <BR>
     *                     and keeps the programming running until the exit <BR>
     *                     option is selected. <BR>
     * </P> 
     * @throws FileDataFormatException thrown if the data in the file
     *                                      is of incorrect format.                                                                              
     * <!--Parameters: -->                                                    
     *  @param fileName A string representing the file name containing the
     *                   data to load into the database.
     **********************************************************************
     */ 
    public static void run(String fileName) throws FileDataFormatException {
        boolean successOrFail, continueProgram = true; // boolean loop control variables
        int choice; // integer for determined menu choice and helps select operation to perform
        String userInputString; // string used to store Scanner input
        
        successOrFail = loadFile(fileName);
        
        if (successOrFail) { // if the file loaded successfully. run program otherwise don't
            while (continueProgram) { // loop program continuously until the exit option is chosen
                displayMenu();
                
                //1. Ask user to choose an option
                System.out.print("Please choose an option from the " +
                        "menu above: ");
                userInputString = userInput.nextLine(); 
                
                // Error check what the user typed and ask for reinput if prev input was invalid
                while(!(userInputString.matches("[123459]{1}"))) {
                    System.out.println("Invalid input. Please enter an " + 
                            "option number from the menu.");
                    System.out.println("Please re-enter your choice: ");
                    userInputString = userInput.nextLine();
                }
                
                // 3. Select the option to be performed based off menu choice 
                choice = Integer.parseInt(userInputString);
                
                switch (choice) {
                    case SEARCH_ABBREV: searchByAbbrev();
                                        break;
                    case SEARCH_NAME: searchByName();
                                        break;
                    case PRINT_DATA: printRecords();
                                        break;
                    case ADD_RECORD: addRecord();
                                        break;
                    case DELETE_RECORD: deleteRecord();
                                        break;
                    case EXIT: exitProgram();
                                break;           
                } // end switch
            } // end while
        } // end if 
        
        // Print this error message if the file was not loaded successfully.
        System.out.println("An error has occurred. The file being loaded " +
                "doesn't exist or has no data in it. Please load a file " +
                "that exists and has data in the proper format or check " +
                "the file your loading for errors.");
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> loadFile <BR>                                                   
     * <B>Description:</B> This method loads the airport database with data
     *                     parsed from a text file.
     * </P>                                                                               
     * <!--Parameters: -->                                                    
     *  @param fileName A string representing the file name containing the
     *                   data to load into the database.
     * <!--Returns: -->
     * @return successOrFail - A boolean value that determines if the file
     *                         loaded successfully or not.
     **********************************************************************
     */ 
    private static boolean loadFile(String fileName) throws 
                                                FileDataFormatException {
        //1. Create the file
        file = new File(fileName);
        boolean successOrFail; // return true if file loaded successfully, otherwise return false
        int indexToAdd; // relative insertion index to insert airports in alphabetical order by key
        String record = "", key; // record holds an airport record from the file and
                                // key holds the string to sort each record by 
        String[] split; // array to split the abbrev data from the name data
        
        //2. Check if the file doesn't exists and length is equal to zero
        if (!(file.exists()) || file.length() == 0L) {
            successOrFail = false; // return false in this case and end method
            return successOrFail;
        }
        
        //3. read the file
        try {
            fileInput = new Scanner(file);
        }
        catch (FileNotFoundException ex) {
            System.out.print("The file does not exist. Please create a file\n" +
                    "and enter the data in the file before running the program.");
            System.exit(0);
        }
        
        while (fileInput.hasNext()) {
            record = fileInput.nextLine();
            
            //4. Error check the format
            if (record.charAt(3) != '/') {
                throw new FileDataFormatException();
            }
            else { // 5. Create the airport object from the file data
                split = record.split("/");
                split[0] = split[0].trim();
                split[1] = split[1].trim();
                
                Airport newAirport = new Airport(split[0], split[1]);
                
                // 6. Add each record to both lists in sorted order by key
                key = newAirport.getAbbreviation();
                indexToAdd = database.compareByAbbrev(key);
                (database.getListAbbrev()).add(indexToAdd, newAirport);
        
                key = newAirport.getName();
                indexToAdd = database.compareByName(key);
                (database.getListName()).add(indexToAdd, newAirport);
            }
        }
        
        // If the file loaded successfully, return true
        successOrFail = true;
        return successOrFail;       
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> searchByAbbrev <BR>                                                   
     * <B>Description:</B> A method that performs option one of the menu, <BR>
     *                     which is to search the database for an Airport 
     *                     instances abbreviation and prints the first instance <BR>
     *                     of an Airport object containing that abbreviation. <BR>
     * </P>                                                                                                                                  
     **********************************************************************
     */ 
    private static void searchByAbbrev() {
        String searchParam; // parameter to search the abbrev. list for
        String result = ""; // stores the search result as a string
        
        // 1. Ask user for input to search for
        System.out.print("Please enter an abbreviation of an airport: ");
        searchParam = userInput.nextLine();
        
        // 2. Search the database for the parameter being searched for
        result = database.search(database.getListAbbrev(), searchParam);
        
        // 3. If it wasn't found, print failure message otherwise print record 
        if (result.equals(""))
            System.out.println("\nThe airport record does not exist.");
        else
            System.out.println("\n" + result);
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> searchByName <BR>                                                   
     * <B>Description:</B> A method that performs option two of the menu, <BR>
     *                     which is to print all records of Airport instances <BR>
     *                     with a similar airport name given a search parameter. <BR>
     *                     The full name of the airport does not need to be <BR>
     *                     specified. <BR> 
     * </P>                                                                                                                                  
     **********************************************************************
     */
    private static void searchByName() {
        String searchParam; // parameter to search the name list for
        String result = ""; // stores the search result as a string
        
        // 1. Ask user for input to search for
        System.out.print("Please enter a name for an airport: ");
        searchParam = userInput.nextLine();
        
        // 2. Search the database for the parameter being searched for
        result = database.search(database.getListName(), searchParam);
        
        // 3. If it wasn't found, print failure message otherwise print record 
        if (result.equals(""))
            System.out.println("\nThe airport record does not exist.");
        else
            System.out.println("\n" + result);
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> printRecords <BR>                                                   
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
    private static void printRecords() {
        String userInputString, result = ""; // a string to store the user input
                                // and one to store the list of records.
        // 1. Choose how the list of records should be printed, 1) prints
        // sorted by abbreviation (alphabetically) and 2) prints sorted by name
        // alphabetically.
        System.out.print("Would you like to print the records sorted,\n" +
                "1) By abbreviation\n2) By name\n");
        System.out.println("Please choose from one of the numbers above.");
        System.out.print("Your choice: ");
        userInputString = userInput.nextLine();
        
        // 2. Error check the user's input to make sure the user can only
        // type 1 or 2 as a choice. If the user fails to do this, print
        // an error message and ask for reinput.
        while(!(userInputString.matches("[12]{1}"))) {
                System.out.println("Please enter an option number " +
                        "from above,\n 1 = Print records sorted by " +
                        "abbreviation.\n 2 = Print records sorted by name.");
                System.out.println("Please re-enter your choice: ");
                userInputString = userInput.nextLine();
        }
        
        // 3. If the user types 1, print the list of records sorted by abbreviation
        // If the user types 2, print the list sorted by name.
        if (userInputString.equals("1"))
            result = database.toString(database.getListAbbrev());
        if (userInputString.equals("2"))
            result = database.toString(database.getListName());
        
        System.out.println(result);
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> addRecord <BR>                                                   
     * <B>Description:</B> A method that performs option four of the menu, <BR>
     *                     which involves adding a new airport record to the <BR>
     *                     database. <BR> 
     * </P>                                                                                                                                  
     **********************************************************************
     */ 
    private static void addRecord() {
        String name, abbreviation, key; // string to store the new name and abbrev.
                            // for the new airport record the user types and the key
                            // to calculate the relative index to insert the new record
                            // into both lists.
        int indexToAdd = 0; // index to add the new record in both lists relative to
                            // it's alphabetical order within each list in the database
        Airport newRecord = new Airport(); // the new airport object to store in the database
        
        // 1. Enter the abbreviation of the new airport record being added
        System.out.print("Enter the abbreviation of the airport: ");
        abbreviation = userInput.nextLine();
        
        // Error check the abbreviation to make sure the user types 3 capital
        // letters, A-Z
        while(!(abbreviation.matches("[A-Z]{3}"))) {
            System.out.println("Invalid input. The aiport abbreviation can" +
                    " only be 3 uppercase characters."); // if not, ask for new
                                                        // input
            System.out.print("Please enter a new airport abbreviation: ");
            abbreviation = userInput.nextLine();
        }
        
        // store the abbreviation in the new Airport object
        newRecord.setAbbreviation(abbreviation);
       
        // 2. Ask the user to type the name of the new airport record
        System.out.print("Enter the name of the airport: ");
        name = userInput.nextLine();
        
        // store the name in the new Airport object
        newRecord.setName(name);
        
        // 3. Insert the new airport record in alphabetical order by abbrev.
        // in the list sorted by abbreviation and name in the list sorted by name
        key = newRecord.getAbbreviation();
        indexToAdd = database.compareByAbbrev(key);
        (database.getListAbbrev()).add(indexToAdd, newRecord);
        
        key = newRecord.getName();
        indexToAdd = database.compareByName(key);
        (database.getListName()).add(indexToAdd, newRecord);
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> deleteRecord <BR>                                                   
     * <B>Description:</B> A method that performs option five of the menu, <BR>
     *                     which involves deleting an airport record from the <BR>
     *                     database based on abbreviation. <BR> 
     * </P>                                                                                                                                  
     **********************************************************************
     */ 
    private static void deleteRecord() {
        String searchParam, userInputString, key; // strings to store the typed
                        // user input, the search parameter to search the abbrev.
                        // list for the record to delete and the key to delete
                        // the records from each of the sorted lists.
        String[] split; // splits the result into abbreviation data and name data
                        // needed to delete from both the name list and abbrev. list
        Object result = null; // stores the record confirmed for deletion
        int indexToRemove; // index of the record being deleted from each list 
        
        // 1. Ask the user for the abbreviation of a record chosen for deletion.
        System.out.print("Please enter an abbreviation of an airport to delete: ");
        searchParam = userInput.nextLine();
        
        // 2. Search for the record in the list sorted by abbreviation and return it.
        result = database.search(database.getListAbbrev(), searchParam);
        
        // 3. If the record wasn't found print an error message, otherwise
        // print the record and confirm deletion
        if (result instanceof String && result.equals(""))
            System.out.println("The airport record does not exist.");
        else {
            System.out.println(result);
            
            // Ask the user to confirm for deletion by typing y for yes or n for no 
            System.out.println("Would you like to delete this record?");
            System.out.print("Enter (Y) for YES and (N) for NO: ");
            userInputString = userInput.nextLine();
            
            // Error check the user input for y and n ignoring case and ask for 
            // reinput if the user types something other than y or n ignoring case.
            while(!(userInputString.matches("[YNyn]{1}"))) {
                System.out.println("Invalid choice, Please enter N to " +
                        "not delete the record or Y to delete the record.");
                System.out.println("Please re-enter your choice: ");
                userInputString = userInput.nextLine();
            }
            
            // 4. If the user confirms deletion with a yes, then slipt the string
            // stored in result and seperate the abbreviation and name data of the
            // airport record. Then delete the record from each list in the database
            // by key.
            if (userInputString.equalsIgnoreCase("y")) {
                split = ((String)result).split(":");
                
                split[0] = split[0].trim();
                split[1] = split[1].trim();
                
                // remove from the list sorted by abbreviation
                key = split[0];
                indexToRemove = database.compareByAbbrev(key);
                (database.getListAbbrev()).remove(indexToRemove);
                
                // remove from the list sorted by name
                key = split[1];
                indexToRemove = database.compareByName(key);
                (database.getListName()).remove(indexToRemove);
            }
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
        System.out.print("Ending Program.");
        System.exit(0);
    }
    
     /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> displayMenu <BR>                                                   
     * <B>Description:</B> A method that prints the interactive menu for <BR>
     *                     the database program. <BR>
     * </P>                                                                                                                                 
     **********************************************************************
     */ 
    private static void displayMenu() {
        System.out.println("**WELCOME TO THE AIRPORT DATABASE PROGRAM**\n\n" +
                          "********************************************\n" +
                          "* Available Options:                       *\n" +
                          "* 1 --> Search for an airport abbreviation *\n" +
                          "* 2 --> Search for an airport name         *\n" +
                          "* 3 --> Print the entire airport record    *\n" +
                          "* 4 --> Add an airport record              *\n" +
                          "* 5 --> Delete an airport record           *\n" +
                          "* 9 --> Exit the program                   *\n" +
                          "********************************************\n");
    }
    
    
}
