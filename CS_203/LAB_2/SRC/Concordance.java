/***************************************************************************
 * <B>Class:</B> Concordance <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 2</B> <BR>
 * <B>Description:</B> This class defines a five word concordance object. <BR>
 * A concordance is a piece of an original text in where a word is chosen from <BR>
 * that text along with words surrounding that word. These concordances are <BR>
 * used to reconstruct an original piece of text. All methods in this class <BR>
 * are O(1) except for toString() which is O(n). <BR>
 * @author Jacob Howarth
 * @version 1.0
 ***************************************************************************
 */
public class Concordance {

    /* -- VARIABLE DECLARATIONS --
     * Integers:
     * int indexOfConWord - The index at which the concordance word (i.e. the
     *                      word with the asterisk) resides in the concordance
     *                      objects String array.
     * int size - The current number of words in the concordance.
     * 
     * String:
     * String[] concordWords - An array of size 5 that holds each word of
     *                         the concordance.
     * 
     * Constants:
     * int MAX_CONCORD_LEN - The maximum number of words a concordance can
     *                       have, which is 5.
     */
    public static final int MAX_CONCORD_LEN = 5;
    private int indexOfConWord;
    private String[] concordWords;
    private int size;

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> Concordance <BR>
     * <B>Description:</B>  A zero argument constructor to create a concordance <BR>
     *                      of size 0 and an array of null Strings used to store <BR>
     *                      each word of the concordance. <BR>
     * </P>
     **********************************************************************
     */
    public Concordance() {
        size = 0;
        concordWords = new String[MAX_CONCORD_LEN];
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> addWord <BR>
     * <B>Description:</B>  This method adds a String to the concordance. <BR>
     *                      String array. <BR>
     * </P>
     * <!--Parameters:-->
     * @param String word A new word to be added to the concordance.
     **********************************************************************
     */
    void addWord(String word) {
        if (size < 5) {
            concordWords[size] = word;
            size++;
        }
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> addWord <BR>
     * <B>Description:</B>  This method adds a String to the concordance. <BR>
     *                      String array. <BR>
     * </P>
     * <!--Parameters:-->
     * @param String word A new word to be added to the concordance.
     * @throws IndexOutOfBoundsException
     **********************************************************************
     */
    public String getWord(int index) throws IndexOutOfBoundsException {
        if (index >= 5 || index < 0)
            throw new IndexOutOfBoundsException("Error: Accessed Array Out of Bounds");

        return (concordWords[index]);
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getPrevWord <BR>
     * <B>Description:</B>  This method returns the previous word given. <BR>
     *                      a current words index. <BR>
     * </P>
     * <!--Parameters:-->
     * @param int index - An integer representing a word in the concordance.
     * <!--Returns:-->
     * @return String the word previous to the word in the position of
     *         the arguments index.
     * @throws IndexOutOfBoundsException
     **********************************************************************
     */
    public String getPrevWord(int index) {
        if (index >= 5 || index < 0)
            throw new IndexOutOfBoundsException("Error: Accessed Array Out of Bounds");
        return (concordWords[index - 1]);
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getIndexOfConcordWord <BR>
     * <B>Description:</B>  This method returns the index of the word <BR>
     *                      that generated the concordance (i.e. the word <BR>
     *                      containing the asterisk). <BR>
     * </P>
     * <!--Returns:-->
     * @return int the index of the concordance word in the String array
     *             (i.e. the word with the asterisk).
     **********************************************************************
     */
    public int getIndexOfConcordWord() {
        return indexOfConWord;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getConcordWord <BR>
     * <B>Description:</B>  This method returns the word that generated the <BR>
     *                      concordance (i.e. the word containing the asterisk). <BR>
     * </P>
     * <!--Returns:-->
     * @return String the concordance word that generated the concordance
     *                (i.e. word containing the asterisk).
     **********************************************************************
     */
    public String getConcordWord() {
        return concordWords[indexOfConWord];
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> setIndexOfConcordWord <BR>
     * <B>Description:</B>  This method sets the index of the word <BR>
     *                      that generated the concordance (i.e. the word <BR>
     *                      containing the asterisk). It sets the position <BR>
     *                      of the concordance word relative to the other <BR>
     *                      words in the concordance String array. <BR>
     * </P>
     * <!--Parameters:-->
     * @param int the index of the concordance word in the String array
     *             (i.e. the word with the asterisk).
     **********************************************************************
     */
    public void setIndexOfConcordWord(int index) {
        this.indexOfConWord = index;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> size <BR>
     * <B>Description:</B>  Returns the current size of the concordance (i.e. the <BR>
     *                      number of words in the concordance). <BR>
     * </P>
     * <!--Returns:-->
     * @return int The current number of words in the concordance.
     **********************************************************************
     */
    public int size() {
        return size;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toString <BR>
     * <B>Description:</B>  Returns a String containing the words from the <BR>
     *                      concordance String array after the specified index. <BR>
     * </P>
     * <!--Parameters-->
     * @param int printIndex - The position at which to start printing at
     *                         in the concordance String array.
     * <!--Returns:-->
     * @return String The words from the concordance String array after the given
     *                index.
     * @throws IndexOutOfBoundsException
     **********************************************************************
     */
    public String toString(int printIndex) throws IndexOutOfBoundsException {

        String result = "";

        if (printIndex < 0 || printIndex > size())
            throw new IndexOutOfBoundsException("Error: Accessed array out of bounds.");

        for (int i = printIndex; i < concordWords.length; i ++) {
            // if the word in the concordance is the word with
            // the asterisk, remove it before printing.
            if (concordWords[i].matches("\\*[\\w]+")) {
                result += concordWords[i].replace('*', ' ').trim() + " ";
            }
            else
                result += concordWords[i] + " ";
        }

        return result;
    }
}
