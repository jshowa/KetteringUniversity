import java.awt.*;
import javax.swing.*;
/**
 * Name: Jacob S. Howarth
 * Login ID: howa1643
 * CS-102, Fall 2008
 * Programming Assignment 5
 * DescriptionPanel class: This class creates a text area with a scroll pane
 * used to present information. 
 */
public class DescriptionPanel extends JPanel {
    
    /* 
     * VARIABLE DECLARATIONS:
     * 
     * JTextArea:
     * description - a text area containing uneditble information text
     * to represent in a UI.
     */
    private JTextArea description = new JTextArea();
    
    /**
     * Method: DescriptionPanel
     * Purpose: A zero argument constructor that initializes and configures
     *          a text area with a scroll pane to be added into a UI.
     * Parameters: N/A
     */
    public DescriptionPanel() {
        description.setLineWrap(true); // line wrap words if they have to many
                                        // chars to fit in the text area.
        description.setWrapStyleWord(true);
        description.setEditable(false);
         
        JScrollPane scrollPane0 = new JScrollPane(description);
        
        setLayout(new BorderLayout(5, 5));
        add(scrollPane0);
    }
    
   /**
    * Method: DescriptionPanel
    * Purpose: A mutator method that sets the text displayed in the text area.
    * Parameters: 
    * @param text       A string representing the information text displayed
    *                   in the text area.
    */
    public void setDescrip(String text) {
        description.setText(text);
    }
    
    /**
    * Method: DescriptionPanel
    * Purpose: An accessor method used to return the text displayed in the 
    *          text area.
    * Parameters: 
    * @return String       returns the information text displayed in the text
    *                      area.
    */
    public String getDescrip() {
        return description.getText();
    }
     
}

