
package lab0;

/**
 *************************************************************************
 * <B>Login ID:</B> howa1643 <BR>                                                         
 * CS-102, Fall 2008 <BR>                                                   
 * <B>Programming Assignment 2</B> <BR>                                             
 * <B>Airport class:</B> Represents an airport object containing an abbreviation <BR>
 * and a name/location. <BR> 
 * @author Jacob Howarth                                                 
 * @version 1.5                                                                                                          
 *************************************************************************
 */
public class Airport {
    
    /**
     ************************************************************************
     * VARIABLE DECLARATIONS:                                               
     * String:                                                       
     * abbreviation - A three character string representing an       
     *                       abbreviation of the airport name.              
     * name - A string representing the name of the airport.         
     ************************************************************************
     */
    private String abbreviation; 
    private String name;
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> Airport <BR>                                              
     * <B>Description:</B>  A zero argument constructor to create an airport <BR> 
     *                      object with abbreviation and name initialized <BR>
     *                      to a null string. <BR>   
     * </P>                                                    
     **********************************************************************
     */
    public Airport() {
        abbreviation = "";
        name = "";
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> Airport <BR>                                              
     * <B>Description:</B>  A two argument constructor to create an airport <BR> 
     *                      object with a designated abbreviation and name. <BR>   
     * </P>
     * <!--Parameters: -->                                                                                                     
     *      @param abbreviation String                                               
     *      @param name String                                                    
     **********************************************************************
     */
    public Airport(String abbreviation, String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getAbbreviation <BR>                                            
     * <B>Description:</B> Accessor method that returns an Airport instances <BR>      
     *                     abbreviation. <BR>
     * </P>                                            
     * <!--Returns: -->                                                                                                        
     *  @return abbreviation - String                                             
     **********************************************************************
     */
    public String getAbbreviation() {
        return abbreviation;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> getName <BR>                                                     
     * <B>Description:</B> Accessor method that returns an Airport <BR>        
     *                     instances name. <BR>                                           
     * </P>
     * <!--Returns: -->                                                                                                         
     *  @return name - String                                                      
     **********************************************************************
     */
    public String getName() {
        return name;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> setAbbreviation <BR>                                              
     * <B>Description:</B> Mutator method that sets an Airport instances <BR>  
     *                     abbreviation. <BR>                                            
     * <!--Parameters: -->                                                                                                     
     *  @param abbreviation String                                               
     **********************************************************************
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> setName <BR>                                                    
     * <B>Description:</B> Mutator method that sets an Airport instances name. <BR>                                                   
     * <!--Parameters: -->                                                                                             
     *  @param name String                                                      
     **********************************************************************
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     ************************************************************************
     * <P align="left">
     * <B>Method:</B> equals <BR>                                           
     * <B>Description:</B> Checks to see if two Airport objects have the <BR>
     *                     same name and abbreviation, or if an input string <BR>
     *                     equals and Airport instances abbreviation or <BR>
     *                     if an Airport instance has the same name as an input <BR>
     *                     string as compared to by the <I>equalsName()</I> <BR> 
     *                     method. This method returns the following: <BR>                                   
     *                       <DL>                                           
     *                       <DT> <B><I>true</I></B> <DD> - if both Airport  
     *                       objects have the same abbreviation and name or
     *                       an input string has the same abbreviation or
     *                       name as compared by the <I>equalsName()</I> method
     *                       as an Airport instance does.            
     *                       <DT> <B><I>false</I></B> <DD> - if both Airport  
     *                       objects <I>do not</I> have the same abbreviation 
     *                       and name or an input string <I>does not</I>
     *                       have the same abbreviation or name as compared by 
     *                       the <I>equalsName()</I> method as an Airport 
     *                       instance does.   
     *                       </DL>
     * </P>                                              
     * <!--Parameters: -->                                                   
     *  @param input object to be compared.                                                       
     *                                                                      
     * <!--Returns: -->                                                      
     *  @return boolean - true if the same, false otherwise.                                                    
     ************************************************************************
     */
    @Override
    public boolean equals(Object input) {
        /* This method is used in conjunction with the search() method
         * in the Database class. This method takes the search parameter
         * element and uses it as the variable input here. This allows
         * for search parameters to be different object types, hence
         * the Object data type for input. */
        
        /* If the input search parameter is an instance of Airport, check if 
         * the abbreviation and name of the calling Airport object are equal. 
         * Return true if they are. */
        if(input instanceof Airport && 
          (this.abbreviation).equals(((Airport)(input)).getAbbreviation()) &&
                (this.name).equals(((Airport)(input)).getName()))
            return true;
        /* If the input search parameter is a String (paticularly an abbreviation
         * string), then make a comparison with the calling Airport object.
         * Return true if they are equal.*/
        if (input instanceof String && 
                (this.abbreviation).equals((String)(input)))
            return true;
        /* If the input search parameter is a String (paticularly a name
         * string), then make a comparison with the calling Airport object.
         * The use of the equalsName() method allows for the input search 
         * parameter to not be exactly equal to the name string contained
         * in the calling Airport object. For example:
         *
         * If I wanted to search for airports in the Detroit area, then
         * I can just type "Detroit" and the following "full" Airport names will
         * be returned:
         *
         *    Detroit (Wayne County), MI, USA
         *    Detroit, MI, USA
         *    Detroit (City Airport), MI, USA
         *    etc...
         * 
         * This allows a user to search for airports without typing the full
         * airport name.
         */
        if (input instanceof String && this.equalsName((String)(input)))
            return true;
        
        return false;
    }
    
    /**
     ************************************************************************
     * <P align="left">
     * <B>Method:</B> equalsName <BR>                                                     
     * <B>Description:</B> Checks if the string input has the same <BR>          
     *                     characters has the string name. The <BR>             
     *                     method returns: <BR>                                 
     *                       <DL>                                           
     *                       <DT> <B><I>true</I></B> <DD> - if the string   
     *                       input has a shorter length then the name string 
     *                       and the input string has the same characters as
     *                       the name string up to the length of the input  
     *                       string.                                        
     *                       <DT> <B><I>false</I></B> <DD> - if the string 
     *                       name and the string input <I>do not</I>        
     *                       have the same characters or the string input   
     *                       is of greater length then the name string.
     *                       </DL>     
     * </P>                                                                     
     * <!--Parameters: -->                                                   
     *  @param input String                                                       
     *                                                                      
     * <!--Returns: -->                                                      
     *  @return boolean - true or false                                                     
     ************************************************************************
     */
    private boolean equalsName(String input) { 
        for (int i = 0; i <= input.length() - 1; i++) 
            /*The if statement compares each character in the name string to
             * the characters in the input string to see if they are equal.
             *If the input string is greater in length then the name string,
             *then the method returns false to prevent ArrayOutOfBounds
             *exceptions. */
            if ((input.length() > name.length()) ||
                    (name.charAt(i) != input.charAt(i)))
                return false;
        
        return true;
    }
    
    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> toString <BR>                                                   
     * <B>Description:</B> Prints the abbreviation and name of an Airport <BR>          
     *                     instance in the following format. <BR>             
     *                     <P> <I><Q>&lt;abbreviation&gt;: &lt;name&gt;</Q></I> 
     *                     </P>                                                                              
     * <!--Returns: -->                                                    
     *  @return name - String                                                     
     **********************************************************************
     */
    @Override
    public String toString() {
        return (abbreviation + ": " + name);
    }
     
}
