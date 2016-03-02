/**
 * Name: Jacob Howarth
 * Course: CS-203, Spring 2009
 * Class Name: Edge
 *
 * Description: This class is a general edge object that specifies an edge
 *              between two Vertex objects. It returns the the Vertexs objects
 *              adjacent to the edge.
 *
 * @author jshowa
 */
public class Edge<T> {
    private Vertex u; // beginning vertice of the incident edge
    private Vertex v; // end vertice of the incident edge

    /**
     * Method: Edge
     * Description: Zero arg constructor that creates an edge with two Vertex object
     *              endpoints, u and v initialized to null.
     */
    public Edge() {
        u = v = null;
    }

    /**
     * Method: Edge
     * Description: Constructor that creates an edge with two Vertex object
     *              endpoints, u and v.
     * Parameters:
     * @param u - beginning Vertex of the edge
     * @param v - end Vertex of the edge
     */
    public Edge(Vertex u, Vertex v) {
        this.u = u;
        this.v = v;
    }

    /**
     * Method: getVertex_U
     * Description: Accessor method that returns the beginning Vertex object
     *              of the edge.
     * Returns:
     * @return Vertex - beginning vertex of the edge.
     */
    public Vertex getVertex_U() {
        return u;
    }

    /**
     * Method: getVertex_V
     * Description: Accessor method that returns the end Vertex object
     *              of the edge.
     * Returns:
     * @return Vertex - end vertex of the edge.
     */
    public Vertex getVertex_V() {
        return v;
    }

    /**
     * Method: setVertex_U
     * Description: Mutator method that sets the beginning Vertex object
     *              of the edge.
     * Parameters:
     * @param u - new beginning Vertex object of the edge.
     */
    public void setVertex_U(Vertex u) {
        this.u = u;
    }

    /**
     * Method: setVertex_V
     * Description: Mutator method that sets the end Vertex object
     *              of the edge.
     * Parameters:
     * @param u - new end Vertex object of the edge.
     */
    public void setVertex_V(Vertex v) {
        this.v = v;
    }

    /**
     * Method: toString
     * Description: Overridden toString method that returns a String representation
     *              of the Edge object, i.e. it prints the beginning an end vertices
     *              that are adjacent to the edge.
     * Returns:
     * @return String - String containing the beginning and end vertices of the edge.
     */
    @Override
    public String toString() {
        return "( " + u.getElement().toString() + ", " +
                v.getElement().toString() + ") ";
    }
}
