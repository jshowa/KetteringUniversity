import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * Name: Jacob S. Howarth
 * Login ID: howa1643
 * CS-102, Fall 2008
 * Programming Assignment 5
 * AirportDatabaseFrame class: Class that creates all the GUI based components
 * for the airport database program.
 */
public class AirportDatabaseFrame {
    
    /* 
     * VARIABLE DECLARATIONS:
     * 
     * JFrame:
     * GUIframe - The UI that contains all the components for the airport
     *            database program. The GUI has a border style layout.
     * 
     * JPanel:
     * westPanel - The panel containing the UI operations to add, delete, and
     *             search the airport database. The panel is placed in the 
     *             west region of the GUIframe.
     * centerPanel - The panel containing the database sorted by abbreviation
     *               text description panel and the database sorted by name
     *               text description panel. The panel is placed in the center 
     *               region of the GUIframe.
     * 
     * DescriptionPanel:
     * descriptionPanel0 - The text area outputing the current state of the
     *                     abbrevDatabase binary search tree that sorts the
     *                     the records lexographically by abbreviation.
     * descriptionPanel1 - The text area outputing the current state of the
     *                     nameDatabase binary search tree that sorts the
     *                     the records lexographically by name.
     * 
     * BinarySearchTree:
     * abbrevDatabase - A tree containing the airport records sorted by
     *                  abbreviation.
     * nameDatabase - A tree containing the airport records sorted by name.
     */
    private JFrame GUIframe;
    private JPanel westPanel;
    private JPanel centerPanel;
    protected DescriptionPanel descriptionPanel0 = new DescriptionPanel();
    protected DescriptionPanel descriptionPanel1 = new DescriptionPanel();
    protected BinarySearchTree<Airport> abbrevDatabase = new BinarySearchTree();
    protected BinarySearchTree<Airport> nameDatabase = new BinarySearchTree();
    
    /**
     * Method: AirportDatabaseFrame
     * Purpose: A one argument constructor that initializes and sets up the
     *          UI for the database program. The progName string parameter
     *          contains the name of the UI frame to be displayed in the upper
     *          left hand corner of the UI window.
     * Parameters:
     * @param progName        String containing the name of the airport database
     *                        UI frame.
     */
     public AirportDatabaseFrame(String progName) {
         GUIframe = new JFrame(progName);
         GUIframe.setLayout(new BorderLayout(5, 10));
         GUIframe.setLocationRelativeTo(null);
         GUIframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         GUIframe.setSize(600, 480);
         GUIframe.setVisible(true);
     }
    
