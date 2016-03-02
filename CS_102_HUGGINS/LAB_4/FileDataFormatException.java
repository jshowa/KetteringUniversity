/**
 * Name: Jacob S. Howarth
 * Login ID: howa1643
 * CS-102, Fall 2008
 * Programming Assignment 4
 * FileDataFormatException class: A custom exception that's thrown when the
 * airport records in the file contains errors, paticularly when the airport
 * abbreviation and name aren't delimited by a ":".
 */
public class FileDataFormatException extends Exception {
    
    /**
     **********************************************************************
     * Method: FileDataFormatException                                               
     * Description:  A zero argument constructor to create a 
     *                      FileDataFormatException to be thrown when 
     *                      the data in the file is of incorrect format 
     *                                                   
     **********************************************************************
     */
    public FileDataFormatException() {
        toString();
    }
    
    /**
     **********************************************************************
     * 
     * Method: toString                                                   
     * Description: A method that prints a message for the 
     *                     explaining why the FileDataFormatException was 
     *                     thrown. 
     *                                                                              
     * Returns:                                                   
     *  @return exceptionInfo - A string containing the error message.                                                     
     **********************************************************************
     */ 
    @Override
    public String toString() {
        String exceptionInfo = "The data in the file has incorrect format.\n" +
                               "Please look inside the file and change the\n" +
                               "data so it is in the following format:\n\n" +
                               "<abbreviation>/ <name>";
        return exceptionInfo;
    }
}

