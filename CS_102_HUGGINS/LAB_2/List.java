
package lab0;

/***************************************************************************
 * <B>Login ID:</B> howa1643 <BR>
 * CS-102, Fall 2008 <BR>
 * <B>Programming Assignment 2</B> <BR>
 * <B>List Interface:</B> An interface containing a series of methods that are <BR>
 * commonly implemented in all list data structures. <BR>
 * <!--Parameters: -->
 *  @param T generic data type
 * @author Jacob Howarth 
 * @version 1.0 
 ***************************************************************************
 */
public interface List<T> {
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> size <BR>                                            
     * <B>Description:</B> A method that returns the actual size of <BR>      
     *                     a list at the current time, ie. only counts <BR>
     *                     the elements it currently contains. <BR>
     * </P>                                            
     * <!--Returns: -->                                                                                                        
     *  @return int - Represents the number of elements in the list.                                              
     **********************************************************************
     */
    int size();
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> isEmpty <BR>                                            
     * <B>Description:</B> A method that returns a boolean value <BR>      
     *                     representing the following: <BR>
     *                     <DL>                                           
     *                       <DT> <B><I>true</I></B> <DD> - if the list contains
     *                       no elements.            
     *                       <DT> <B><I>false</I></B> <DD> - if the list has
     *                       one or more elements.   
     *                     </DL>
     * </P>                                            
     * <!--Returns: -->                                                                                                        
     *  @return boolean - true or false                                             
     **********************************************************************
     */
    boolean isEmpty();
    
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> add <BR>                                            
     * <B>Description:</B> A method that adds an element of generic type <BR>      
     *                     at a specific position in the list data structure <BR>
     *                     represented by the index parameter. <BR>
     * 
     *                     The key value is a new feature that allows the user <BR>
     *                     to sort lists by a specific key value when adding <BR>
     *                     to the list. If the list does not need sorting <BR>
     *                     please pass a null reference as an argument <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param element data of generic type to be added at the index position.
     *  @param index integer representing the position in the list to add too.
     *  @param key a string value representing a sort premiss for the element.                                              
     **********************************************************************
     */
    void add(T element, int index, String key);
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> remove <BR>                                            
     * <B>Description:</B> A method that removes an element of generic type <BR>      
     *                     at a specific position in the list data structure <BR>
     *                     represented by the index parameter. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param index integer representing the index of the element being removed.                                              
     **********************************************************************
     */
    void remove(int index);
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> get <BR>                                            
     * <B>Description:</B> A method that returns an element of generic type <BR>      
     *                     at a specific position in the list data structure <BR>
     *                     represented by the index parameter. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param index integer representing the position of the element being
     *               retrieved.
     * <!--Returns: -->
     *  @return T - element of generic type                                               
     **********************************************************************
     */
    T get(int index);
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> indexOf <BR>                                            
     * <B>Description:</B> A method that returns the index of an element <BR>      
     *                     being searched for in the list data structure. <BR>
     *                     <BR>
     *                     If the element is <B>not</B> present, the method <BR>
     *                     returns -1. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param element data of a generic type that is being searched for.
     * <!--Returns: -->
     *  @return int - index at which the element is present in the list.                                               
     **********************************************************************
     */
    int indexOf(T element);
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> search <BR>                                            
     * <B>Description:</B> A method that searches for an element in the list <BR>      
     *                     data structure and returns that element, if found. <BR>
     *                     <BR>
     *                     If the element is <B>not</B> present, the method <BR>
     *                     returns a null String. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param element data of Object type that is being searched for.
     * <!--Returns: -->
     *  @return String - the element in the list (as a String), if present.                                               
     **********************************************************************
     */
    String search(Object element);
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toString <BR>                                                   
     * <B>Description:</B> A method that prints the data in the list data <BR>
     *                     structure in a specific format. <BR>
     *                     </P>                                                                              
     * <!--Returns: -->                                                    
     *  @return name - String                                                     
     **********************************************************************
     */ 
    @Override
    String toString();
    

}
