/***************************************************************************
 * <B>Class:</B> maxSum <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 3</B> <BR>
 * <B>Description:</B> This class defines a maxSum object <BR>
 * that holds the max sum of a consecutive sequence of numbers in an array <BR>
 * of length N. It also stores the range of where the sum can be found <BR>
 * in the array. <BR>
 * @author Jacob Howarth
 * @version 1.0
 ***************************************************************************
 */
public class maxSum {

    /******************VARIABLE TABLE********************
     * int leftBound - the left bound indice of the max
     *                 sum
     * int rightBound - the right bound indice of the
     *                  max sum
     * int sum - the max sum of a consecutive sequence in
     *           an array of N integers.
     ****************************************************
     */
    private int leftBound;
    private int rightBound;
    private int sum;

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> maxSum <BR>
     * <B>Description:</B>  A zero argument constructor to create a maxSum <BR>
     *                      object with the sum of zero. <BR>
     * </P>
     **********************************************************************
     */
    public maxSum() {
        this.sum = 0;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> maxSum <BR>
     * <B>Description:</B>  A constructor that creates a maxSum object with <BR>
     *                      an initialized sum value and a left bound and right <BR>
     *                      bound indice of where that sum is located. <BR>
     * </P>
     * <!--Parameters: -->
     * @param sum - The sum of a consecutive sequence of elements
     * @param leftBound
     * @param rightBound
     **********************************************************************
     */
    public maxSum(int sum, int leftBound, int rightBound) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.sum = sum;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getSum <BR>
     * <B>Description:</B>  Accessor method that returns the maximum sum <BR>
     *                      of a consecutive sequence of elements in an array <BR>
     *                      of size N. <BR>
     * </P>
     * <!--Returns: -->
     * @return integer representing the max sum of a consecutive sequence.
     **********************************************************************
     */
    public int getSum() {
        return sum;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getLeftBound <BR>
     * <B>Description:</B>  Accessor method that returns the left indice of <BR>
     *                      the range containing the max sum of a consecutive <BR>
     *                      sequence of elements in the array <BR>
     * </P>
     * <!--Parameters: -->
     * @returns integer representing the left bound of the range of the max
     *                  sum of a consecutive sequence.
     **********************************************************************
     */
    public int getLeftBound() {
        return leftBound;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getRightBound <BR>
     * <B>Description:</B>  Accessor method that returns the right indice of <BR>
     *                      the range containing the max sum of a consecutive <BR>
     *                      sequence of elements in the array <BR>
     * </P>
     * <!--Parameters: -->
     * @returns integer representing the right bound of the range of the max
     *                  sum of a consecutive sequence.
     **********************************************************************
     */
    public int getRightBound() {
        return rightBound;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> setSum <BR>
     * <B>Description:</B>  Mutator method that sets the sum of a consecutive <BR>
     *                      sequence of elements. <BR>
     * </P>
     * <!--Parameters: -->
     * @param sum - The sum of a consecutive sequence of elements
     **********************************************************************
     */
    public void setSum(int sum) {
        this.sum = sum;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> setLeftBound <BR>
     * <B>Description:</B>  Mutator method that sets the left bound on the <BR>
     *                      sequence of which the sum is being calculated. <BR>
     * </P>
     * <!--Parameters: -->
     * @param leftBound - the left indice or bound of the sequence range
     **********************************************************************
     */
    public void setLeftBound(int leftBound) {
        this.leftBound = leftBound;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> setRightBound <BR>
     * <B>Description:</B>  Mutator method that sets the right bound on the <BR>
     *                      sequence of which the sum is being calculated. <BR>
     * </P>
     * <!--Parameters: -->
     * @param rightBound - the right indice or bound of the sequence range
     **********************************************************************
     */
    public void setRightBound(int rightBound) {
        this.rightBound = rightBound;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toString <BR>
     * <B>Description:</B>  Prints the value of the max sum of a consecutive <BR>
     *                      sequence and the range it can be found in in an <BR>
     *                      array of size N. <BR>
     * </P>
     * <!--Returns:-->
     * @return String - A string representing the maximum sum and the range
     *                  in which it is found in an array of size N.
     **********************************************************************
     */
    @Override
    public String toString() {
        return "The max contiguous sum is " + sum + " in the interval " + leftBound +
                " to " + rightBound;
    }

}
