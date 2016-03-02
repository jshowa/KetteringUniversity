/**
 * Name: Jacob S. Howarth
 * Login ID: howa1643
 * CS-102, Fall 2008
 * Programming Assignment 4
 * Prog4 class: The class containing main which begins airport database 
 * program execution. 
 */
public class Prog4 {
    public static void main(String args[]) {
        if (args.length > 1) { // If there are more than one arguments at the
                            // command line, print an error message and exit
                            // program.
            System.out.println("Please enter only one argument for the" +
                    " file name at the command line.\n" +
                    "Usage: java Prog1 <filePathName>");
            System.exit(0);
        }
        else if (args.length == 0) { // If there are no command line arguments
                                   // print an error message and exit the program.
            System.out.println("Please enter an argument for the" +
                    " file name at the command line.\n" +
                    "Usage: java Prog1 <filePathName>");
            System.exit(0);
        }
        
        try {
            DatabaseMenu.run(args[0]); //If no errors, run the program.
        }
        catch (FileDataFormatException ex) {
            System.exit(0); // This exception will be thrown if the format 
        }                   // of the data input in the file did not have a
                            // '/' delimiter sperating the abrev. and name.
    }
}

