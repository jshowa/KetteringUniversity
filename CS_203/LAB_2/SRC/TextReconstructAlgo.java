import java.util.*;

/***************************************************************************
 * <B>Class:</B> TextReconstructAlgo <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 2</B> <BR>
 * <B>Description:</B> This class specifys the text reconstruction algorithm <BR>
 *                     given a list of concordances from that text. The <BR>
 *                     algorithms time complexity is somewhat difficult to <BR>
 *                     deduce in that it exits loops early depending on <BR>
 *                     certain conditions which effects the number of comparisons <BR>
 *                     when searching for concordances to reconstruct the original <BR>
 *                     text. In the absolute worse case, the algorithm will have <BR>
 *                     the time n + n * (log(n) + k) or O(nlogn) because this <BR>
 *                     would be the case that it takes n time to find the concordance <BR>
 *                     with the first word having the asterisk and then making n <BR>
 *                     binary searches with a specific amount of k comparisons until <BR>
 *                     the right concordance is found if the concordance index returned <BR>
 *                     by the binary search was not the concordance being looked for <BR>
 *                     However, the input to produce this result would be extremely <BR>
 *                     unlikely as the original text would probably need the same <BR>
 *                     word to be repeated over and over line by line which would <BR>
 *                     have no meaning or context in any language <BR>.
 * @author Jacob Howarth
 * @version 1.0
 ***************************************************************************
 */
public class TextReconstructAlgo {

