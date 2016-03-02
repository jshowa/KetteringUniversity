/***************************************************************************
 * <B>Name:</B> Jacob Howarth <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 1</B> <BR>
 * <B>WordCountNode class:</B> This class defines a node to be used in a linked list. <BR>
 * The node contains the data type to be stored and a pointer to the next <BR>
 * node in a linked list. It also conatins a word count associated with the <BR>
 * element. <BR>
 * <!--Parameters: -->
 * @param T generic data type
 * @author Jacob Howarth
 * @version 1.0
 ***************************************************************************
 */

public class WordCountNode<T> extends Node<T> {

    /**
     * VARIABLE DECLARATION: <BR>
     * int count - A count variable used to count the occurences of the element. <BR>
     */
    private int count;

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> WordCountNode <BR>
     * <B>Description:</B>  A zero argument constructor to create a <BR>
     *                      WordCountNode object with the element and next <BR>
     *                      pointer initialized to null. <BR>
     *
     * </P>
     **********************************************************************
     */
    public WordCountNode() {
        super.element = null;
        super.next = null;
        count = 0;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> WordCountNode <BR>
     * <B>Description:</B>  A two argument constructor to create a <BR>
     *                      WordCountNode object with a specific element <BR>
     *                      and count initialized to zero.
     *
     * </P>
     * <!--Paramters: -->
     * @param element data of generic type to be stored in the node.
     **********************************************************************
     */
    public WordCountNode(T element) {
        super.element = element;
        super.next = null;
        count = 0;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getCount <BR>
     * <B>Description:</B>  An accessor method used to return the value of <BR>
     *                      count.
     *
     * </P>
     * <!--Returns: -->
     *  @return count - An integer representing the number of the objects
     *                  occurence.
     **********************************************************************
     */
    public int getCount() {
        return count;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> incrementCount <BR>
     * <B>Description:</B>  A mutator method used to increment occurence <BR>
     *                      count by 1.
     * </P>
     **********************************************************************
     */
    public void incrementCount() {
        this.count++;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> resetCount <BR>
     * <B>Description:</B>  A mutator method that resets the occurence <BR>
     *                      counter to 0.
     * </P>
     **********************************************************************
     */
    public void resetCount() {
        this.count = 0;
    }


    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toString <BR>
     * <B>Description:</B> Returns a string representing the nodes element <BR>
     *                     and the number of occurences of that element.
     * </P>
     **********************************************************************
     */
    @Override
    public String toString() {
        return (this.getElement() + "=" + getCount());
    }
}