     /**
     * Method: westRegionComponents
     * Purpose: A method that configures the west region of the border layout
     *          airport database GUI window.
     * Parameters: N/A
     */
    public void westRegionComponents() {
        //1. create the add record panel, remove panel, and search panel that
        //   will contain the UI components for the add new record, delete record,
        //   and search for a record operations and config there layouts.
        JPanel searchPanel = new JPanel(new GridLayout(3, 0, 5, 5));
        JPanel removePanel = new JPanel(new FlowLayout());
        JPanel addPanel = new JPanel (new FlowLayout());
        removePanel.setBorder(new TitledBorder(null, "Remove An Airport Record"));
        addPanel.setBorder(new TitledBorder(null, "Add A New Airport Record"));
        searchPanel.setBorder(new TitledBorder(null, "Search For An Airport"));
        westPanel = new JPanel(new BorderLayout(5, 5));
        westPanel.setBorder(new TitledBorder
                (new LineBorder(Color.BLUE, 3), "Database Operations"));
        
        //2. Create and configure the input text fields for the add, remove,
        //   and search operations.
        final JTextField abbrevField = new JTextField(3);
        final JTextField nameField = new JTextField(8);
        final JTextField searchField = new JTextField(8);
        final JTextField abbrevRemoveField = new JTextField(3);
        final DescriptionPanel searchOutput = new DescriptionPanel();
        
        // set the document properties on the text fields were an abbreviation
        // input is required to limit the input to three characters.
        abbrevField.setDocument(new JTextFieldLimit(3)); 
        abbrevRemoveField.setDocument(new JTextFieldLimit(3));
        
        //3. Add labels to each text field and add the text field components to
        //   the add, remove, and search panels.
        addPanel.add(new JLabel("Abbreviation", JLabel.LEFT));
        addPanel.add(abbrevField);
        addPanel.add(new JLabel("Name", JLabel.LEFT));
        addPanel.add(nameField);
        
        removePanel.add(new JLabel("Abbreviation", JLabel.LEFT));
        removePanel.add(abbrevRemoveField);
        
        searchPanel.add(new JLabel("Enter A Name Or Abbreviation", JLabel.LEFT));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Search History", JLabel.LEFT));
        searchPanel.add(searchOutput);
        
        //4. Create the interactive buttons used to perform an action
        //   on the data in the text fields for the add, remove, and search
        //   operations.
        JButton jbt = new JButton("Add Record");
        JButton jbt1 = new JButton("Delete Record");
        JButton jbt2 = new JButton("Search By Abbreviation");
        JButton jbt3 = new JButton("Search By Name");
        
        //4a. The first button adds the new airport record from the 
        //    given data submitted in the text fields and reprints the
        //    current state of the abbreviation sorted and name sorted
        //    trees to the center description panels.
        jbt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // create a new airport object with the data input into
                // the abbreviation and name text fields.
                Airport newRecord = new 
                        Airport(abbrevField.getText(), nameField.getText());
                abbrevDatabase.add(newRecord, newRecord.getAbbreviation());
                nameDatabase.add(newRecord, newRecord.getName());
                
                // reprint the databases to the text description panels.
                descriptionPanel0.setDescrip(
                        abbrevDatabase.printInOrder());
                descriptionPanel1.setDescrip(nameDatabase.printInOrder());
             
            }
        });
        
        //4b. The second button removes an airport record from the
        //    database and reprints the
        //    current state of the abbreviation sorted and name sorted
        //    trees to the center description panels.
        jbt1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String result = "";
                String[] split;
                
                // create a bogus airport record from the given data in the
                // text field and search the abbreviation sorted tree for this
                // record.
                Airport temp = new Airport(abbrevRemoveField.getText(), "");
                result = abbrevDatabase.search(temp);
                
                // if the result is a null string, show an information message
                if (result.equals(""))
                    JOptionPane.showMessageDialog(null, "The record does not exist.");
                else {
                    // prompt the user for confirmation to delete the found record
                    int selection = JOptionPane.showConfirmDialog(null, 
                            "Are you sure you want to delete\n " + 
                            result + "?", "Confirmation Window", 
                            JOptionPane.YES_NO_OPTION, 
                            JOptionPane.WARNING_MESSAGE);
                    
                    // if the yes button in the confirmation window is selected...
                    if (selection == JOptionPane.YES_OPTION) {
                        split = result.split(":"); // seperate the abbrev. data
                        split[0] = split[0].trim(); // from the name data in the
                        split[1] = split[1].trim(); // airport record
                        
                        // remove the record from the tree sorted by abbreviation
                        temp = new Airport(split[0], "");
                        abbrevDatabase.delete(temp);
                
                        // remove the record from the tree sorted by name
                        temp = new Airport("", split[1]);
                        nameDatabase.delete(temp);
                    }
                    
                    // reprint the current state of the database on the text
                    // description panels to visually confirm the record has been removed
                    // from both trees
                    descriptionPanel0.setDescrip(
                        abbrevDatabase.printInOrder());
                    descriptionPanel1.setDescrip(nameDatabase.printInOrder());
                    
                    // clear the text field for new input
                    abbrevRemoveField.setText("");
                }
            }
        });
        
        //4b. The third button searches for an airport record in the
        //    database based on abbreviation and returns the 
        //    first instance of a record with the same abbreviation as the
        //    search parameter into the search history description text.
        jbt2.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent e) {
                Airport temp = new Airport(searchField.getText(), "");
                searchOutput.setDescrip(searchByAbbrev(temp));         
            }
        });
        
        //4c. The fourth button searches for an airport record in the
        //    database based on abbreviation and returns multiple 
        //    instances of records with a similar name to the
        //    search parameter into the search history description text.
        jbt3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                Airport temp = new Airport("", searchField.getText());
                searchOutput.setDescrip(searchByName(temp)); 
            }
        });
        
        //5. Add the buttons to their respective panels and add the add new
        //   record panel, remove a record panel, and search for a record panel
        //   to the west region of the GUI frame.
        addPanel.add(jbt);
        removePanel.add(jbt1);
        searchPanel.add(jbt2);
        searchPanel.add(jbt3);
        westPanel.add(addPanel, BorderLayout.NORTH);
        westPanel.add(removePanel);
        westPanel.add(searchPanel, BorderLayout.SOUTH);
        
        GUIframe.add(westPanel, BorderLayout.WEST);
    }
    
    /**
     * Method: centerRegionComponents
     * Purpose: A method that configures the center region of the border layout
     *          airport database GUI window.
     * Parameters: N/A
     */
    public void centerRegionComponents() {
        //1. Initialize and configure the center region of the GUI frame.
        centerPanel = new JPanel(new GridLayout(2, 0, 5, 5));
        centerPanel.setBorder(new TitledBorder(
                new LineBorder(Color.YELLOW, 3), "Database By Abbreviation"));
        
        //2. Divide the center panel using border layout into north, east, south
        //   and west regions and configure the region
        JPanel centerSouthPanel = new JPanel(new BorderLayout(5, 5));
        centerSouthPanel.setBorder(new TitledBorder(null, "Database By Name"));
        
        //3. Add the text description panel that shows the database output
        //   sorted by name in the center region of the southPanel
        centerSouthPanel.add(descriptionPanel1);
        
        //4. Add the text description panel that shows the database output
        //   sorted by abbreviation in the center region of the panel
        centerPanel.add(descriptionPanel0);
        
        //5. Add the centerSouthPanel to the center panel of the GUI frame and
        //   add the panel to the frame. This is so the two text fields
        //   show one on top of the other for an asthtetically pleasing layout.
        centerPanel.add(centerSouthPanel);
        GUIframe.add(centerPanel);
    }
    
    /**
     * Method: northRegionComponents
     * Purpose: A method that configures the north region of the border layout
     *          airport database GUI window as well as creates the menu bar
     *          for the airport database GUI window.
     * Parameters: N/A
     */
    public void northRegionComponents() {
        // 1. Create the menu bar and the menus and menu items associated
        //    with it
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        JMenuItem open = new JMenuItem("Open file...");
        JMenuItem save = new JMenuItem("Save As...");
        JMenuItem helpContents = new JMenuItem("Help Contents");
        JMenuItem about = new JMenuItem("About");
        
        // 2. Add the menu items to the appropriate menus and the menus to the 
        //    menu bar. Finally add the menu bar to the GUI frame.
        fileMenu.add(open);
        fileMenu.add(save);
        helpMenu.add(helpContents);
        helpMenu.add(about);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        GUIframe.setJMenuBar(menuBar);
        
        // 3. When the "Open file..." menu item is clicked in the "File" menu,
        //    open a popup window asking for the user to specify the full path name
        //    of a file to load into the database UI.
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileName = "";
                boolean successOrFail;
                // ask for the user to specify the full path name of the file to load
                fileName = JOptionPane.showInputDialog(null, "Please enter the " +
                        "full path of the file you want to load", 
                        "Enter A File Name", JOptionPane.INFORMATION_MESSAGE);
                
                try {
                    successOrFail = loadFile(fileName);
                    
                    // if the file is not loaded successfully, display an error
                    // message
                    if (!successOrFail)
                        JOptionPane.showMessageDialog(null, "Error: The file " +
                                "does not exist. Please enter a " +
                                "valid file name", "File Not Found",
                                JOptionPane.ERROR_MESSAGE);
                    else {
                        descriptionPanel0.setDescrip(abbrevDatabase.printInOrder());
                        descriptionPanel1.setDescrip(nameDatabase.printInOrder());
                    }
                }
                // display error messages for the following exceptions
                catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Error: The file " +
                                "does not exist. Please enter a " +
                                "valid file name", "File Not Found",
                                JOptionPane.ERROR_MESSAGE);
                }
                catch (FileDataFormatException ex1) {
                    JOptionPane.showMessageDialog(null, "Error: The data " +
                                "in the file is formatted incorrectly " +
                                "and will not be loaded.\n Please " +
                                "fix the error (Hint: Check to see if " +
                                "the delimiter between the abbreviation\n " +
                                "data and the name data is a '/'"
                                , "File Not Found",
                                JOptionPane.ERROR_MESSAGE);
                }
                        
            }
        });
        
        // 3a. When the "Save As..." menu item is clicked in the "File" menu,
        //    open a popup window asking for the user to specify a file name
        //    to save the current database too.
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 String fileName = ""; // file path as entered by user
                 PrintWriter output; // PrintWriter obeject that writes abbrev tree 
                            // to a file.
                 fileName = JOptionPane.showInputDialog(null, "Please specify " +
                         "a file name to save the database " +
                         "in. (Please include the file extension)\n" +
                         "THIS PROGRAM ONLY SAVES TEXT FILES!\n" +
                         "USE .txt AS YOUR FILE EXTENSION!"
                         , "Save As...", JOptionPane.INFORMATION_MESSAGE);
        
                 File file = new File(fileName);
        
                 if (!(file.exists())) { // if the file isn't created, don't save
                    try {
                        String[] printedDatabase;
                        String result = "";
                        output = new PrintWriter(file);
                        
                        // Save tree in pre-order so it can be recreated upon loading.
                        result = abbrevDatabase.printPreOrder();
                        printedDatabase = result.split("\n");
                        
                        for (int pos = 0; pos < printedDatabase.length; pos++)
                            output.println(printedDatabase[pos]);
                        
                        output.close();
                        
                        JOptionPane.showMessageDialog(null, "File Saved Successfully!",
                            "Confirmation Window", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error: File does not" +
                                "exist.", "File Not Found", JOptionPane.ERROR_MESSAGE);
                    }
                 }
                 else
                     JOptionPane.showMessageDialog(null, "Error: A file" +
                             " with this name already exists. Please" +
                             " specify a new file name.", "File Exists",
                             JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // 3b. When the "Help Contents" menu item is clicked in the "Help" menu,
        //     display a new window with a description panel displaying information
        //     on how to use the program.
        helpContents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //1. Create a new frame with border layout
                JFrame helpWindow = new JFrame("Help Contents");
                helpWindow.setLayout(new BorderLayout(5, 10));
                helpWindow.setLocationRelativeTo(null);
                helpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                helpWindow.setSize(400, 400);
                helpWindow.setVisible(true);
                
                //2. Create a panel within the new frame with a title.
                JPanel helpPanel = new JPanel(new BorderLayout());
                helpPanel.setBorder(new TitledBorder(null, "Help"));
                
                //3. Add a text description panel to the panel within the new
                //   frame.
                DescriptionPanel helpInstructions = new DescriptionPanel();
                
                //4. Set the descripton panel to display the following help
                //   information
                helpInstructions.setDescrip("WELCOME TO THE AIRPORT DATABASE PROGRAM!\n" +
                   "Author: Jacob Howarth\n\n" + "Section I: Operations\n\n" +
                   "**Adding A New Airport Record**\n" +
                   "----------------------------------------------------\n" +
                   "1. Click on the first text field, titled \"Abbreviation\" " +
                   "in the \"Add A New Record\" area at the upper, left " +
                   "corner of the window and type in a three letter abbreviation.\n" +
                   "2. Click on the next text field, titled \"Name\" (or press the " +
                   "TAB key) and enter the name of the airport.\n" +
                   "3. Click the \"OK\" button and the record will be added to the two text " +
                   "fields marked \"Database By Abbreviation\" and " +
                   "\"Database By Name\" on the left side of the window. " +
                   "These areas represent the databases current state, sorted " +
                   "by name (bottom text area) and abbreviation (upper text area).\n\n" +
                   "**Deleting A Record**\n" +
                   "-----------------------------------------------------\n" +
                   "1. Click in the text field titled \"Abbreviation\" in the " +
                   "\"Delete An Airport Record\" area on the left side of the " +
                   "window and enter a three character abbreviation code for " +
                   "the record you want to delete.\n" +
                   "2. Click \"OK\" button and a confirmation window will appear " +
                   "asking if you really want to delete the record if the record " +
                   "exists.\n" +
                   "3. Click the \"YES\" button and the record will be removed " +
                   "from the database or \"NO\" to stop the deletion.\n\n" +
                   "**Search For A Record**" +
                   "-----------------------------------------------------\n" +
                   "1. Click in the text field titled \"Search By Abbreviation " +
                   "or Name\" and enter an airport abbreviation or name that " +
                   "is currently in the database.\n" +
                   "2. Click either the \"Search Abbreviation\" button or the " +
                   "\"Search Name\" button and the text area titled \"Search " +
                   "History\" will be filled with either\n" +
                   "    a) The first instance of a record when searching\n" +
                   "    by abbreviation.\n" +
                   "    b) All the instances (if there are more than one)\n" +
                   "    of records with the same name when searching\n" +
                   "    the database by name.\n" +
                   "3. The \"Search History\" text field will be CLEARED if another " +
                   "search is performed for a different abbreviation or name.\n\n" +
                   "Section II: Menu Bar Functions\n\n" +
                   "**Loading a Text File**\n" +
                   "-----------------------------------------------------\n" +
                   "1. Go to File -> Open File...\n" +
                   "2. A window will appear asking you to enter the full path " +
                   "of the .txt file to load\n" +
                   "3. The data will show up in the \"Database By Abbreviation\" " +
                   "and the \"Database By Name\" text areas if the file was " +
                   "loaded successfully.\n" +
                   "4. If the file could not be found or an error occured while " +
                   "loading the text file, then an error message will appear " +
                   "and nothing will be done.\n\n" +
                   "**Saving A File**\n" +
                   "------------------------------------------------------\n" +
                   "1. Go to File -> Save As...\n" +
                   "2. A window will appear prompting for a file name to " +
                   "save the database.\n" +
                   "3. After a file name is specified, a confirmation window " +
                   "will appear telling the user if the file was successfully " +
                   "saved or an error occured. (The text file will be saved in " +
                   "the current directory).\n");
                
                //5. Add the panel to the new frame
                helpPanel.add(helpInstructions);
                helpWindow.add(helpPanel);
            }
        });
        
        // 3c. When the "About" menu item is clicked in the "Help" menu,
        //     display a popup window with general information about the program.
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "AIRPORT DATABASE PROGRAM" +
                        " alpha build v0.1\n" +
                        "Author: Jacob S. Howarth\n" +
                        "Kettering University\n" +
                        "CS-102: Lab Assignment 5\n" +
                        "Professor Huggins\n" +
                        "My Email: howa1643@kettering.edu", "About This Program",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
    }
    
    /**
     *
     * Method: searchByAbbrev                                                   
     * Description: A method called when the "Search By Abbreviation" button
     *              is clicked in the search panel with a given text field input.
     *              
     *              The method returns the first instance of the record 
     *              with the same abbreviation as the dummy airport object
     *              parameter or a confirmation window saying the record was
     *              not found.
     * Parameters:
     * @param input     A dummy airport object with the abbreviation input
     *                  in the text field contained in the search panel and
     *                  no name.
     * @return result   The resulting string representing the airport record,
     *                  if found, to be printed into the "Search History"
     *                  text description panel.                                                                                                                                
     *
     */ 
    private String searchByAbbrev(Airport input) {
        String result;
        result = abbrevDatabase.search(input);
                
        if (result.equals(""))
            JOptionPane.showMessageDialog(null, "The record doesn't exist",
                            "Confirmation Window", JOptionPane.INFORMATION_MESSAGE);
        else {
            return result;
        }
        
        return "";
    }
    
    /**
     *
     * Method: searchByName                                                   
     * Description: A method called when the "Search By Name" button
     *              is clicked in the search panel with a given text field input.
     *              
     *              The method returns multiple instances of airport records 
     *              with a similar name as the dummy airport object
     *              parameter or a confirmation window saying the record was
     *              not found.
     * Parameters:
     * @param input     A dummy airport object with the name input
     *                  in the text field contained in the search panel and
     *                  no abbreviation.
     * @return result   The resulting string representing the airport record,
     *                  if found, to be printed into the "Search History"
     *                  text description panel.                                                                                                                                
     *
     */ 
    private String searchByName(Airport input) {
        String result;
        result = nameDatabase.inOrderSearch(input);
        
        if (result.equals(""))
            JOptionPane.showMessageDialog(null, "The record doesn't exist",
                            "Confirmation Window", JOptionPane.INFORMATION_MESSAGE);
        else {
            return result;
        }
        
        return "";
    }
    
    /**
     *
     * Method: loadFile                                                    
     * Description: This method loads the airport database UI description
     *              panels with data parsed from a text file.
     *                                                                                
     * Parameters:                                                   
     *  @param fileName A string representing the file name containing the
     *                   data to load into the database.
     * Returns:
     * @return successOrFail - A boolean value that determines if the file
     *                         loaded successfully or not.
     *
     */ 
    private boolean loadFile(String fileName) throws 
                              FileDataFormatException, FileNotFoundException {
        //1. Create the file
        Scanner fileInput;
        File file = new File(fileName);
        boolean successOrFail; // return true if file loaded successfully, otherwise return false
        String record = ""; // record holds an airport record from the file 
        String[] split; // array to split the abbrev data from the name data
        
        //2. Check if the file doesn't exist
        if (!(file.exists())) {
            successOrFail = false; // return false in this case and end method
            return successOrFail;
        }
        
        //3. Attempt to read the file
        fileInput = new Scanner(file);
        
        //4. Initialize tree sorted by abbreviations and tree sorted by names
        abbrevDatabase = new BinarySearchTree();
        nameDatabase = new BinarySearchTree();
        
        // 5. Begin file reading.
        while (fileInput.hasNext()) {
            record = fileInput.nextLine();
            
            //6. Error check the format
            if (record.charAt(3) != '/') {
                throw new FileDataFormatException();
            }
            else { // 7. Create the airport object from the file data
                split = record.split("/");
                split[0] = split[0].trim();
                split[1] = split[1].trim();
                
                Airport newAirport = new Airport(split[0], split[1]);
                
                // 8. Add each record to both lists in sorted order by key
                abbrevDatabase.add(newAirport, newAirport.getAbbreviation());
                nameDatabase.add(newAirport, newAirport.getName());
            }
        }
        
        // If the file loaded successfully, return true
        successOrFail = true;
        return successOrFail;       
    }
    
}

