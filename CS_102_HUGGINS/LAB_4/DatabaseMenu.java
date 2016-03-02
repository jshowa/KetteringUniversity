
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Name: Jacob S. Howarth
 * Login ID: howa1643
 * CS-102, Fall 2008
 * Programming Assignment 4
 * DatabaseMenu class: The class that controls user I/O and program I/O 
 * and performs each of the menu options (add, delete, print, etc.) in the 
 * program.
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
    private static BinarySearchTree<Airport> abbrevDatabase;
    private static BinarySearchTree<Airport> nameDatabase;                                
    private static Scanner userInput = new Scanner(System.in);
    private static Scanner fileInput;
    private static File file;
    
    private final static int  SEARCH_ABBREV = 1, SEARCH_NAME = 2, PRINT_DATA = 3,
                        ADD_RECORD = 4, DELETE_RECORD = 5, SAVE_DATABASE = 6, 
                        LOAD_DATABASE = 7, EXIT = 9;
    
    
    /**
     **********************************************************************
     * Method: run                                                    
     * Description: This method calls the loadFile() method which
     *                     loads data from a given file name into the database 
     *                     as well as displays the interactive menu, controls 
     *                     the option selection and keeps the program running 
     *                     until the exit option is selected. 
     *  
     * @throws FileDataFormatException thrown if the data in the file
     *                                      is of incorrect format.                                                                              
     * Parameters:                                                    
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
                while(!(userInputString.matches("[12345679]{1}"))) {
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
                    case SAVE_DATABASE: saveDatabaseToFile();
                                        break;
                    case LOAD_DATABASE: loadDatabaseFromFile();
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
     * Method: loadFile                                                    
     * Description: This method loads the airport database with data
     *              parsed from a text file.
     *                                                                                
     * Parameters:                                                   
     *  @param fileName A string representing the file name containing the
     *                   data to load into the database.
     * Returns:
     * @return successOrFail - A boolean value that determines if the file
     *                         loaded successfully or not.
     **********************************************************************
     */ 
    private static boolean loadFile(String fileName) throws 
                                                FileDataFormatException {
        //1. Create the file
        file = new File(fileName);
        boolean successOrFail; // return true if file loaded successfully, otherwise return false
        String record = ""; // record holds an airport record from the file 
        String[] split; // array to split the abbrev data from the name data
        
        //2. Check if the file doesn't exist
        if (!(file.exists())) {
            successOrFail = false; // return false in this case and end method
            return successOrFail;
        }
        
        //3. Attempt to read the file
        try {
            fileInput = new Scanner(file);
        }
        catch (FileNotFoundException ex) {
            System.out.print("An attempt at reading the file was made and " +
                    "the read failed. Either the file does not exist or it " +
                    "is corrupt. Please create a new records file.\n");
            System.exit(0);
        }
        
        //4. Initialize tree sorted by abbreviations and tree sorted by names
        abbrevDatabase = new BinarySearchTree();
        nameDatabase = new BinarySearchTree();
        
        // 5. Begin file reading.
        while (fileInput.hasNext()) {
            record = fileInput.nextLine();
            
            //6. Error check the format
            if (record.charAt(3) != '/') {
                throw new FileDataFormatException();
            }
            else { // 7. Create the airport object from the file data
                split = record.split("/");
                split[0] = split[0].trim();
                split[1] = split[1].trim();
                
                Airport newAirport = new Airport(split[0], split[1]);
                
                // 8. Add each record to both lists in sorted order by key
                abbrevDatabase.add(newAirport, newAirport.getAbbreviation());
                nameDatabase.add(newAirport, newAirport.getName());
            }
        }
        
        // If the file loaded successfully, return true
        successOrFail = true;
        return successOrFail;       
    }
    
    /**
     **********************************************************************
     * Method: searchByAbbrev                                                   
     * Description: A method that performs option one of the menu, 
     *                     which is to search the database for an Airport 
     *                     instances abbreviation and prints the first instance 
     *                     of an Airport object containing that abbreviation.                                                                                                                                
     **********************************************************************
     */ 
    private static void searchByAbbrev() {
        String searchParam; // parameter to search the abbrev. list for
        String result = ""; // stores the search result as a string
        Airport temp; // bogus airport object used for searching records by
                      // abbreviation.
        
        // 1. Ask user for input to search for
        System.out.print("Please enter an abbreviation of an airport: ");
        searchParam = userInput.nextLine();
        
        // 2. Create a bogus airport object with the abbreviation specified 
        //    and search the tree sorted by abbreviation for an airport object
        //    with same abbreviation as the bogus airport object
        temp = new Airport(searchParam, "");
        result = abbrevDatabase.search(temp);
        
        // 3. If it wasn't found, print failure message otherwise print record 
        if (result.equals(""))
            System.out.println("\nThe airport record does not exist.");
        else
            System.out.println("\n" + result);
    }
    
    /**
     **********************************************************************
     * Method: searchByName                                                    
     * Description: A method that performs option two of the menu, 
     *              which is to print all records of Airport instances 
     *              with a similar airport name given a search parameter. 
     *              The full name of the airport does not need to be 
     *              specified.                                                                                                                                   
     **********************************************************************
     */
    private static void searchByName() {
        String searchParam; // parameter to search the name list for
        String result = ""; // stores the search result as a string
        Airport temp; // bogus airport object used for searching records by
                      // name.
        
        // 1. Ask user for input to search for
        System.out.print("Please enter a name for an airport: ");
        searchParam = userInput.nextLine();
        
        // 2. Create a bogus airport object with the name or abbrev. name 
        //    specified and search the tree sorted by airport name for 
        //    multiple airport objects that have a similar name to
        //    the search parameter and return these objects as one string.
        temp = new Airport("", searchParam);
        result = nameDatabase.inOrderSearch(temp);
        
        // 3. If it wasn't found, print failure message otherwise print record 
        if (result.equals(""))
            System.out.println("\nThe airport record does not exist.");
        else
            System.out.println("\n" + result);
    }
    
    /**
     **********************************************************************
     * Method: printRecords                                                   
     * Description: A method that performs option three of the menu, 
     *                     which prints the entire Airport database record.
     *                     There are two ways to print the database:
     *                                                               
     *                     Option 1 - print the 
     *                       database alphabetically by airport abbreviation.            
     *                     Option 2 - print the 
     *                       database alphabetically by airport name. 
     *                                                                                                                                                    
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
            result = abbrevDatabase.printInOrder();
        if (userInputString.equals("2"))
            result = nameDatabase.printInOrder();
        
        System.out.println(result);
    }
    
    /**
     **********************************************************************
     * Method: addRecord                                                   
     * Description: A method that performs option four of the menu,
     *              which involves adding a new airport record to the 
     *              database. 
     *                                                                                                                                
     **********************************************************************
     */ 
    private static void addRecord() {
        String name, abbreviation;// string to store the new name and abbrev.
                            // for the new airport record the user types.
        Airport newRecord = new Airport(); // the new airport object to store in 
                                           // the database
        
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
        abbrevDatabase.add(newRecord, abbreviation);
        nameDatabase.add(newRecord, name);
    }
    
    /**
     **********************************************************************
     * Method: deleteRecord                                                   
     * Description: A method that performs option five of the menu, 
     *              which involves deleting an airport record from the 
     *              database based on abbreviation.                                                                                                                          
     **********************************************************************
     */ 
    private static void deleteRecord() {
        String userInputString, searchParam; // string to store the typed user input.
                                             // and the search parameter to
                                             // search records for record to 
                                             // delete.
        String[] split; // splits the result into abbreviation data and name data
                        // needed to delete from both the name tree and abbrev. tree
        String result = ""; // stores the record string confirmed for deletion
        Airport temp; // bogus airport object used for searching records by
                      // abbreviation to find the record to delete.
        
        // 1. Ask the user for the abbreviation of a record chosen for deletion.
        System.out.print("Please enter an abbreviation of an airport to delete: ");
        searchParam = userInput.nextLine();
        
        // 2. Search for the record in the tree sorted by abbreviation and return it.
        temp = new Airport(searchParam, "");
        result = abbrevDatabase.search(temp);
        
        // 3. If the record wasn't found print an error message, otherwise
        // print the record and confirm deletion
        if (result.equals(""))
            System.out.println("This airport record does not exist.");
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
            // airport record. Then delete the record from each tree in the database.
            if (userInputString.equalsIgnoreCase("y")) {
                split = ((String)result).split(":");
                
                split[0] = split[0].trim();
                split[1] = split[1].trim();
                
                // remove from the tree sorted by abbreviation
                temp = new Airport(split[0], "");
                abbrevDatabase.delete(temp);
                
                // remove from the list sorted by name
                temp = new Airport("", split[1]);
                nameDatabase.delete(temp);
            }
        }
                  
    }
    
    /**
     **********************************************************************
     * Method: saveDatabaseToFile                                                    
     * Description: A method that performs option 6 of the menu
     *              which saves the tree database sorted by abbreviation
     *              in pre-order traversal fashion.                                                                                                                                  
     **********************************************************************
     */ 
    private static void saveDatabaseToFile() {
        String fileName = ""; // file path as entered by user
        PrintWriter output; // PrintWriter obeject that writes abbrev tree 
                            // to a file.
        
        System.out.print("Specify a file name to save the database to: ");
        fileName = userInput.nextLine();
        
        file = new File(fileName);
        
        if (file.exists()) { // if the file isn't created, don't save
            try {
                output = new PrintWriter(file);
            }
            catch (FileNotFoundException ex) {
                System.out.println("Error in writing to file. The file may " +
                  "not exist or is corrupt. Please create a new empty file.");
                return;
            }
            
            // Save tree in pre-order so it can be recreated upon loading.
            output.print(abbrevDatabase.printPreOrder());
            output.close();
            System.out.println("\nFile written successfully!\n");
        }
    }
    
    /**
     **********************************************************************
     * Method: loadDatabaseFromFile                                                    
     * Description: A method that performs option seven of the menu
     *              which loads a new file into and creates a new database
     *              replacing the existing one.                                                                                                                                  
     **********************************************************************
     */ 
    private static void loadDatabaseFromFile() throws FileDataFormatException {
        String fileName = ""; //file name to be specified by user
        
        System.out.print("Enter an exisiting file name to be loaded: ");
        fileName = userInput.nextLine();
        
        if(!loadFile(fileName)) // loadfile checks if the file exists or not
            System.out.print("Error in loading file, the file does not " +
                    "exist. Please specify a file path that exists.\n");
        else
            System.out.println("\nFile loaded successfully!\n");
    }
    
    /**
     **********************************************************************
     * Method: exitProgram                                                    
     * Description: A method that performs option nine of the menu
     *              which exits the program.                                                                                                                                  
     **********************************************************************
     */ 
    private static void exitProgram() {
        System.out.print("Ending Program.");
        System.exit(0);
    }
    
     /**
     **********************************************************************
     * Method: displayMenu                                                    
     * Description: A method that prints the interactive menu for 
     *              the database program.                                                                                                                                 
     **********************************************************************
     */ 
    private static void displayMenu() {
        System.out.println("**WELCOME TO THE AIRPORT DATABASE PROGRAM**\n\n" +
                          "********************************************\n" +
                          "* Available Options:                       *\n" +
                          "* 1 --> Search for an airport abbreviation *\n" +
                          "* 2 --> Search for an airport name         *\n" +
                          "* 3 --> Print the entire airport database  *\n" +
                          "* 4 --> Add an airport record              *\n" +
                          "* 5 --> Delete an airport record           *\n" +
                          "* 6 --> Save database to a file            *\n" +
                          "* 7 --> Load database from a file          *\n" +
                          "* 9 --> Exit the program                   *\n" +
                          "********************************************\n");
    }
    
}

