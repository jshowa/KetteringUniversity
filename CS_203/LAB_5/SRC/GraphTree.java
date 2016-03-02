import java.util.*;

/**
 * Name: Jacob Howarth
 * Course: CS-203, Spring 2009
 * Class Name: GraphTree
 *
 * Description: This class creates a tree structure, which is a spanning
 * tree of a graph G with a specified root and vertices adjacent to that root.
 * The tree structure is mainly created from a BFS or DFS traversal.
 *
 * @author jshowa
 */
public class GraphTree {
    private int root; // root vertex of the tree
    private int[] parent; // an array that contains the parent of each vertice in the tree
    private List<Integer> searchOrder; // the order each vertice was searched in during tree construction
    private Vertex[] vertices; // vertices contained in the graph

    /**
     * Method: GraphTree
     * Description: A constructor used to create a tree with a root vertex,
     *              the parents of each vertice in the tree, the search order
     *              of vertices used to create the tree, and all the vertices in
     *              the graph.
     * Parameters:
     * @param root - root vertex of tree
     * @param parent - integer array containing the parent indexes of the vertices in the tree
     * @param searchOrder - the order each vertice was searched in construction of the tree
     * @param vertices - vertices in the graph
     */
    public GraphTree(int root, int[] parent, List<Integer> searchOrder, Vertex[] vertices) {
        this.root = root;
        this.parent = parent;
        this.searchOrder = searchOrder;
        this.vertices = vertices;
    }

    /**
     * Method: GraphTree
     * Description: Same as the method above except the order the vertices were
     *              searched in to constuct the tree is not kept.
     * Parameters:
     * @param root - root vertex of tree
     * @param parent - integer array containing the parent indexes of the vertices in the tree
     * @param vertices - vertices in the graph
     */
    public GraphTree(int root, int[] parent, Vertex[] vertices) {
        this.root = root;
        this.parent = parent;
        this.vertices = vertices;
    }

    /**
     * Method: getRoot
     * Description: Returns the root of the spanning tree.
     * Returns:
     * @return int - integer representing the index of the root vertice
     */
    public int getRoot() {
        return root;
    }

    /**
     * Method: getParent
     * Description: Returns the index of the parent vertice given the index
     *              of a vertice in the tree.
     * Parameters:
     * @param v - integer representing the index of a vertex in the tree
     * Returns:
     * @return int - integer representing the index of the parent vertice of vertice v
     */
    public int getParent(int v) {
        return parent[v];
    }

    /**
     * Method: searchOrder
     * Description: Returns the order in which the vertices were examined to construct
     *              the spanning tree.
     * Returns:
     * @return List - integer list holding the indexes of each node traveled to
     *                create the tree.
     */
    public List<Integer> getSearchOrder() {
        return searchOrder;
    }

    /**
     * Method: getNumVerticesFound
     * Description: Returns the number of vertices used to construct the tree.
     * Returns:
     * @return int - integer representing the number of vertices in the tree.
     */
    public int getNumVerticesFound() {
        return searchOrder.size();
    }

    /**
     * Method: pathIterator
     * Description: Returns an iterator that computes a path from vertex index v
     *              to the root vertice.
     * Parameters:
     * @param v - integer representing the index of a vertex in the tree
     * Returns:
     * @return Iterator - integer list holding the indexes of each node traveled to
     *                create the tree.
     */
    public Iterator pathIterator(int v) {
        return new PathIterator(v);
    }

    /**
     * Name: Jacob Howarth
     * Inner Class Name: PathIterator
     *
     * Description: An inner class that generates a path from a given vertice
     *              index v to the root using an iterator interface.
     *
     * @author jshowa
     */
    public class PathIterator implements Iterator {
        private Stack<Integer> stack; // stack that stores the vertice indexes in the path

        /**
         * Method: PathIterator
         * Description: A PathIterator constructor used to create a path from
         *              a specific vertex index v to the root vertex.
         * Parameters:
         * @param v - integer representing the vertex index of which to generate
         *            a path to the root from.
         */
        public PathIterator(int v) {
            stack = new Stack<Integer>();
            do {
                stack.add(v);
                v = parent[v];
            }
            while(v != -1); // parent array is originally initialized to -1 in BFS and DFS
                            // methods in UnDirectGraph class.
        }

        /**
         * Method: hasNext
         * Description: Checks if there is another vertex in the stack in the path
         *              to the root.
         * Returns:
         * @return boolean - true if there is another vertice on the way to the root
         *                   false if the root has been reached.
         */
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Method: next
         * Description: Returns a vertex in the path from the starting vertex
         *              to the root.
         * Returns:
         * @return Vertex - Vertex object of a vertice in the path to the root
         */
        public Vertex next() {
            return vertices[stack.pop()];
        }

        // Method part of Iterator interface but not implemented.
        public void remove() {}
    }

    /**
     * Method: printPath
     * Description: Prints a path from the vertex index v to the root of the
     *              tree.
     * Parameters:
     * @param v - integer representing the index of a vertex in the tree to start
     *            the path to the root from.
     */
    public void printPath(int v) {
        PathIterator iterator = new PathIterator(v);
        boolean secondSeq = false;
        Vertex temp = null;
        System.out.print("Path (" + vertices[root] + "," + vertices[v] + ") = [" );

        if (parent[v] == -1) {
            System.out.println("DNE]");             // if a vertex v has no path to root i.e. no parent
            return;                                 // there is no path from vertex v to root.
        }
        else {

            while (iterator.hasNext()) {
                temp = iterator.next();
                // return path in special sequence as specified in requirements
                // sequence ex. (A,C) = [A,(A,B),B,(B,C),C]
                if (!secondSeq) {
                    System.out.print(temp + ",(" + temp + ",");
                    secondSeq = true;
                }
                else if (!iterator.hasNext())
                    System.out.println(temp + ")," + temp + "]");
                else
                    System.out.print(temp + ")," + temp + ",(" + temp + ",");
            }

            
        }
    }
}
