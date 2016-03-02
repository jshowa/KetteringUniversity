
package lab1;
import java.util.Iterator;
import java.util.NoSuchElementException;

/***************************************************************************
 * <B>Login ID:</B> howa1643 <BR>
 * CS-102, Fall 2008 <BR>
 * <B>Programming Assignment 2</B> <BR>
 * <B>LinkedListIterator class:</B> This class defines a linked list iterator <BR>
 * used to iterate through and return each node in the linked list data structure. <BR>
 * <!--Parameters: -->
 * @param T generic data type
 * @author Jacob Howarth 
 * @version 1.0 
 ***************************************************************************
 */
public class LinkedListIterator<T> implements Iterator<T> {
    
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     *  Node:
     *  current - Represents the current node being pointed to in the linked list
     *            structure.
     *  
     *  Integers:
     *  size - An integer representing the current size of the list.         
     ************************************************************************
     */
    private int size;
    private Node<T> current;
    
    /**
     ************************************************************************
     * <P align="left">
     * <B>Method:</B> LinkedListIterator <BR>                                              
     * <B>Description:</B>  A two argument constructor to create a linked list <BR> 
     *                      with a given list size and a pointer to the first <BR>
     *                      element in the list, usually the head. <BR>
     * </P>
     * <!--Paramters: -->
     *  @param size An integer representing the current size of the linked list.
     *  @param current a Node of generic type used as a pointer to the first <BR>
     *                 element in the linked list.                                   
     ************************************************************************
     */
    public LinkedListIterator(int size, Node<T> current) {
        this.size = size;
        this.current = current;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> hasNext <BR>                                              
     * <B>Description:</B>  A method that returns the following:  <BR> 
     *                      <DL>                                           
     *                       <DT> <B><I>true</I></B> <DD> - if there is another
     *                       node to iterate through.            
     *                       <DT> <B><I>false</I></B> <DD> - if there are no
     *                       other Nodes to iterate through and the current
     *                       Node is null.   
     *                     </DL>                                     
     * </P>
     * <!--Returns: -->                                                                                                     
     *      @return boolean - true or false                                                                                                   
     **********************************************************************
     */
    public boolean hasNext() {
        return (current != null);
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> next <BR>                                              
     * <B>Description:</B>  A method that returns the element in the Node <BR>
     *                      pointed to by current and then iterates current <BR>
     *                      to the next Node, if available. <BR>                      
     * </P>
     * <!--Returns: -->                                                                                                     
     *      @return T - element of generic data type.                                                                                                    
     **********************************************************************
     */
    public T next() {
        
        if (!hasNext()) // if there aren't any Nodes in the list, throw this 
                        // exception.
            throw new NoSuchElementException();
        
        T result = current.getElement(); // return the element stored in the 
                                        // Node current is pointing too.
        current = current.getNext(); // get the next Node in the list and set
                                    // current to point to it.
        return result;
        
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> nextKey <BR>                                              
     * <B>Description:</B>  A method that returns the key the cu Node is sorted<BR>
     *                      by in the linked list then iterates current <BR>
     *                      to the next Node, if available. <BR>                      
     * </P>
     * <!--Returns: -->                                                                                                     
     *      @return key - a string value representing what the node is sorted by.                                                                                                    
     **********************************************************************
     */
    public String nextKey() {
        if (!hasNext()) // if there are no more Nodes, throw exception.
            throw new NoSuchElementException();
        
        String key = current.getKey(); // return the key stored in the Node
                                        // current is pointing too.
        current = current.getNext(); // advance the current Node to point to
                                    // the next Node in the list.
        
        return key;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> remove <BR>                                              
     * <B>Description:</B>  A method belonging to the Iterator interface <BR> 
     *                      that must be implemented in this class. However <BR>                        
     *                      it is not used and therefore throws an Unsupported <BR>
     *                      Operation exception. <BR>
     * </P>
     * @throws java.lang.UnsupportedOperationException                                                                                                     
     **********************************************************************
     */
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This operation of iterator " +
                "remove is not supported yet.");
    }
    
    

}
