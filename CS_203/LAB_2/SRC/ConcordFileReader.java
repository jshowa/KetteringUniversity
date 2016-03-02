import java.io.*;
import java.util.*;

/***************************************************************************
 * <B>Class:</B> ConcordFileReader <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 2</B> <BR>
 * <B>Description:</B> This class acts as a parser for a list of concordances <BR>
 *                     for a give text. The class reads each concordance and <BR>
 *                     places it in the appropriate list of which the maximum <BR>
 *                     is 10 because the original text will have a maximum of <BR>
 *                     10 lines. A alphabetical list of concordances words <BR>
 *                     for each line of text is generated as well. <BR>
 * @author Jacob Howarth
 * @version 1.0
 ***************************************************************************
 */
public class ConcordFileReader {

    private String filename;
    private File file;

    private final int INIT_CAP = 500;

    private ArrayList<Concordance> concordList1, concordList2, concordList3,
                                    concordList4, concordList5, concordList6,
                                    concordList7, concordList8, concordList9,
                                    concordList10;

    private ArrayList<String> concordTable1, concordTable2, concordTable3,
                              concordTable4, concordTable5, concordTable6,
                              concordTable7, concordTable8, concordTable9,
                              concordTable10;

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> ConcordanceFileReader <BR>
     * <B>Description:</B>  A one argument constructor used to create <BR>
     *                      a concord file reader instance with a given <BR>
     *                      filename containing a list of concordances. <BR>
     * </P>
     **********************************************************************
     */
    public ConcordFileReader(String filename) {
        this.filename = filename;
        this.file = new File(filename);
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> read <BR>
     * <B>Description:</B>  Reads a concordance list text file and creates <BR>
     *                      the concordance list and concordance word tables <BR>
     *                      for each line in the original text. <BR>
     * </P>
     * @throws FileNotFoundException
     **********************************************************************
     */
    public void read() throws FileNotFoundException {
        /*-- VARIABLE TABLE --
         * Scanner reader - File scanner to read the text in the concordance
         *                  list text file.
         * String currLine - A line read in from the concordance list text file.
         * String[] currConcord - The same line read in from the text file except
         *                        without spaces.
         * boolean[] isLine - Used to turn on and off the instanstiating of the
         *                    array lists used to store each concordance list
         *                    for a specific line in the original text. If the
         *                    original text is less then the maximum of 10 lines,
         *                    then on the correct number of array lists will
         *                    be instantiated.
         */
        Scanner reader = new Scanner(file);
        boolean[] isLine = {false, false, false, false, false,
                            false, false, false, false, false, false};
        String currLine;
        String[] currConcord;

        // throw exception if file doesn't exist or is empty
        if (!file.exists() || file.length() == 0L)
            throw new FileNotFoundException("Error: The file does not exits" +
                    "or it is empty. Please specifiy a new file.");

        //Step 2: Read the lines from the file
        while (reader.hasNext()) {
            currLine = reader.nextLine();

            currConcord = currLine.split(" ");

            //Step 3: Turn on appropriate concordance lists and tables since
            //each concordance has a line number associated with it coresponding
            //to the original text.
            switch (currConcord[0].charAt(0)) {
                case '1': if (isLine[1] == false) { // if there is 1 line of original text...
                            concordList1 = new ArrayList<Concordance>(INIT_CAP);
                            concordTable1 = new ArrayList<String>(INIT_CAP);
                          }
                          isLine[1] = true; // turn on/instantiate that line...
                          break;
                case '2': if (isLine[2] == false) { // if there is a second line in
                                                    // the original text...
                            concordList2 = new ArrayList<Concordance>(INIT_CAP);
                            concordTable2 = new ArrayList<String>(INIT_CAP);
                          }
                          isLine[2] = true; // turn on/instantiate that line
                          break;
                case '3': if (isLine[3] == false) { // etc...
                            concordList3 = new ArrayList<Concordance>(INIT_CAP);
                            concordTable3 = new ArrayList<String>(INIT_CAP);
                          }
                          isLine[3] = true;
                          break;
                case '4': if (isLine[4] == false) {
                            concordList4 = new ArrayList<Concordance>(INIT_CAP);
                            concordTable4 = new ArrayList<String>(INIT_CAP);
                          }
                          isLine[4] = true;
                          break;
                case '5': if (isLine[5] == false) {
                            concordList5 = new ArrayList<Concordance>(INIT_CAP);
                            concordTable5 = new ArrayList<String>(INIT_CAP);
                          }
                          isLine[5] = true;
                          break;
                case '6': if (isLine[6] == false) {
                            concordList6 = new ArrayList<Concordance>(INIT_CAP);
                            concordTable6 = new ArrayList<String>(INIT_CAP);
                          }
                          isLine[6] = true;
                          break;
                case '7': if (isLine[7] == false) {
                            concordList7 = new ArrayList<Concordance>(INIT_CAP);
                            concordTable7 = new ArrayList<String>(INIT_CAP);
                          }
                          isLine[7] = true;
                          break;
                case '8': if (isLine[8] == false) {
                            concordList8 = new ArrayList<Concordance>(INIT_CAP);
                            concordTable8 = new ArrayList<String>(INIT_CAP);
                          }
                          isLine[8] = true;
                          break;
                case '9': if (isLine[9] == false) {
                            concordList9 = new ArrayList<Concordance>(INIT_CAP);
                            concordTable9 = new ArrayList<String>(INIT_CAP);
                          }
                          isLine[9] = true;
                          break;
                default: if (isLine[10] == false) {
                             concordList10 = new ArrayList<Concordance>(INIT_CAP);
                             concordTable10 = new ArrayList<String>(INIT_CAP);
                         }
                         isLine[10] = true;
                         break;
            } // end switch

            Concordance newConcord = new Concordance();

            //Step 4: Find the concordance word (i.e. the word with
            //the asterisk) index and set it in the concordance object.
            //Add the rest of the words from the text input line to the
            //Concordance object.
            for (int i = 0; i < currConcord.length; i++) {
                if (!(currConcord[i].matches("[\\d]:"))) {
                    if (currConcord[i].matches("\\*[\\w]+")) {
                        newConcord.addWord(currConcord[i]);
                        newConcord.setIndexOfConcordWord(newConcord.size() - 1);
                    }
                    else {
                        newConcord.addWord(currConcord[i]);
                    }
                }
            }

            //Step 5: Add the concordance to the concordance list
            //and the concordance word to the associated concordance table
            switch(currLine.charAt(0)) {
                case '1': concordList1.add(newConcord);
                          concordTable1.add(newConcord.getConcordWord());
                          break;
                case '2': concordList2.add(newConcord);
                          concordTable2.add(newConcord.getConcordWord());
                          break;
                case '3': concordList3.add(newConcord);
                          concordTable3.add(newConcord.getConcordWord());
                          break;
                case '4': concordList4.add(newConcord);
                          concordTable4.add(newConcord.getConcordWord());
                          break;
                case '5': concordList5.add(newConcord);
                          concordTable5.add(newConcord.getConcordWord());
                          break;
                case '6': concordList6.add(newConcord);
                          concordTable6.add(newConcord.getConcordWord());
                          break;
                case '7': concordList7.add(newConcord);
                          concordTable7.add(newConcord.getConcordWord());
                          break;
                case '8': concordList8.add(newConcord);
                          concordTable8.add(newConcord.getConcordWord());
                          break;
                case '9': concordList9.add(newConcord);
                          concordTable9.add(newConcord.getConcordWord());
                          break;
                default: concordList10.add(newConcord);
                          concordTable10.add(newConcord.getConcordWord());
                          break;
            } //end switch


        } //end while

    } //end method

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getConcordLists <BR>
     * <B>Description:</B>  Returns a concordance list for a given line <BR>
     *                      of text specified by a line number. <BR>
     * </P>
     * <!--Paramters-->
     * @param listNumber - An integer from 1 to 10 specifiying which
     *                     concordance list to return.
     * <!--Returns-->
     * @return ArrayList&ltConcordance&gt An array list of concordances
     *                                    for a given line of the original text.
     **********************************************************************
     */
    public ArrayList<Concordance> getConcordLists(int listNumber) {
        switch (listNumber) {
            case 1: return concordList1;
            case 2: return concordList2;
            case 3: return concordList3;
            case 4: return concordList4;
            case 5: return concordList5;
            case 6: return concordList6;
            case 7: return concordList7;
            case 8: return concordList8;
            case 9: return concordList9;
            case 10: return concordList10;
            default: return null;
        }
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getConcordTables <BR>
     * <B>Description:</B>  Returns a concordance table for a given line <BR>
     *                      of text specified by a line number. These tables <BR>
     *                      are used for the binary search process in the <BR>
     *                      text reconstruction algorithm. <BR>
     * </P>
     * <!--Paramters-->
     * @param listNumber - An integer from 1 to 10 specifiying which
     *                     concordance table to return.
     * <!--Returns-->
     * @return ArrayList&ltString&gt An array list of concordance words (i.e.
     *                               words with asterisk) for a given line of
     *                               the original text.
     **********************************************************************
     */
    public ArrayList<String> getConcordTables(int tableNumber) {
        switch (tableNumber) {
            case 1: return concordTable1;
            case 2: return concordTable2;
            case 3: return concordTable3;
            case 4: return concordTable4;
            case 5: return concordTable5;
            case 6: return concordTable6;
            case 7: return concordTable7;
            case 8: return concordTable8;
            case 9: return concordTable9;
            case 10: return concordTable10;
            default: return null;
        }
    }

}
