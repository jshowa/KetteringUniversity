/**
 * Name: Jacob Howarth
 * Course: CS-203, Spring 2009
 * Class Name: Vertex
 *
 * Description: This class is a general vertex object used for graphs. It contains
 *              the vertex data and the index of the vertex.
 *
 * @author jshowa
 */
public class Vertex {
    private String element; // vertex data
    private int index; // index of vertex, used to make it easier to process in tree

    /**
     * Method: Vertex
     * Description: Vertex constructor initiailized with a data element of type
     *              String.
     * Parameters:
     * @param element - String containing the vertex data
     */
    public Vertex(String element) {
        this.element = element;
        this.index = -1;
    }

    /**
     * Method: Vertex
     * Description: Vertex constructor initiailized with a data element of type
     *              String and an index value.
     * Parameters:
     * @param element - String containing the vertex data
     * @param index - integer value representing the index used for tree structure
     */
    public Vertex(String element, int index) {
        this.element = element;
        this.index = index;
    }


    /**
     * Method: getVertexIndex
     * Description: Accessor method that returns the index of a vertex
     * Returns:
     * @return int - an integer representing the index of the vertice
     */
    public int getVertexIndex() {
        return index;
    }

    /**
     * Method: getElement
     * Description: Accessor method that returns the data element of the vertex
     * Returns:
     * @return String - A String representing the data stored in the vertex
     */
    public String getElement() {
        return element;
    }

    /**
     * Method: equals
     * Description: Overriden equals method that checks to see if two
     *              Vertex objects are equal by checking their data elements
     * Returns:
     * @return boolean - true if there data elements equate, false if not
     */
    public boolean equals(Vertex v) {
        if ((this.getElement()).equalsIgnoreCase(v.getElement()))
            return true;
        else
            return false;
    }

    /**
     * Method: toString
     * Description: Overriden toString method that returns the data of a vertex
     *              object in it's string representation.
     * Returns:
     * @return String - string representation of a Vertex object
     */
    @Override
    public String toString() {
        return element.toString();
    }

}
