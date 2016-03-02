
import javax.swing.*;
import javax.swing.text.*;

/**
 * Name: Jacob S. Howarth
 * Login ID: howa1643
 * CS-102, Fall 2008
 * Programming Assignment 5
 * JTextFieldLimit class: A class that limits a text field to a specified
 * character width used in UI applications.
 */
public class JTextFieldLimit extends PlainDocument {
    
    /* 
     * VARIABLE DECLARATIONS:
     * 
     * Integer (static):
     * limit = integer representing the character limit in a text field.
     * 
     */
     private int limit;
     
     public JTextFieldLimit(int limit) {
         super(); // call the zero arg constructor in the plain document
                    // class to create a PlainDocument object.
         this.limit = limit; 
     }
     
     /**
     * Method: getLimit
     * Purpose: An accessor method that returns the character limit on 
     *          a text field object. The method is declared final so it
     *          it cannot be overriden.
     * Parameters:
     * @return int          The limit integer representing the character limit.
     */
     public final int getLimit() {
         return limit;
     }
     
     /**
     * Method: setLimit
     * Purpose: A mutator method that sets the character limit on a text field.
     * Parameters:
     * @param limit         Integer representing the character input limit on
     *                      the text field.
     */
     public final void setLimit(int limit) {
         this.limit = limit;
     }
}

