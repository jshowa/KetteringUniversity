
package lab0;
import java.util.*;

/****************************************************************************
 * <B>Login ID:</B> howa1643 <BR>
 * CS 102, Fall 2008 <BR>
 * <B>Programming Assignment 1</B> <BR>
 * <B>ArrayIterator class:</B> Creates an iterator that iterates through an
 * array of elements.
 * <!--Parameters: -->
 *  @param T generic data type
 * @author Jacob Howarth
 * @version 1.0
 */
public class ArrayIterator<T> implements Iterator<T> {
    
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     * Generic type T:                                                       
     * items - An array of generic type that holds elements of int size.
     * Integer:
     * current - represents the current element the iterator is pointing to
     *           in the array (ie. iterator counter).
     * size - represents the true size of the array at a given point in time.         
     ************************************************************************
     */
    private T[] items;
    private int current;
    private int size;
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> ArrayIterator <BR>                                              
     * <B>Description:</B>  A two argument constructor to create an  <BR> 
     *                      ArrayIterator instance with an array of elements <BR>
     *                      and the current array size. <BR>                        
     * </P>
     * <!--Parameters: -->                                                                                                     
     *      @param items An array of a generic data type.                                               
     *      @param size The current size of the array.                                                    
     **********************************************************************
     */
    public ArrayIterator(T[] items, int size) {
        this.items = items;
        this.size = size;
        current = 0;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> hasNext <BR>                                              
     * <B>Description:</B>  A method that returns the following:  <BR> 
     *                      <DL>                                           
     *                       <DT> <B><I>true</I></B> <DD> - if there is another
     *                       element to iterate through.            
     *                       <DT> <B><I>false</I></B> <DD> - if there are no
     *                       other elements to iterate through and the end
     *                       of the list is reached.   
     *                     </DL>                                     
     * </P>
     * <!--Returns: -->                                                                                                     
     *      @return boolean - true or false                                                                                                   
     **********************************************************************
     */
    public boolean hasNext() {
        return (current < size);
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> next <BR>                                              
     * <B>Description:</B>  A method that returns the element before  <BR> 
     *                      iteration and increments <I>current</I>. <BR>                        
     * </P>
     * <!--Returns: -->                                                                                                     
     *      @return T - element of generic data type.                                                                                                    
     **********************************************************************
     */
    public T next() throws NoSuchElementException {  
        if (!hasNext())
            throw new NoSuchElementException(); // throw exception if the
                                        // iterator has completed iteratoring
                                        // through the array.
        current++; // iterator the counter and return previous element before
                   // iteration.
        return (items[current - 1]);
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> nextOffset <BR>                                              
     * <B>Description:</B>  A special method that returns the element without <BR>
     *                      incrementing <I>current</I>. This allows the iterator <BR>
     *                      to be used for searching for a paticular element <BR>                        
     *                      because it returns the element before the <BR>
     *                      incrementing of current which occured in the <BR> 
     *                      <I>next()</I> method call. <BR>
     * </P>
     * <!--Returns: -->                                                                                                     
     *      @return T - element of generic data type.                                                                                                    
     **********************************************************************
     */
    public T nextOffset() {
        return (items[current - 1]);
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
        throw new UnsupportedOperationException();
    }
    
}
