/***************************************************************************
 * <B>Login ID:</B> howa1643 <BR>
 * CS-102, Fall 2008 <BR>
 * <B>Programming Assignment 2</B> <BR>
 * <B>Node class:</B> This class defines a node to be used in a linked list. <BR>
 * The node contains the data type to be stored, a key for sorting each node, <BR>
 * and a pointer to the next node in a linked list. <BR>
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
     *  
     *  String:
     *  key - A string used to search and sort the nodes in a linked list.         
     ************************************************************************
     */
    T element;
    Node<T> next;
    String key;
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> Node <BR>                                              
     * <B>Description:</B>  A zero argument constructor to create a Node <BR> 
     *                      object with the element, next pointer, and key <BR>
     *                      initialized to zero. <BR>   
     * </P>                                                    
     **********************************************************************
     */
    public Node() {
        element = null;
        next = null;
        key = null;
    }
    
    /**
     ************************************************************************
     * <P align="left">
     * <B>Method:</B> Node <BR>                                              
     * <B>Description:</B>  A one argument constructor to create a Node <BR> 
     *                      with an initialized element value. The next <BR>
     *                      pointer and the key are null values.<BR>   
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
     ************************************************************************
     * <P align="left">
     * <B>Method:</B> Node <BR>                                              
     * <B>Description:</B>  A two argument constructor to create a Node <BR> 
     *                      with an initialized element value and an initialized.
     *                      key value to sort the node with. The next pointer <BR>
     *                      is initialized to null. <BR>   
     * </P>
     * 
     * <!--Paramters: -->
     *  @param element data of generic type to be stored in the node.
     *  @param key a string value used to sort the node in the linked list.                                                   
     ************************************************************************
     */
    public Node(T element, String key) {
        this(element);
        this.key = key;
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
     * <B>Method:</B> setKey <BR>                                            
     * <B>Description:</B> Mutator method that sets the key to sort a <BR>
     *                     node by. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param key a string value representing the key the node is sorted by.                                             
     **********************************************************************
     */
    public void setKey(String key) {
        this.key = key;
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
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getKey <BR>                                            
     * <B>Description:</B> Accessor method that returns the key used to sort <BR>
     *                     each node. <BR>
     * </P>                                            
     * <!--Returns: -->                                                                                                        
     *  @return key - a string value representing the key the node is sorted by.                                             
     **********************************************************************
     */
    public String getKey() {
        return key;
    }
}
