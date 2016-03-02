/**
 * Name: Jacob S. Howarth
 * Login ID: howa1643
 * CS-102, Fall 2008
 * Programming Assignment 5
 * Airport class: A class to create an Airport object with an abbreviation
 * and name. The class also contains accessors, mutators, toString, and
 * equals methods.
 *
 */
public class Airport implements Comparable<Airport> {
    
    /* 
     * VARIABLE DECLARATIONS:
     * 
     * String:
     * String name - Contains the name of an airport.
     * String abbreviation - Contains the abbreviation of an airport.
     * 
     * CONSTANTS:
     * 
     * Integer:
     * int ERROR - A random value returned in the compareTo method when
     *             Object "comparison" cannot be compared to the abbreviation
     *             string or name string of an airport object. This is so the
     *             the method actually returns something.
     */
     private String abbreviation;
     private String name;
     private static final int ERROR = 10;
     
    /**
     * Method: Airport
     * Purpose: This method creates an empty Airport with no name
     *          and no abbreviation.
     * Parameters: N/A
     */
    public Airport() {
        this.abbreviation = "";
        this.name = "";
    }
    
    /**
     * Method: Airport
     * Purpose: This method creates an Airport object with a name and
     *          abbreviation specified in the arguments.
     * Parameters:
     * @param abbreviation      The abbreviation of the airport.
     * @param name              The full name of the airport.
     */
    public Airport(String abbreviation, String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }
    
    /**
     * Method: getAbbreviation
     * Purpose: Accessor that returns the three letter abbreviation of
     *          an airport object.
     * Parameters:
     * @return abbreviation   String containing the airports three letter
     *                        abbreviation code.
     */
    public String getAbbreviation() {
        return abbreviation;
    }
    
    /**
     * Method: setAbrreviation
     * Purpose: Mutator that stores the three letter abbreviation of
     *          an airport.
     * Parameters:
     * @param abbreviation    String containing the airports three letter
     *                        abbreviation code.
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
    
    /**
     * Method: getName
     * Purpose: Accessor that returns the full name of an airport object.
     * Parameters:
     * @return name       String containing the full name of the airport.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Method: setName
     * Purpose: Mutator that stores the full name of an airport object.
     * Parameters:
     * @param name      String containing the airports full name. 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Method: equals
     * Purpose: Overriden method from Object class that will return the
     *          following:
     *              true - If the argument is an airport object and
     *                     the abbreviation and name of the argument
     *                     is equal to the calling object.
     *              true - If the argument is a string and the calling
     *                     object's abbreviation is equal to this string
     *              true - If the argument is a string and the calling
     *                     object's name is equal to the string under
     *                     the following circumstances:
     *                          
     *                          1) The string is equal to the calling
     *                             objects full name and not greater
     *                             in length then the calling objects
     *                             full name.
     *                          2) The string is an abbreviation of
     *                             the calling objects full name.
     * 
     *              false - If the argument is an airport object and
     *                      its abbreviation and name are NOT equal
     *                      to the calling objects.
     *              false - If the argument is a string and the 
     *                      calling objects abbreviation is NOT equal
     *                      to this string argument.
     *              false - If the argument is a string and the
     *                      argument is NOT the calling objects full 
     *                      name the argument is longer then calling
     *                      objects full name or the argument is NOT
     *                      the abbreviations full name.
     * Parameters:
     * @param item          An object type.
     * @return boolean      A value of true or false in accordance to the
     *                      above conditions
     */
    @Override
    public boolean equals(Object item) {
        // 1. If the object item is an airport, check if the abbreviation
        //    and name of the argument equals the calling objects.
        if (item instanceof Airport) {
            if ((this.abbreviation).equals(((Airport)item).getAbbreviation())
                  && (this.name).equals(((Airport)item).getName()))
                return true;
        }
        // 2. If the object item is a string...
        else if (item instanceof String) {
            // 3. check if the argument is equal to the calling objects
            //    abbreviation
            if ((this.abbreviation).equals((String)item))
                return true;
            // 4. check if the argument is equal to the calling objects
            //    name or an abbreviation of the calling objects name.
            if (this.checkName((String)item))
                return true;
        }
        // 5. Otherwise return false if above cases fail.
        return false;
    }
    
    /**
     * Method: checkName
     * Purpose: A helper method for the equals method that checks
     *          the argument under the following conditions:
     *              
     *              1) Checks the argument to see if the 
     *                 argument is longer than the calling
     *                 objects.
     *              2) Checks the individual characters of
     *                 the argument to see if the argument
     *                 is a valid abbreviation for the calling
     *                 objects name.
     * 
     * Parameters:
     * @param shortName     String argument to check for validation
     *      `               based on the above conditions.
     * @return boolean      A value of true or false based on
     *                      the above conditions.
     */
    private boolean checkName(String shortName) { 
        for (int pos = 0; pos <= shortName.length() - 1; pos++) 
            if ((shortName.length() > name.length()) ||
                    (name.charAt(pos) != shortName.charAt(pos)))
                return false;
        
        return true;
    }
    
    /**
     * Method: compareTo
     * Description: A method implemented from the Comparable interface
     *              that compares an airport objects abbreviation and
     *              name strings to a key value to see if the abbreviation
     *              and name strings are lexographically the same, greater than,
     *              or less than the comparison string 
     * Parameters:
     * @param comparison        String value being compared to
     * Returns:
     * @return Integer          The integer 0 means the comparison object's
     *                          name or abbreviation strings EQUAL the
     *                          abbreviation or name strings of the calling
     *                          object, lexographically. 1 if the abbreviation
     *                          or name of the calling object is lexographically
     *                          greater than the comparison object's name
     *                          or abbreviation strings. And -1 if the name
     *                          and abbreviation strings of the calling
     *                          object are lexographically less than the 
     *                          comparison object's name and abbreviation strings.
     * 
     */
    public int compareTo(Airport comparison) {
        if (!(comparison.getAbbreviation().equals(""))) {
            if (this.equals(comparison.getAbbreviation()))
                return 0;
            if (this.abbreviation.compareTo(comparison.getAbbreviation()) < 0)
                return -1;
            if (this.abbreviation.compareTo(comparison.getAbbreviation()) > 0)
                return 1;
        }
        else if (!(comparison.getName().equals(""))) {
            if (this.equals(comparison.getName()))
                return 0;
            if (this.name.compareTo(comparison.getName()) < 0)
                return -1;
            if (this.name.compareTo(comparison.getName()) > 0) 
                return 1;
        }
        return ERROR;
    }
    
    /**
     * Method: toString
     * Purpose: Overriden method from Object class that prints the
     *          airports abbreviation and name in the following
     *          format:
     * 
     *              &lt;abbreviation&gt: &lt;name&gt;
     * Parameters:
     *  @return String  String in the form &lt;abbreviation&gt: &lt;name&gt;
     */
    @Override
    public String toString() {
        return (this.abbreviation + ": " + this.name);
    }
    
    /**
     * Method: toString
     * Description: This method is an overloaded "toString" method
     *              that uses special print codes to print an airport record
     *              in different formats.
     * @param option        Number string specifying print code.
     * @return result       Resulting formatted string.
     */
    public String toString(String option) {
        String result = "";
        
        if (option.equals("1"))
            result = this.abbreviation + "/" + this.name;
        
        return result;
    }

    
    
}

