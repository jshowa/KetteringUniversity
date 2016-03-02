
package lab0;

/**
 *************************************************************************
 * <B>Login ID:</B> howa1643 <BR>                                                         
 * CS-102, Fall 2008 <BR>                                                   
 * <B>Programming Assignment 2</B> <BR>                                             
 * <B>FileDataFormatException class:</B> A class representing an exception <BR>
 * when the format of the data in a file is incorrect. <BR>
 * @author Jacob Howarth
 * @version 1.0                                                                                                          
 *************************************************************************
 */
public class FileDataFormatException extends Exception {
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> FileDataFormatException <BR>                                              
     * <B>Description:</B>  A zero argument constructor to create a <BR> 
     *                      FileDataFormatException to be thrown when <BR>
     *                      the data in the file is of incorrect format <BR>
     * </P>                                                    
     **********************************************************************
     */
    public FileDataFormatException() {
        toString();
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toString <BR>                                                   
     * <B>Description:</B> A method that prints a message for the <BR>
     *                     explaining why the FileDataFormatException was <BR>
     *                     thrown. <BR>
     * </P>                                                                              
     * <!--Returns: -->                                                    
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
