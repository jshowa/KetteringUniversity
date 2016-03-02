
package lab0;

/**
 *************************************************************************
 * <B>Login ID:</B> howa1643 <BR>                                                         
 * CS-102, Fall 2008 <BR>                                                   
 * <B>Programming Assignment 1</B> <BR>                                             
 * <B>Database class:</B> A blueprint for a Database object containing 
 * generic data. <BR> 
 * <!--Parameters: -->
 *  @param T generic data type
 * @author Jacob Howarth
 * @version 1.0                                                                                                          
 *************************************************************************
 */
public class Database<T> implements List<T> {
    
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     * Constants:                                                       
     * DEFAULT_SIZE - A random number that represents the default size of a newly
     *           created Database object if no size is specified.              
     * NOT_FOUND - An integer used in the indexOf() method to represent
     *             an object that wasn't found.
     * Integers:
     * size - A number representing the current size of the Database. The size
     *        is -1 if no elements are in the Database at the current time.
     * Arrays:
     * contents - An array of generic type that holds the data of the 
     *            Database.         
     ************************************************************************
     */
    private static final int DEFAULT_SIZE = 50;
    private static final int NOT_FOUND = -1;
    private int size;
    private T[] contents;
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> Database <BR>                                              
     * <B>Description:</B>  A zero argument constructor to create a Database <BR> 
     *                      object with a default size specified by the. <BR>
     *                      constant DEFAULT_SIZE. <BR>   
     * </P>                                                    
     **********************************************************************
     */
    public Database() {
        contents = (T[])(new Object[DEFAULT_SIZE]);
        size = -1; // size starts at -1 to allow for 0 to be the starting
                   // index to add.
    }
    