    private String text = "";

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> runAlgorithm <BR>
     * <B>Description:</B>  This method simply runs the text reconstruction <BR>
     *                      algorithm to reconstruct an original prose given <BR>
     *                      a list of concordances from that prose. <BR>
     *                      The algorithm is run for each line of the original <BR>
     *                      text. <BR>
     * </P>
     * <!--Parameters:-->
     * @param ArrayList&ltConcordance&gt - An array list of concordances to reconstruct
     *                                     a specific line of the original text.
     * @param ArrayList&ltString&gt - A concordance table that contains all the
     *                                asterisk words used for constructing the
     *                                concordances of a line in the original text.
     **********************************************************************
     */
    public void runAlgorithm(ArrayList<Concordance> concordList, ArrayList<String> concordTable) {

        String newKeyConcord = "", prevOfKeyWord = "";
        boolean stopInitSearch = true, doneSearching = false, stopCompare = false;

        // If there were less then 10 lines in the original text, then
        // there will NOT be concordances for 10 lines of text, so
        // if any of the lists were not created in ConcordFileReaders
        // read method, do NOT go through with the algorithm.
        if (concordList == null)
            return;


        // Step 1: Find the concordance whose first word is the word
        // containing the asterisk. When found, go to the end of the
        // concordance and set the new search key to the last word
        // in the concordance and store the word previous to it.
        for (int i = 0; i < concordList.size() && stopInitSearch; i++) {
            if ((concordList.get(i).getWord(0)).matches("\\*[\\w]+")) {
                text += concordList.get(i).toString(0);
                newKeyConcord = '*' + concordList.get(i).getWord(Concordance.MAX_CONCORD_LEN - 1);
                prevOfKeyWord = concordList.get(i).getPrevWord(Concordance.MAX_CONCORD_LEN - 1);
                stopInitSearch = false;
            }
        }

        //Step 2: Search for the next piece of the text line by searching
        //for the new asterisk word using a binary search table. This
        //binary search will calculate the index to look at. Using the
        //previous word to the asterisk word, one can find the next part
        //of the text and append it to the original starting part.
        //Keep doing the searches until the word with the asterisk
        //is the last word in the concordance.
        while (!doneSearching) {
            int index = binSearch(concordTable, newKeyConcord);

            if (index != -1) {
                Concordance concordCheck = concordList.get(index);

                // TODO add sanity check if prev.matches(\\*[\\w]+) && key.matches(\\*[\\w]+)
                // for binary search situation (may not be needed?)
                if (concordCheck.getIndexOfConcordWord() == Concordance.MAX_CONCORD_LEN - 1) {
                    text += "\n";
                    doneSearching = true;
                }
                if (prevOfKeyWord.equals(concordCheck.getPrevWord(concordCheck.getIndexOfConcordWord())) && !doneSearching) {
                    text += concordCheck.toString(concordCheck.getIndexOfConcordWord() + 1);
                    newKeyConcord = '*' + concordCheck.getWord(Concordance.MAX_CONCORD_LEN - 1);
                    prevOfKeyWord = concordCheck.getPrevWord(Concordance.MAX_CONCORD_LEN - 1);
                }
                else {
                    // Search left and right in the concordance list until the
                    // right concordance is found.
                    Concordance concordCheckLeft, concordCheckRight;

                    for (int i = index - 1, j = index + 1;
                    i >= 0 && j < concordTable.size() && !stopCompare && !doneSearching; i--, j++) {
                       concordCheckLeft = concordList.get(i);
                       concordCheckRight = concordList.get(j);

                       // Compare the prev word to the prev word of the key in the
                       // left half of the concordance list
                       if (prevOfKeyWord.equals(concordCheckLeft.getPrevWord(concordCheckLeft.getIndexOfConcordWord()))) {
                            text += concordCheckLeft.toString(concordCheckLeft.getIndexOfConcordWord() + 1);
                            newKeyConcord = '*' + concordCheckLeft.getWord(Concordance.MAX_CONCORD_LEN - 1);
                            prevOfKeyWord = concordCheckLeft.getPrevWord(Concordance.MAX_CONCORD_LEN - 1);
                            stopCompare = true;
                       }

                       // Compare the prev word to the prev word of the key in the
                       // right half of the concordance list
                       if (prevOfKeyWord.equals(concordCheckRight.getPrevWord(concordCheckRight.getIndexOfConcordWord()))) {
                            text += concordCheckRight.toString(concordCheckRight.getIndexOfConcordWord() + 1);
                            newKeyConcord = '*' + concordCheckRight.getWord(Concordance.MAX_CONCORD_LEN - 1);
                            prevOfKeyWord = concordCheckRight.getPrevWord(Concordance.MAX_CONCORD_LEN - 1);
                            stopCompare = true;
                       }
                    } // end for
                } // end else

            } // end outer if
        } // end while

    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> binSearch <BR>
     * <B>Description:</B>  Standard binary search algorithm which searches a <BR>
     *                      sorted array for a specific key value. The algorithm <BR>
     *                      uses a standard divide and conquer technique where <BR>
     *                      it searches at most half the array to find the key. <BR>
     *                      The algorithm runs in O(log(n)) time, worst case <BR>
     * </P>
     * <!--Parameters:-->
     * @param ArrayList&ltString&gt - A concordance table that contains all the
     *                                asterisk words used for constructing the
     *                                concordances of a line in the original text.
     * @param String key - Value being searched for in the array list.
     **********************************************************************
     */
    public int binSearch(ArrayList<String> concordTable, String key) {
        int low = 0;
        int high = concordTable.size() - 1;
        boolean found = false;
        int index = -1;

        while (high >= low && !found) {
            int mid = (high + low) / 2;

            if (key.compareTo(concordTable.get(mid)) == 0) {
                found = true;
                index = mid;
            }
            if (key.compareTo(concordTable.get(mid)) < 0)
                high = mid - 1;
            if (key.compareTo(concordTable.get(mid)) > 0)
                low = mid + 1;
        }

        return index;

    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toString <BR>
     * <B>Description:</B>  This method returns the original text <BR>
     *                      after the algorithm has completed reconstruction. <BR>
     *                      from the texts concordance list. <BR>
     * </P>
     * <!--Parameters:-->
     * @param String - The original text.
     **********************************************************************
     */
    @Override
    public String toString() {
        return text;
    }
}
