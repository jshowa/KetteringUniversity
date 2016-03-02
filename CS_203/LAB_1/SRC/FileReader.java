import java.io.*;
import java.util.*;

/***************************************************************************
 * <B>Name:</B> Jacob Howarth <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 1</B> <BR>
 * <B>FileReader class:</B> This class reads the text from an input text file <BR>
 *                          and stores each word in the text file into a linked <BR>
 *                          list.
 * @author Jacob Howarth
 * @version 1.0
 ***************************************************************************
 */
public class FileReader {

    /**
     * -- VARIABLE DECLARATIONS --
     * File newFile - File object created for the file to be read as specified
     *                by the user.
     * LinkedList<String> wordList - List of words from the input file
     */
    private File newFile;
    private LinkedList<String> wordList = new LinkedList<String>();

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> FileReader <BR>
     * <B>Description:</B> A zero argument constructor that creates a file <BR>
     *                     given a file name. The constructor also checks <BR>
     *                     for errors in case the file does not exist, or <BR>
     *                     if it's empty before reading the file. <BR>
     * </P>
     **********************************************************************
     */
    public FileReader() {

        Scanner input = new Scanner(System.in);
        String fileName;

        System.out.print("Please enter a valid file name with extension: ");
        fileName = input.nextLine(); // get the file name/path to read from user

        this.newFile = new File(fileName); // create the file

        // If the file doesn't exist, or is empty have the user
        // submit a new file name.
        while (!newFile.exists() || newFile.length() == 0L) {
    			System.out.println("The file does not exist or the file is empty.\n" +
										"Please check the file or enter a new file name.\n");        
				
				System.out.print("Please enter a new file name: ");
            fileName = input.nextLine();

            this.newFile = new File(fileName);
        }

        try {
            read(); // try to read the file

        }
        catch (IOException ex) {
            System.out.println(ex.toString());
        }

    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> read <BR>
     * <B>Description:</B> Reads text from a text file and stores each word <BR>
     *                     in the text file into a linked list. Note: All <BR>
     *                     punctuation from each word token is removed <BR>
     *                     before being added to the list. The method <BR>
     *                     also prints the file, 15 words per line, to the <BR>
     *                     console with punctuation intact before adding <BR>
     *                     to the list.
     * </P>
     * @throws FileNotFoundException
     **********************************************************************
     */
    public void read() throws FileNotFoundException {

        /* -- VARIABLE DECLARATIONS --
         * int wordsPerLineCount - words per line counter to keep track of the
         *                         number of words per line to be displayed to
         *                         the output after the file has been tokenized.
         * Scanner fileScanner - Scanner used to get data from the file. Each
         *                       word in the file is tokenized by whitespace.
         */
        int wordsPerLineCount = 0;
        Scanner fileScanner = new Scanner(newFile);

        System.out.println();

            while (fileScanner.hasNext()) {
                String token = fileScanner.next(); // get each word from the file

                System.out.print(token + " "); // print the word

                wordsPerLineCount++; // increment the counter

                // After 15 words have been displayed to the console, move to
                // a new line and reset the counter
                if (wordsPerLineCount == 15) {
                    System.out.println();
                    wordsPerLineCount = 0;
                }

                // Use a regular expression to remove all the punctuation
                // from the tokenized word that isn't whitespace, such as
                // ., "", ;, etc.
                token = token.replaceAll("[\\W&&[[^\\-]{1}]&&[^']]", "");

                if (!token.equals(""))
                    wordList.addLast(token); // add the word to the end of the
                                            // linked list
            }

    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getList <BR>
     * <B>Description:</B> A accessor method to get the list of words <BR>
     *                     read in from the input text file. <BR>
     * </P>
     **********************************************************************
     */
    public LinkedList<String> getList() {
        return this.wordList;
    }

}
