
package lab1;
import lab0.Airport;
import lab0.List;

/***************************************************************************
 * <B>Login ID:</B> howa1643 <BR>
 * CS-102, Fall 2008 <BR>
 * <B>Programming Assignment 2</B> <BR>
 * <B>LinkedList class:</B> This class defines a linked list data structure. <BR>
 * A linked list data structure consists of nodes that hold data and pointers
 * to the next node in the list. These lists are commonly used when memory must
 * be managed dynamically.<BR>
 * <!--Parameters: -->
 * @param T generic data type
 * @author Jacob Howarth 
 * @version 1.0 
 ***************************************************************************
 */
public class LinkedList<T> implements List<T> {
    
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     *  Node:
     *  head - Points to the first element in the list.
     *  
     *  Integers:
     *  size - An integer representing the current size of the list.         
     ************************************************************************
     */
    Node<T> head;
    int size;
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> LinkedList <BR>                                              
     * <B>Description:</B>  A zero argument constructor to create an empty <BR>
     *                      linked list. <BR>   
     * </P>                                                    
     **********************************************************************
     */
    public LinkedList() {
        head = null;
        size = 0;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> size <BR>                                              
     * <B>Description:</B>  Returns the current number of Nodes in the <BR>
     *                      linked list. <BR>   
     * </P>
     * <!--Returns: -->
     * @return size - Integer representing the number of nodes in the linked list.                                                    
     **********************************************************************
     */
    public int size() {
        return size;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> isEmpty <BR>                                            
     * <B>Description:</B> A method that returns a boolean value <BR>      
     *                     representing the following: <BR>
     *                     <DL>                                           
     *                       <DT> <B><I>true</I></B> <DD> - if the linked list
     *                       contains no elements.            
     *                       <DT> <B><I>false</I></B> <DD> - if the linked list 
     *                       has one or more elements.   
     *                     </DL>
     * </P>                                            
     * <!--Returns: -->                                                                                                        
     *  @return boolean - true or false                                             
     **********************************************************************
     */
    public boolean isEmpty() {
        return (head == null);
    }
    
     /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> add <BR>                                            
     * <B>Description:</B> A method that adds a Node holding a data value of <BR>
     *                     generic type, a key to sort the Node by in the list, <BR> 
      *                    and a pointer to the next Node in the list. <BR>
     *                     <BR>
     *                     An IndexOutOfBoundsException is thrown if a Node is <BR>
     *                     added to a negative index or an index greater size. <BR>
     *                     <BR>
     *                     The key string parameter is stored in the Node <BR>
     *                     and is used to relatively sort the Nodes as they <BR>
     *                     are added to the list. <BR>             
     * </P>                                            
     * <!--Parameters: -->
     *  @param element An element of generic data type to be added to the 
     *                 linked list.                                                                                                        
     *  @param index Integer representing the position the element will be
     *               be added to in the linked list. 
     *  @param key A string value used to sort the Nodes in the list.                                             
     **********************************************************************
     */
    public void add(T element, int index, String key) {
        if (index < 0)
            throw new IndexOutOfBoundsException();
        
        Node<T> current = head;
        Node<T> previous = null;
        
         /* This loop goes through the list in reverse order, moving
         * the index while moving the current node pointer. This is done
         * so the whole list doesn't have to be iterated through.
         */
        for (; current != null && index > 0; index--) {
            previous = current; // set the previous node to the current node
                                // before moving current.
            current = current.getNext();
        }
        
        if (index != 0) // if the index isn't zero, this states that an addition
                        // was made outside the current list size.
            throw new IndexOutOfBoundsException();
        
        Node<T> addition = new Node(element, key);
        addition.setNext(current);
        
        if (previous == null) // if the list was empty, the head node is
            head = addition; // is the new head node.
        else
            previous.setNext(addition);
        
        size++; // increase size
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> remove <BR>                                            
     * <B>Description:</B> A method that removes a Node from the <BR>
     *                     linked list at the location specified by the index <BR>
     *                     parameter. <BR>
     *                     <BR>
     *                     An IndexOutOfBoundsException is thrown if the index <BR>
     *                     of removal is negative or greater than the current <BR>
     *                     list size. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param index Integer representing the position of the Node being
     *               removed.                                             
     **********************************************************************
     */
    public void remove(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        
        Node<T> current = head; // start at the head of the list
        Node<T> previous = null;
        
        for (int pos = index; current != null && index > 0; index--) {
            previous = current; // iterate through the list to the position
            current = current.getNext(); // of removal.
        }
        
        if (current == null)
            throw new IndexOutOfBoundsException();
        
        if (previous == null && size == 1)
            head = previous; // if the list with one node is the node of removal
        else if (previous == null && size > 1) {
            current = current.getNext(); // use this code if the index of
            head = current;              // removal is at the head to prevent
                                        // deletion of an entire list.
        }
        else {
            previous.setNext(current.getNext());
        }
        size--; // decrease the size
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> get <BR>                                            
     * <B>Description:</B> A method that returns the first instance of an <BR>
     *                     element in the linked list. The iterating is done <BR>
     *                     in reverse order for efficiency. <BR>
     *                     <BR>
     *                     An IndexOutOfBoundsException is thrown if the index <BR> 
     *                     is negative or greater than the size and if the <BR>
     *                     the current node pointer is null. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param index Integer representing the position of the element.
     * <!--Returns: -->
     *  @return T - element of generic type.                                               
     **********************************************************************
     */
    public T get(int index) {
        Node<T> current = head; // start iterating at the head of the linked list.
        
        for(; current != null && index > 0; index--)
            current = current.getNext(); // iterate through the linked list
                                        // stopping at the index of the element
                                        // being retrieved.
        if (current == null || index != 0)
            throw new IndexOutOfBoundsException();
        
        return current.getElement(); // return the element at the specified index. 
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> indexOf <BR>                                            
     * <B>Description:</B> A method that returns the index of the first <BR> 
     *                     instance of an element in the linked list. <BR>
     *                     If the element is <I>not found</I> the method <BR>
     *                     returns -1. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param element element of generic type being searched for.
     * <!--Returns: -->
     *  @return index - Integer representing the index of the element being
     *                  searched for if found. Returns -1 if not found.                                               
     **********************************************************************
     */
    public int indexOf(T element) {
        final int NOT_FOUND = -1; // return if the element is not in the list.
        int count = 0; // a counter to return the index of the element being 
                        // searched for.
        LinkedListIterator<T> iterator = iterator(); // create an iterator to
        T nodeElement;                               // iterate through each node.
        
        while (iterator.hasNext()) {
            nodeElement = iterator.next(); // grad the data element from the Node.
            if (nodeElement.equals(element)) // test if the elements are equal.
                return count;
            count++;
        }
        
        return NOT_FOUND;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> compareToKey <BR>                                            
     * <B>Description:</B> A method that returns an integer value representing <BR>      
     *                     the appropriate index to add a Node in the linked list <BR>
     *                     based off a lexographical (alphabetical) comparison <BR>
     *                     between the key argument and the Nodes key. <BR>                    
     * </P>
     * <!--Parameters: -->
     *  @param key A string value representing the key to be compared too.                                            
     * <!--Returns: -->                                                                                                        
     *  @return index - An integer value representing the appropriate
     *                         index to add to in the linked list, in
     *                         alphabetical order based on the key value in 
     *                         the Node                                           
     **********************************************************************
     */
    public int compareToKey(String key) { 
        LinkedListIterator<T> iterator = iterator();
        boolean continueLoop = true;
        int index = 0;
       
        while (iterator.hasNext() && continueLoop) { // iterate through each Node in the list.
            String nodeKey = iterator.nextKey(); // this iterator iterates 
                                              // through the keys in each Node
                                            // and compares them alphabetically.
            if(key.compareTo(nodeKey) > 0)
                index++; // increment the index counter if the key 
                                // parameter is lexographically greater
                                // than the Node key. This ensures addition
                                // by alphabetical order.
            else if(key.compareTo(nodeKey) <= 0)
                continueLoop = false; // stop the loop when you've reached a 
                                    // a Node key value that is greater than
                                    // the key parameter.
            
        }
        return index;
           
    }
    
     /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> iterator <BR>                                            
     * <B>Description:</B> A method that returns a linked list iterator instance <BR>
     *                     which can be used to iterate through each node <BR>
     *                     currently in the linked list. <BR>
     * </P>                                            
     * <!--Returns: -->
     *  @return LinkedListIterator&lt;T&gt; - iterator that iterates through 
     *                                   the nodes in the list.                                               
     **********************************************************************
     */
    public LinkedListIterator<T> iterator() {
        return (new LinkedListIterator<T>(size, head));
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> search <BR>                                            
     * <B>Description:</B> A method that returns multiple instances of <BR> 
     *                     an element in the linked list if there is more <BR>
     *                     than one or only one. A search parameter of <BR>
     *                     Object type can be used. The method will return <BR>
     *                     a null string if not found. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param element element of Object type being searched for.
     * <!--Returns: -->
     *  @return result - String representing the instance(s) of the element as
     *                   returned by the toString() method inherited by
     *                   each elements class.                                                
     **********************************************************************
     */
    public String search(Object element) {
        LinkedListIterator<T> iterator = iterator();
        String result = "";
        
        /* This method compares the element of Object type to see if it's an
         * instance of Airport or an instance of String. This allows for search
         * parameters of different types to be used and explains the long
         * if condition expression.
         */
        while (iterator.hasNext()) { // while another element exists, continue
                                    //iterating.
            T valueFromList = iterator.next(); // store the current iteration
                                                // temporarily.
            if((element instanceof Airport && (valueFromList).equals(element))
                || (element instanceof String && 
                (valueFromList).equals((String)(element))))
                
                result += (valueFromList).toString() + "\n";
                /* If a match is found after calling the equals method of the
                 * Airport class, the Airport object is converted to a
                 * String and concatenated to result. This allows a return
                 * of a list of multiple Airport objects.
                 */
        }
        
        return result;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toString <BR>                                                   
     * <B>Description:</B> A method that prints the data in the linked list <BR>
     * </P>                                                                              
     * <!--Returns: -->                                                    
     *  @return result - A string representing the data in the linked list.                                                     
     **********************************************************************
     */ 
    @Override
    public String toString() {
        LinkedListIterator<T> iterator = iterator();
        String result = "";
        
        while (iterator.hasNext())
            result += iterator.next() + "\n";
        
        return result;
    }
    
}
