

import java.util.*;
/**
 *************************************************************************
 * <B>Login ID:</B> howa1643 <BR>                                                         
 * CS-102, Fall 2008 <BR>                                                   
 * <B>Programming Assignment 3</B> <BR>                                             
 * <B>Database class:</B> A class that defines an Airport database with <BR>
 * two linked lists, one sorted by abbreviation and the other sorted by name. <BR>
 * The database also performs the functions of searching, relative insertion by <BR>
 * key, and printing each list in sorted order. <BR>
 * @author Jacob Howarth                                                                      
 *************************************************************************
 */
public class AirportDatabase {
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     * LinkedLists:                                                       
     * listAbbrev - A linked list of airport records sorted by abbreviation.           
     * listName - A linked list of airport records sorted by name.         
     ************************************************************************
     */
    private LinkedList<Airport> listAbbrev = new LinkedList<Airport>();
    private LinkedList<Airport> listName = new LinkedList<Airport>();
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> compareByAbbrev <BR>                                            
     * <B>Description:</B> A method that returns an integer value representing <BR>      
     *                     the appropriate index to add an airport in the linked list <BR>
     *                     based off a lexographical (alphabetical) comparison <BR>
     *                     between the key argument and the airports abbreviation. <BR>                    
     * </P>
     * <!--Parameters: -->
     *  @param key A string value representing the key to be compared too.                                            
     * <!--Returns: -->                                                                                                        
     *  @return index - An integer value representing the appropriate
     *                         index to add to in the linked list, in
     *                         alphabetical order based on the airports
     *                         abbreviation.                                          
     **********************************************************************
     */
    public int compareByAbbrev(String key) {
        ListIterator<Airport> iterator = listAbbrev.listIterator(); // linked list iterator
        boolean continueLoop = true; // loop control variable to exit when key is
                                     // lexographically less than airport abbrev.
        int index = 0; // index incrementer for returning appropriate index to add too.            
       
        while (iterator.hasNext() && continueLoop) { // iterate through each element in the list.
            String airportAbbrev = (iterator.next()).getAbbreviation(); // grab the string to compare
                                                         // the key too. In this case the
                                                         // abbreviation string of the Airport.
            if(key.compareTo(airportAbbrev) > 0)
                index++; // increment the index counter if the key 
                                // parameter is lexographically greater
                                // than the abbreviation. This ensures addition
                                // by alphabetical order.
            else if(key.compareTo(airportAbbrev) <= 0)
                continueLoop = false; // stop the loop when you've reached a 
                                    // a abbreviation string that is greater than
                                    // the key parameter.
            
        }
        return index;    
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> compareByName <BR>                                            
     * <B>Description:</B> A method that returns an integer value representing <BR>      
     *                     the appropriate index to add an airport in the linked list <BR>
     *                     based off a lexographical (alphabetical) comparison <BR>
     *                     between the key argument and the airports name. <BR>                    
     * </P>
     * <!--Parameters: -->
     *  @param key A string value representing the key to be compared too.                                            
     * <!--Returns: -->                                                                                                        
     *  @return index - An integer value representing the appropriate
     *                         index to add to in the linked list, in
     *                         alphabetical order based on the airports
     *                         name.                                          
     **********************************************************************
     */
    public int compareByName(String key) {
        ListIterator<Airport> iterator = listName.listIterator(); // linked list iterator
        boolean continueLoop = true;// loop control variable to exit when key is
                                     // lexographically less than airport abbrev.
        int index = 0; // index incrementer for returning appropriate index to add too.
       
        while (iterator.hasNext() && continueLoop) { // iterate through each element in the list.
            String airportName = (iterator.next()).getName(); // grab the string to compare
                                                         // the key too. In this case the
                                                         // name string of the Airport.
            if(key.compareTo(airportName) > 0)
                index++; // increment the index counter if the key 
                                // parameter is lexographically greater
                                // than the name. This ensures addition
                                // by alphabetical order.
            else if(key.compareTo(airportName) <= 0)
                continueLoop = false; // stop the loop when you've reached a 
                                    // a name string that is greater than
                                    // the key parameter.
            
        }
        return index;    
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getListAbbrev <BR>                                            
     * <B>Description:</B> Accessor method that returns the linked list <BR>      
     *                     sorted by airport abbreviations. <BR>
     * </P>                                            
     * <!--Returns: -->                                                                                                        
     *  @return listAbbrev - LinkedList of generic type sorted by airport abbreviation.                                            
     **********************************************************************
     */
    public LinkedList<Airport> getListAbbrev() {
        return listAbbrev;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getListName <BR>                                            
     * <B>Description:</B> Accessor method that returns the linked list <BR>      
     *                     sorted by airport name. <BR>
     * </P>                                            
     * <!--Returns: -->                                                                                                        
     *  @return listName - LinkedList of generic type sorted by airport name.                                             
     **********************************************************************
     */
    public LinkedList<Airport> getListName() {
        return listName;
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
     *  @param list A linked list of generic type to search through.
     *  @param searchParam The value to search for in the list.
     * <!--Returns: -->
     * @return result - String representing the instance(s) of the element as
     *                   returned by the toString() method inherited by
     *                   each elements class.                                                
     **********************************************************************
     */
    public String search(LinkedList<Airport> list, Object searchParam) {
        ListIterator<Airport> iterator = list.listIterator();
        String result = "";
        
        /* This method compares the element of Object type to see if it's an
         * instance of Airport or an instance of String. This allows for search
         * parameters of different types to be used and explains the long
         * if condition expression.
         */
        while (iterator.hasNext()) { // while another element exists, continue
                                    //iterating.
            Airport valueFromList = iterator.next(); // store the current iteration
                                                // temporarily.
            if (valueFromList.equals(searchParam))
                result += valueFromList.toString() + "\n"; //If found, call
                                                // the found objects toString()
                                                // method and append to result.
        }
        
        return result;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toString <BR>                                                   
     * <B>Description:</B> A method that prints the data in the linked list <BR>
     * </P> 
     * <!--Parameters: -->
     * @param list Linked list to print.                                                                             
     * <!--Returns: -->                                                    
     * @return result - A string representing the data in the linked list.
     **********************************************************************
     */ 
    public String toString(LinkedList<Airport> list) {
        ListIterator<Airport> iterator = list.listIterator(); // list iterator
        String result = ""; // printed list
        
        while (iterator.hasNext()) { // while the list has an element, append
                                     // the element to result using it's toSting()
                                     // method.
            result += (iterator.next()).toString() + "\n";      
        }
        
        return result;
    }

}
