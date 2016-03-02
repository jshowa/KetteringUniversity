/***************************************************************************
 * <B>Name:</B> Jacob Howarth <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 1</B> <BR>
 * <B>Main class:</B> This class is the driver code for the repeated word <BR>
 * algorithm. It's job is to read the input file, run the repeated word algorithm <BR>
 * and then print out the three most repeated words by first occurence in the <BR>
 * file.
 * @author Jacob Howarth 
 ***************************************************************************
 */
public class Main {

    /**
	  ***********************************************************************
	  * <P align="left">
	  * <B>Method:</B> main
	  * <B>Description:</B> This is the main method where the program <BR>
	  *							begins execution. <BR>
	  * </P>
	  * <!--Parameters-->
     * @param args A string array representing the command line arguments
	  *				 to the program.
	  ***********************************************************************
     */
    public static void main(String[] args) {

        // Read input text file
        FileReader progStart = new FileReader();

        // Run repeated word algorithm on input text
        RepeatedWordAlgorithm runAlgorithm =
        new RepeatedWordAlgorithm(progStart.getList(), new LinkedList<WordCountNode<String>>());

        // Display the top three repeated word
        System.out.println();
        System.out.println("\nThe three most repeated words based " +
                "on the first occurrence of each word in the article " +
                "are:\n" + runAlgorithm.threeMostRepeated());
    }

}
