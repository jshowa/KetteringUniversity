
package lab0;
import java.util.*;
import java.io.*;

/**
 *************************************************************************
 * <B>Login ID:</B> howa1643 <BR>                                                         
 * CS-102, Fall 2008 <BR>                                                   
 * <B>Programming Assignment 1</B> <BR>                                             
 * <B>DatabaseInput class:</B> A class for getting input from a file to store <BR> 
 * in a Database object. <BR>
 * @author Jacob Howarth
 * @version 1.0                                                                                                          
 *************************************************************************
 */
public class DatabaseInput {
    
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     * Scanner (static):                                                       
     * fileInput - A scanner used to read each line from the input file.                       
     ************************************************************************
     */
    private static Scanner fileInput;
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> loadFile <BR>                                            
     * <B>Description:</B> A method that creates a Database from the data <BR>      
     *                     in the file by reading each line and spliting <BR>
     *                     it into two pieces of information, the abbreviation <BR>
     *                     and the name to be stored in an Airport object. <BR>
     *                     This Airport object is then stored in a Database of <BR>
     *                     Airport objects and returned. <BR>
     * 
     * </P>
     * @throws lab0.FileDataFormatException throw this exception if the format
     *                                      of the data in the file is incorrect.
     * @throws java.io.IOException throw this exception if the file to be
     *                             read does not exist.
     * <!--Parameters: -->
     * @param fileName A String representing the file name containing the
     *                  input data.                                            
     * <!--Returns: -->                                                                                                        
     *  @return data - A Database object containing Airport objects that represent
     *                 the data from the file.
     **********************************************************************
     */
    public static Database loadFile(String fileName) throws IOException,
                                                    FileDataFormatException {
        File input = new File(fileName);
        Database<Airport> data; // database to hold the Airport objects 
                                // representing the data in the file.
        
        /* check the file name to see if it exists, or if the file size 
         * is 0 bytes. If either is true, throw an exception. */
        if (!input.exists() || input.length() == 0L) 
            throw new IOException();
        else {
            data = new Database<Airport>();
            String[] dataLineSplit; /* this will be used to split the incoming
                                     * data from the file into two parts. An
                                     * abbreviation and a name. */
            fileInput = new Scanner(input); 
            
            int i = 0;
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
                    data.add(new Airport(dataLineSplit[0], dataLineSplit[1]), i);
                    i++;
                }
            }
                
            return data;       
        }
    }
    
}
