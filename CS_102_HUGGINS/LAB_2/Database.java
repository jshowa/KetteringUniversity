
package lab1;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import lab0.Airport;
import lab0.FileDataFormatException;

/**
 *************************************************************************
 * <B>Login ID:</B> howa1643 <BR>                                                         
 * CS-102, Fall 2008 <BR>                                                   
 * <B>Programming Assignment 2</B> <BR>                                             
 * <B>Database class:</B> A class that loads the two sorted linked lists, <BR>
 * one sorted by airport abbreviation, the other sorted by airport name from <BR>
 * a file and prints each list, sorted according to key value in each Node in <BR>
 * the linked lists contained in the database. <BR>
 * <!--Parameters: -->
 * @param T - generic data type
 * @author Jacob Howarth
 * @version 1.0                                                                                                          
 *************************************************************************
 */
public class Database<T> {
    
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     * Objects (static):                                                       
     * fileInput - A Scanner used to scan the file that contains the airport
     *             records.              
     * listAbbrev - A LinkedList with Nodes sorted by airport abbreviation.
     * listName - A LinkedList with Nodes sorted by airport name.
     ************************************************************************
     */
    private static Scanner fileInput;
    private LinkedList<T> listAbbrev = new LinkedList<T>();
    private LinkedList<T> listName = new LinkedList<T>();
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> Database <BR>                                              
     * <B>Description:</B>  A one argument constructor to create a Database <BR> 
     *                      of airports with two linked lists, one sorted by <BR>
     *                      abbreviation, the other sorted by name. <BR>
     * </P>
     * <!--Exceptions -->
     * @throws java.io.IOException - thrown if file is empty or doesn't exist.
     * @throws lab0.FileDataFormatException - thrown if the format of 
     *                                        the records typed in the file
     *                                        is incorrect.
     * <!--Parameters: -->                                                                                                     
     *      @param fileName a string representing the file path of the file
     *                      to be loaded.
     **********************************************************************
     */
    public Database(String fileName) throws IOException,
                                       FileDataFormatException {
        File input = new File(fileName);
        String[] dataLineSplit;
        
        /* check the file name to see if it exists, or if the file size 
         * is 0 bytes. If either is true, throw an exception. */
        if (!input.exists() || input.length() == 0L) 
            throw new IOException();
        
        fileInput = new Scanner(input);
 
        while(fileInput.hasNext()) {
            // grab a line in the file and store it in a string.
            String fileData = fileInput.nextLine();
                // check to see if the data in the file is delimited with the
                // appropriate character. If not throw an exception.
            if (fileData.charAt(3) != '/')
                throw new FileDataFormatException();
            else {
                 /* seperate the abbreviation and name data in each line of 
                 *the file with the "/" pattern if no exception is thrown.
                 */
                dataLineSplit = fileData.split("/");
                
                // add a new Airport with the split data into the database.
                T airport = (T)(new Airport(dataLineSplit[0], dataLineSplit[1]));
                addByAbbreviation(airport);
                addByName(airport);
            }
        }
    }
    
     /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> addByAbbreviation <BR>                                              
     * <B>Description:</B> A helper method called by the Database constructor <BR>
     *                    that adds airport objects from the records in the file <BR>
     *                    and sorts them by abbreviation. It does this by <BR>
     *                    calculating the index of insertion compared to the <BR>
     *                    alphabetical order of the linked list by abbreviation. <BR>                                            
     * <!--Parameters: -->                                                                                                     
     *  @param addition an object of generic type to be added to the linked list.                                               
     **********************************************************************
     */
    private void addByAbbreviation(T addition) {
        int index = 0;// set initial index to zero so the data can be added
                        // to the empty list.
        
        if (addition instanceof Airport) { // check if addition is an airport,
                                            // otherwise don't add.
            index = listAbbrev.compareToKey(((Airport)addition).getAbbreviation());
            // calculate the relative index of insertion based on the 
            // alphabetical ordering of the list by airport name.
            listAbbrev.add(addition, index, 
                        ((Airport)addition).getAbbreviation());
        }
        
    }
    
     /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> addByName <BR>                                              
     * <B>Description:</B> A helper method called by the Database constructor <BR>
     *                    that adds airport objects from the records in the file <BR>
     *                    and sorts them by name. It does this by calculating <BR>
     *                    the index of insertion compared to the alphabetical <BR>
     *                    order of the linked list by name. <BR>                                            
     * <!--Parameters: -->                                                                                                     
     *  @param addition an object of generic type to be added to the linked list.                                               
     **********************************************************************
     */
    private void addByName(T addition) {
        int index = 0; // set initial index to zero so the data can be added
                        // to the empty list.
        
        if (addition instanceof Airport) { // check if addition is an airport,
                                            // otherwise don't add.
            index = listName.compareToKey(((Airport)addition).getName());
            // calculate the relative index of insertion based on the 
            // alphabetical ordering of the list by airport name.
            listName.add(addition, index, 
                        ((Airport)addition).getName());
        }
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> setAbbreviation <BR>                                              
     * <B>Description:</B> Mutator method that sets the listAbbrev linked list. <BR>                                            
     * <!--Parameters: -->                                                                                                     
     *  @param listAbbrev LinkedList of generic type sorted by airport abbreviation.                                               
     **********************************************************************
     */
    public void setListAbbrev(LinkedList<T> listAbbrev) {
        this.listAbbrev = listAbbrev;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> setListName <BR>                                                    
     * <B>Description:</B> Mutator method that sets the listName linked list. <BR>                                                   
     * <!--Parameters: -->                                                                                             
     *  @param listName LinkedList of generic type sorted by airport name.                                                      
     **********************************************************************
     */
    public void setListName(LinkedList<T> listName) {
        this.listName = listName;
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
    public LinkedList<T> getListAbbrev() {
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
    public LinkedList<T> getListName() {
        return listName;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toStringByAbbreviation <BR>                                                   
     * <B>Description:</B> Prints the linked list sorted by airport abbreviation <BR>          
     *                     in the following format: <BR>             
     *                     <P> <I><Q>&lt;abbreviation&gt;: &lt;name&gt;</Q></I> 
     *                     </P>                                                                              
     * <!--Returns: -->                                                    
     *  @return LinkedList&lt;T&gt; String - a string representing the airport
     *                                      records sorted by abbreviation.                                                     
     **********************************************************************
     */
    public String toStringByAbbreviation() {
        return listAbbrev.toString();
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toStringByName <BR>                                                   
     * <B>Description:</B> Prints the linked list sorted by airport name <BR>          
     *                     in the following format: <BR>             
     *                     <P> <I><Q>&lt;abbreviation&gt;: &lt;name&gt;</Q></I> 
     *                     </P>                                                                              
     * <!--Returns: -->                                                    
     *  @return LinkedList&lt;T&gt; String - a string representing the airport
     *                                      records sorted by name.                                                     
     **********************************************************************
     */
    public String toStringByName() {
        return listName.toString();
    }
    
    
    
}
