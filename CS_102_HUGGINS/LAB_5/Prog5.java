import javax.swing.*;
/**
 * Name: Jacob S. Howarth
 * Login ID: howa1643
 * CS-102, Fall 2008
 * Programming Assignment 5
 * Prog5 class: The main program that runs the Airport Database GUI for a
 * database containing airport records.
 *
 */
public class Prog5 {
    public static void main(String[] args) {
        AirportDatabaseFrame databaseProgram = new AirportDatabaseFrame("Airport Database Program");
        databaseProgram.northRegionComponents(); // initialize northern region of the GUI
                                                  // that uses border layout
        databaseProgram.westRegionComponents(); // initialize the western region of GUI
        databaseProgram.centerRegionComponents(); // initialize the center region of GUI
    }
}

