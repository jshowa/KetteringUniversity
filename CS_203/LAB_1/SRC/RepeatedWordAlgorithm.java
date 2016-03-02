/***************************************************************************
 * <B>Name:</B> Jacob Howarth <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 1</B> <BR>
 * <B>RepeatedWordAlgorithm class:</B> This class defines the algorithm to be <BR>
 * used to determine the most repeated words in a text file and the number <BR>
 * of their occurences. This algorithm has worst case time complexity of <BR>
 * O(n^2). The class also returns the three most repeated elements. Each of <BR>
 * these comparisons takes O(n) time for a worst case n comparisons per loop <BR>
 * times 3 loops. <BR>
 * @author Jacob Howarth
 * @version 1.0
 ***************************************************************************
 */
public class RepeatedWordAlgorithm {

    /* --VARIABLE DECLARATIONS--
     * LinkedList<WordCountNode<String>> list2 - A list representing the words 
     *                                           in the document and the number
     *                                           of repetitions of each word.
     */
    private LinkedList<WordCountNode<String>> list2;
    /**
     **********************************************************************************
     * <P align="left">
     * <B>Method:</B> RepeatedWordAlgorithm <BR>
     * <B>Description:</B>  This method executes the repeated word algorithm.<BR>
     *                      by taking the first element in the linked list of <BR>
     *                      words from the file and comparing it to all the words <BR>
     *                      in that linked list. If there is a match, the word <BR>
     *                      count is increased for that word and the words repeat <BR>
     *                      is removed from the list. This algorithm runs in worst <BR>
     *                      case O(n^2) time.
     * </P>
     *
     * <!--Paramters: -->
     *  @param list1 A LinkedList containing a list of words from the text file
     *               input.
     *  @param list2 A LinkedList containing the list of words from the original
     *               file and their repeated word counts, if they were repeated
     *               in the original text file.
     **********************************************************************************
     */
    public RepeatedWordAlgorithm(LinkedList<String> list1, LinkedList<WordCountNode<String>> list2) {
        int i = 0, j = 0; // j - incrementer for the original list (list1) in the while loop
                          // i - marker for the first element in the original list (list1)

        this.list2 = list2; // make a shallow copy of local parameter list2 to the
                            // global variable list2 in this class. Now the global
                            // variable points to the local variable list2.
		  
		  try {
        		// Step 1: Take the first element in the original list containing
                // the words from the file (list1) and add it to list2
                list2.addLast(new WordCountNode<String>(list1.get(i)));


		  		while (j <= list1.size() && list1.size() != 0) {
        			
                    // Step 2: If the end of the list is reached, get the first
                    // element again from the original list (list1) and store it
                    // in list2 and reset the iterator counter for list1
                    if (j == list1.size()) {
                        list2.addLast(new WordCountNode<String>(list1.get(i)));
                        j = 0;
                    }
                    // Step 3: If the end of the list is NOT reached, compare the
                    // last element in list2 with the element at position j of the
                    // original list (list1). If they are equal, increment the word count
                    // for that element and remove it from the original list (list1)
                    if (list2.getLast().getElement().equalsIgnoreCase(list1.get(j))) {
                        list2.getLast().incrementCount();
                        list1.remove(j);
                    }
                    // Step 4: If the last element in list2 is NOT equal to the
                    // the element at position j in the original list (list1) then
                    // increment j and check it with the next word.
                    else
                        j++;
                }
			}
			catch (IndexOutOfBoundsException ex) { // LinkedList throws IndexOutOfBoundsExceptions from
                                                    // the get() method, so make sure to catch this exception
                                                    // just in case.
				System.out.println(ex.toString() + ": An error occured in reading the file.");
				System.exit(1);
			}	
    }

    /**
     **********************************************************************************
     * <P align="left">
     * <B>Method:</B> getWordCountList <BR>
     * <B>Description:</B> A accessor method used to return the list containing
     *                     the words from the file and their word counts.
     * </P>
     *
     * <!--Returns: -->
     * @return LinkedList&ltWordCountNode&ltString&gt&gt list2
     *         The list of words from the file with their associated word counts
     **********************************************************************************
     */
    public LinkedList<WordCountNode<String>> getWordCountList() {
        return this.list2;
    }

    /**
     **********************************************************************************
     * <P align="left">
     * <B>Method:</B> threeMostRepeated <BR>
     * <B>Description:</B> A method that returns the top three most repeated words <BR>
     *                     in the input text file based on the first occurence of <BR>
     *                     each word in the file.
     * </P>
     *
     * <!--Returns: -->
     * @return String A string containing the three most repeated words in the
     *                text file based on the first occurence of each word in the
     *                file.
     **********************************************************************************
     */
    public String threeMostRepeated() {

        /* -- VARIABLE DECLARATION --
         * int[] threeMostRepeated - An integer array of length three that will
         *                           store the index of the first, second, and third
         *                           largest word counts.
         * String result - The string that will contain largest, second largest, and
         *                 third largest repeated word and their associated counts.
         */
        int[] threeMostRepeated = {0, 0, 0};
        String result = "";

        if (list2 != null) { // don't do anything if the list is empty


            // Set the maximum word count to be the count of the first element
            // in the list
            int maxWordAppear = list2.get(0).getCount();

            for (int i = 0; i < list2.size(); i++)
                // Iterate through the list, and if their is a word count
                // thats bigger than the first elements word count, set the max
                // to be that new word count and store the index of that word
                // in the array.
                if (maxWordAppear < list2.get(i).getCount()) {
                    maxWordAppear = list2.get(i).getCount();
                    threeMostRepeated[0] = i; // store index of the word with the largest
                                            // word count in the first position of the array
                }

            // Add the word with the largest repeated word count to the return string
            result += list2.get(threeMostRepeated[0]).toString() + "\n";

            // Reset the word count of the word with the largest repeated word counts to
            // zero so it won't be picked up again in the next pass of the list to
            // find the word with the second largest repeated word count. Zero
            // Is the smallest possible value since that means that the word was
            // never in the original input file
            list2.get(threeMostRepeated[0]).resetCount();

            // Reset the maximum and repeated the above to find the word with
            // the second largest repeated word count...
            maxWordAppear = list2.get(0).getCount();

            for (int i = 0; i < list2.size(); i++)
                if (maxWordAppear < list2.get(i).getCount()) {
                    maxWordAppear = list2.get(i).getCount();
                    threeMostRepeated[1] = i;
                }

            result += list2.get(threeMostRepeated[1]).toString() + "\n";
            list2.get(threeMostRepeated[1]).resetCount();

            // Repeat the above again to find the word with the third largest
            // repeated word count...
            maxWordAppear = list2.get(0).getCount();

            for (int i = 0; i < list2.size(); i++)
                if (maxWordAppear < list2.get(i).getCount()) {
                    maxWordAppear = list2.get(i).getCount();
                    threeMostRepeated[2] = i;
                }
        
            result += list2.get(threeMostRepeated[2]).toString() + "\n";
            
        }

        // Return the concatenated string with the largest, second largest,
        // and third largest repeated words and their associated counts.
        return result;
    }

}
