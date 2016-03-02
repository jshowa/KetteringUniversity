import java.util.*;
import java.io.*;

/**
 * Name: Jacob Howarth
 * Course: CS-203, Spring 2009
 * Class Name: GraphFileReader
 *
 * Description: This class reads a text file containing graph information
 *              and parses that information in a specific format. An example
 *              format is the following:
 *
 *                  V = {A, B, C, D}
 *                  (A,B)
 *                  (B,A)
 *                  (A,C)
 *                  (C,A)
 *                  etc...
 *
 * @author jshowa
 */
public class GraphFileReader {

    private File file; // file object created for graph file
    private String filename; // path to the file
    private UnDirectGraph graph  = null; // new graph object

    /**
     * Method: GraphFileReader
     * Description: A constructor that sets the file name of the file to be
     *              read and creates the File object assoc. with the path name.
     * Parameters:
     * @param filename - String representing the path to the file to be read.
     */
    public GraphFileReader(String filename) {
        this.filename = filename;
        file = new File(filename);
    }

    /**
     * Method: read
     * Description: A method that reads the file and creates the graph object from
     *              the information contained in the file, i.e. creates vertice
     *              and edge list.
     * Returns:
     * @return boolean - true if file was read without error, false if failed to read
     */
    public boolean read() throws FileNotFoundException {
        Scanner fileScan; // scanner to read file data
        String currentLine, graphNameAndVertices = "", 
                graphName = "", tempStr = "";
        String[] edgePair;
        boolean parsedFirstLine = false; // check if vertices have been parsed (first line of file)
        
        ArrayList<Vertex> vertices = new ArrayList<Vertex>(); // new graph vertice list
        ArrayList<Edge> edges = new ArrayList<Edge>(); // new graph edge list
        Edge newEdge; // temp edge object

        // check if file exists
        if (file.exists() && file.length() != 0L)
            fileScan = new Scanner(file);
        else
            return false;

        while (fileScan.hasNextLine()) {
            // Read in line from file
            currentLine = fileScan.nextLine();

            // Parse the input line and gather necessary information
            if (!parsedFirstLine) {
                graphNameAndVertices = parseVerticeInfo(currentLine);

                // gather graph name and vertice info first
                for (int i = 0; i < graphNameAndVertices.length(); i++) {
                    tempStr = graphNameAndVertices.substring(i, i + 1);
                    if (i == 0)
                        graphName += tempStr; // add name (first char in graphNameAndVertice is graph name)
                    else
                        vertices.add(new Vertex(tempStr, i - 1)); // add vertices
                }

                parsedFirstLine = true;
            }
            else {

                // gather edge information
                edgePair = parseEdgeInfo(currentLine);
                newEdge = new Edge();
                for (int i = 0; i < vertices.size(); i++) {
                    if (vertices.get(i).getElement().equals(edgePair[0]))
                        newEdge.setVertex_U(vertices.get(i));
                    if (vertices.get(i).getElement().equals(edgePair[1]))
                        newEdge.setVertex_V(vertices.get(i));
                }

                // add new edge
                edges.add(newEdge);
            }  
        } // end while file reader

        if (!fileScan.hasNextLine() && edges != null && vertices != null) {
            this.graph = new UnDirectGraph(graphName, edges, vertices);
            return true;
        }
        else
            return false;
    }

    /**
     * Method: parseVerticeInfo
     *
     * Description: Method that parses the vertice info given a line from
     *              the file. Usually the first line in the file.
     *
     * Parameters:
     * @param line - line from file containing vertice info of graph.
     *
     * Returns:
     * @return String - A string with readable vertice info.
     */
    private String parseVerticeInfo(String line) {
        String[] delimitedLine;
        String result = "";

        line = line.replaceAll("\\s", ""); // remove white space
        delimitedLine = line.split("\\W+"); // split the string by non-word char delimiter
        for (int i = 0; i < delimitedLine.length; i++)
                result += delimitedLine[i];

        return result;
    }

    /**
     * Method: parseEdgeInfo
     *
     * Description: Method that parses the edge pair info given a line from
     *              the file. Usually all lines following from the first to EOF.
     *
     * Parameters:
     * @param line - line from file containing vertice info of graph.
     *
     * Returns:
     * @return String[] - A String array containing parsed vertices that represent
     *                    the two endpoints of an edge.
     */
    private String[] parseEdgeInfo(String line) {
        String[] delimitedLine = new String[2]; // all edges have two vertices
        line = line.replaceAll("\\W", ""); // replace non-word chars with white space
        delimitedLine[0] = line.substring(0, 1);
        delimitedLine[1] = line.substring(1, 2);
        return delimitedLine;
    }

    /**
     * Method: getGraph
     *
     * Description: Accessor method that returns the undirected graph created
     *              from the file data.
     *
     * Returns:
     * @return UnDirectGraph - An undirected graph object.
     */
    public UnDirectGraph getGraph() {
        return graph;
    }



}
