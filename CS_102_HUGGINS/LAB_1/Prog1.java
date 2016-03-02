
package lab0;
import java.io.IOException;

/**
 *************************************************************************
 * <B>Login ID:</B> howa1643 <BR>                                                         
 * CS-102, Fall 2008 <BR>                                                   
 * <B>Programming Assignment 1</B> <BR>                                             
 * <B>Prog1 class:</B> The class containing main which begins Database <BR>
 * program execution. <BR>
 * @author Jacob Howarth
 * @version 1.0                                                                                                          
 *************************************************************************
 */
public class Prog1 {
    public static void main(String[] args) {
        
        if (args.length > 1) // If there are more than one arguments at the
                            // command line, print an error message and exit
                            // program.
            System.out.println("Please enter only one argument for the" +
                    " file name at the command line.\n" +
                    "Usage: java Prog1 <filePathName>");
        else if (args.length == 0) // If there are no command line arguments
                                   // print an error message and exit the program.
            System.out.println("Please enter an argument for the" +
                    " file name at the command line.\n" +
                    "Usage: java Prog1 <filePathName>");
        else {
            try {
                UserInterface.run(args[0]); // run the Database program
            }
            catch (IndexOutOfBoundsException ex) {
                System.out.println("The database has been accessed using" +
                        " a negative index or an index outside its size.");
                System.exit(0);
            }
            catch (FileDataFormatException ex1) {
                System.exit(0); // custom exception for incorrect data format
            }                   // in the file
        }
    }
}
