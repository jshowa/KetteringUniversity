/***************************************************************************
 * <B>Name:</B> Jacob Howarth <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 1</B> <BR>
 * <B>LinkedList class:</B> This class defines a linked list data structure. <BR>
 * A linked list data structure consists of nodes that hold data and pointers
 * to the next node in the list. These lists are commonly used when memory must
 * be managed dynamically.<BR>
 * <!--Parameters: -->
 * @param T generic data type
 * @author Jacob Howarth 
 * @version 1.5
 ***************************************************************************
 */
public class LinkedList<T> implements List<T> {
    
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     *  Node:
     *  head - A reference to the first element in the list.
     *  tail - A reference to the last element in the list.
     *
     *  Integers:
     *  size - An integer representing the current size of the list.         
     ************************************************************************
     */
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
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
        tail = null;
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
    public void add(T element, int index) {
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
        
        Node<T> addition = new Node(element);
        addition.setNext(current);
        
        if (previous == null) { // if the list was empty, the head node is
            head = addition; // is the new head node and so is the tail.
            tail = addition;
        }
        else {
            previous.setNext(addition);
            tail = previous.getNext();
        }
        
        size++; // increase size
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> addLast <BR>
     * <B>Description:</B> Appends elements to the end of the list using  <BR>
     *                     a tail pointer that points the the last element <BR>
     *                     in the list.
     * </P>
     * <!--Parameters: -->
     * @param element An element of a generic data type to be added to the
     *                linked list.
     **********************************************************************
     */
    public void addLast(T element) {

        Node<T> addition = new Node(element); // Create new node
        addition.setNext(null);

        if (head == null) { // If the list is empty, head and tail are the same
            head = addition; // since there is only one element in the list
            tail = addition;
            size++;
        }
        else {
            tail.setNext(addition); // append the new node to the end of the list
            tail = addition;        // and set the new node to be the tail node
            size++;
        }
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
        
        if (previous == null && size == 1) {
            head = previous; // if the list with one node is the node of removal
            tail = previous;
        }
        else if (previous == null && size > 1) {
            current = current.getNext(); // use this code if the index of
            head = current;              // removal is at the head to prevent
                                        // deletion of an entire list.
        }
        else if (current.getNext() == null) {
            tail = previous;
            previous.setNext(current.getNext());
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

    public T getLast() {
        return tail.getElement();
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
            result += iterator.next().toString() + "\n";
        
        return result;
    }
    
}
