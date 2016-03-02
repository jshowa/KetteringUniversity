/***************************************************************************
 * <B>Login ID:</B> howa1643 <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 1</B> <BR>
 * <B>Node class:</B> This class defines a node to be used in a linked list. <BR>
 * The node contains the data type to be stored and a pointer to the next <BR>
 * node in a linked list.<BR>
 * <!--Parameters: -->
 * @param T generic data type
 * @author Jacob Howarth 
 * @version 1.0 
 ***************************************************************************
 */
public class Node<T> {
    
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     *  Node:
     *  element - A generic type representing the data stored in the node.
     *  next - Pointer to the next node.        
     ************************************************************************
     */
    protected T element;
    protected Node<T> next;
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> Node <BR>                                              
     * <B>Description:</B>  A zero argument constructor to create a Node <BR> 
     *                      object with the element and next pointer <BR>
     *                      initialized to zero. <BR>   
     * </P>                                                    
     **********************************************************************
     */
    public Node() {
        element = null;
        next = null;
    }
    
    /**
     ************************************************************************
     * <P align="left">
     * <B>Method:</B> Node <BR>                                              
     * <B>Description:</B>  A one argument constructor to create a Node <BR> 
     *                      with an initialized element value. The next <BR>
     *                      pointer is set to null.<BR>
     * </P>
     * 
     * <!--Paramters: -->
     *  @param element data of generic type to be stored in the node.                                                    
     ************************************************************************
     */
    public Node(T element) {
        this.element = element;
        next = null;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> setNext <BR>                                            
     * <B>Description:</B> Mutator method that sets the next pointer of a node 
     *                     object. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param next a pointer to the next node in the list of generic type.                                             
     **********************************************************************
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> setElement <BR>                                            
     * <B>Description:</B> Mutator method that sets the data value the node. <BR>
     *                     will store. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param element data of a generic type to be stored in the node.                                             
     **********************************************************************
     */
    public void setElement(T element) {
        this.element = element;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getElement <BR>                                            
     * <B>Description:</B> Accessor method that returns the generic element <BR>
     *                     stored in the node. <BR>
     * </P>                                            
     * <!--Returns: -->                                                                                                        
     *  @return element - A generic data type.                                             
     **********************************************************************
     */
    public T getElement() {
        return element;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getNext <BR>                                            
     * <B>Description:</B> Accessor method that returns a pointer to the <BR>
     *                     next node in a linked list. <BR>
     * </P>                                            
     * <!--Returns: -->                                                                                                        
     *  @return next - a pointer to the next node in the list of generic type.                                             
     **********************************************************************
     */
    public Node<T> getNext() {
        return next;
    }
   
}
