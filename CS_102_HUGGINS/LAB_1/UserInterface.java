
package lab0;
import java.io.IOException;
import java.util.*;

/**
 *************************************************************************
 * <B>Login ID:</B> howa1643 <BR>                                                         
 * CS-102, Fall 2008 <BR>                                                   
 * <B>Programming Assignment 1</B> <BR>                                             
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
     ************************************************************************
     */
    private static Database<Airport> database;
    private static Scanner userInput;
    
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
            database = DatabaseInput.loadFile(fileName);
        }
        catch (IOException ex) {
            System.out.println("The file being accessed does not exist.\n" +
                    "Please create an existing file with the included data\n" +
                    "and rerun the program.");
            System.exit(0);
        }
        
        userInput = new Scanner(System.in);
        System.out.println("**WELCOME TO THE AIRPORT DATABASE PROGRAM**");
        
        while (contProgram) {
            
            checkInput = true;
            System.out.print(displayUI());
            System.out.print("Please choose an option number from the above.\n" +
                "Your choice: ");
            userInputString = userInput.nextLine();
        
            while (checkInput) {
            
                if (userInputString.matches("[1239]{1}")) {
                    checkInput = false;
                }
                else {
                    System.out.println("Invalid input. Please choose ONLY the" +
                                     " numbers given in the menu.\n");
                    System.out.print("Please choose an option number from the above.\n" +
                    "Your choice: ");
                    userInputString = userInput.nextLine();
                }      
            }
        
            int choice = Integer.parseInt(userInputString);
        
            switch (choice) {
                case 1:  optionOne();
                        break;
                case 2:  optionTwo();
                        break;
                case 3:  optionThree();
                        break;
                case 9:  exitProgram();
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
        
        System.out.print("Enter an airport abbreviation: ");
        searchParam = userInput.nextLine();
        
        System.out.println(database.search(searchParam));
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
        String searchParam = "";
        
        System.out.print("Enter an airport name: ");
        searchParam = userInput.nextLine();
        
        System.out.println(database.search(searchParam));
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> optionThree <BR>                                                   
     * <B>Description:</B> A method that performs option three of the menu, <BR>
     *                     which prints the entire Airport database record.<BR>
     * </P>                                                                                                                                  
     **********************************************************************
     */ 
    private static void optionThree() {
        System.out.println(database);
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
                          "* 9 --> Exit the program                   *\n" +
                          "********************************************\n\n";
        return userMenu;
    }
}
