import java.io.*;

/***************************************************************************
 * <B>Class:</B> Main <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 2</B> <BR>
 * <B>Description:</B> This class defines main which is where the algorithm <BR>
 *                     begins execution. <BR>
 * @author Jacob Howarth
 * @version 1.0
 ***************************************************************************
 */
public class Main {

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> main <BR>
     * <B>Description:</B>  Where program begins execution. <BR>
     * </P>
     **********************************************************************
     */
    public static void main(String[] args) {

        // This is the maximum number of lines the original
        // text can have.
        final int MAX_LINE_OF_TEXT = 10;

        //Step 1: Check args and create ConcordFileReader
        if (args.length == 0 || args.length > 1) {
            System.out.print("Usage: java Main arg1");
        }
        else {
            try {
                //Step 2: Read in concordance list from text file.
                ConcordFileReader fileReader = new ConcordFileReader(args[0]);
                fileReader.read();

                //Step 3: Run text reconstruction algorithm for all
                //the lines in the original text.
                TextReconstructAlgo algorithm = new TextReconstructAlgo();
                for (int i = 1; i <= MAX_LINE_OF_TEXT; i++)
                    algorithm.runAlgorithm(fileReader.getConcordLists(i)
                            , fileReader.getConcordTables(i));

                //Step 4: Print reconstructed text to output
                System.out.print(algorithm);
            }
            catch (FileNotFoundException ex1) {
                System.out.print(ex1);
            }
            catch (IndexOutOfBoundsException ex2) {
                System.out.print(ex2);
            }
            catch (Exception ex3) {
                System.out.print(ex3);
            }
        }
    }

}