    /**
     ************************************************************************
     * <P align="left">
     * <B>Method:</B> Database <BR>                                              
     * <B>Description:</B>  A one argument constructor to create a Database <BR> 
     *                      object with a specified size. <BR>   
     * </P>
     * 
     * <!--Paramters: -->
     *  @param initialCapacity integer representing the specified size of the 
     *                         Database.                                                    
     ************************************************************************
     */
    public Database(int initialCapacity) {
        contents = (T[])(new Object[initialCapacity]);
        size = -1;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> size <BR>                                            
     * <B>Description:</B> A method that returns the actual size of <BR>      
     *                     the Database at the current time, ie. only counts <BR>
     *                     the elements it currently contains. <BR>
     * </P>                                            
     * <!--Returns: -->                                                                                                        
     *  @return int - Integer representing the number of elements 
     *                currently in the Database.                                              
     **********************************************************************
     */
    public int size() {
        return (size + 1); //returns the size + 1, because size initially starts
                            //one behind.
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> isEmpty <BR>                                            
     * <B>Description:</B> A method that returns a boolean value <BR>      
     *                     representing the following: <BR>
     *                     <DL>                                           
     *                       <DT> <B><I>true</I></B> <DD> - if the Database
     *                       contains no elements.            
     *                       <DT> <B><I>false</I></B> <DD> - if the Database has
     *                       one or more elements.   
     *                     </DL>
     * </P>                                            
     * <!--Returns: -->                                                                                                        
     *  @return boolean - true or false                                             
     **********************************************************************
     */
    public boolean isEmpty() {
        return (size == -1);
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> get <BR>                                            
     * <B>Description:</B> A method that returns the first instance of an <BR>
     *                     element in the Database. <BR>
     *                     <BR>
     *                     An IndexOutOfBoundsException is thrown if an element <BR>
     *                     is added to a negative index or an index greater than <BR>
     *                     size + 1. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param index Integer representing the first instance of the element.
     * <!--Returns: -->
     *  @return T - element of generic type.                                               
     **********************************************************************
     */
    public T get(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        else 
            return (contents[index]);
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> iterator <BR>                                            
     * <B>Description:</B> A method that returns an ArrayIterator instance <BR>
     *                     which can be used to iterate through each element <BR>
     *                     currently in the Database. <BR>
     * </P>                                            
     * <!--Returns: -->
     *  @return ArrayIterator&lt;T&gt; - iterator that iterates through 
     *                                   generic data types.                                               
     **********************************************************************
     */
    public ArrayIterator<T> iterator() {
        return (new ArrayIterator<T>(contents, size()));
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> add <BR>                                            
     * <B>Description:</B> A method that adds an element of generic data <BR>
     *                     type to the Database at a location specified <BR>
     *                     by the index parameter. <BR>
     *                     <BR>
     *                     An IndexOutOfBoundsException is thrown if an element <BR>
     *                     is added to a negative index or an index greater than <BR>
     *                     size + 1. <BR>
     * </P>                                            
     * <!--Parameters: -->
     *  @param element An element of generic data type to be added to the 
     *                 Database.                                                                                                        
     *  @param index Integer representing the position the element will be
     *               be added to in the Database.                                              
     **********************************************************************
     */
    public void add(T element, int index) {
        if ((index < 0) || (index > size + 1)) {
            throw new IndexOutOfBoundsException();
        }
        if (size == contents.length - 1) // if the end of the array is reached
            contents = extendCapacity(contents); // extend the array.
        
        for (int pos = size; pos >= index; pos--)
            contents[pos + 1] = contents[pos]; // shift the data right in the
                                               // array after adding the element
                                               // to keep the data in a
                                               // continuous line.
        contents[index] = element;
        size++;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> extendCapacity <BR>                                            
     * <B>Description:</B> A helper method used by add() when the Database <BR>
     *                     has reached full capacitiy. This method extends the <BR>
     *                     Databases size by making a copy of the elements <BR>
     *                     currently in the Database into an array of extended <BR>
     *                     size to serve as the new Database. <Br>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param contents A copy of the Database to be extended.
     * <!--Returns: -->
     *  @return copy - An extend version of the Database that contains the
     *                 same elements before the copy.                                              
     **********************************************************************
     */
    private T[] extendCapacity(T[] contents) {
        T[] copy = (T[])(new Object[contents.length * 2]);
        for (int i = 0; i <= size; i++)
            copy[i] = contents[i];
        return copy;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> remove <BR>                                            
     * <B>Description:</B> A method that removes an elements from the <BR>
     *                     Database at the location specified by the index <BR>
     *                     parameter. <BR>
     *                     <BR>
     *                     An IndexOutOfBoundsException is thrown if an element <BR>
     *                     is added to a negative index or an index greater than <BR>
     *                     size + 1. <BR>
     * </P>                                            
     * <!--Parameters: -->                                                                                                        
     *  @param index Integer representing the position of the element being
     *               removed.                                             
     **********************************************************************
     */
    public void remove(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        
        for (int pos = index++; pos <= size; pos++)
            contents[pos--] = contents[pos]; //shifts the data left in the
                                            //array after removing the element 
                                            //to keep the data in a continuous 
                                            //line.
        size--;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> indexOf <BR>                                            
     * <B>Description:</B> A method that returns the index of the first <BR> 
     *                     instance of an element in the Database. <BR>
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
        for (int index = 0; index <= size; index++)
            if (contents[index].equals(element))
                return index;
        
        return NOT_FOUND;
    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> search <BR>                                            
     * <B>Description:</B> A method that returns multiple instances of <BR> 
     *                     an element in the Database if there is more <BR>
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
        ArrayIterator<T> iterator = iterator();
        String result = "";
        
        /* This method compares the element of Object type to see if it's an
         * instance of Airport or an instance of String. This allows for search
         * parameters of different types to be used and explains the long
         * if condition expression.
         */
        while (iterator.hasNext()) // while another element exists, continue
                                    //iterating.
            if((element instanceof Airport && (iterator.next()).equals(element))
                || (element instanceof String && 
                (iterator.next()).equals((String)(element))))
                /* The nextOffset() method is used to return the element before
                 * the incrementing of the iterators position which occured in
                 * the next() method call in the if condition expression. */
                result += (iterator.nextOffset()).toString() + "\n";
                /* If a match is found after calling the equals method of the
                 * Airport class, then the Airport object is converted to a
                 * String and concatenated to result. This allows a return
                 * of a list of multiple Airport objects.
                 */
        
        return result;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toString <BR>                                                   
     * <B>Description:</B> A method that prints the data in the Database <BR>
     * </P>                                                                              
     * <!--Returns: -->                                                    
     *  @return result - A string representing the data in the Database.                                                     
     **********************************************************************
     */ 
    @Override
    public String toString() {
        String result = "";
        ArrayIterator<T> iterator = iterator();
        
        while(iterator.hasNext())
            result += iterator.next() + "\n";
        
        return result;       
    }

}
