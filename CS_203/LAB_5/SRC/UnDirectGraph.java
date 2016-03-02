import java.util.*;

/**
 * Name: Jacob Howarth
 * Course: CS-203, Spring 2009
 * Class Name: UnDirectGraph
 *
 * Description: This class creates an undirected graph object.
 *
 * @author jshowa
 */
public class UnDirectGraph {
    private Vertex[] vertices; // vertices in the graph
    private List<Vertex>[] neighbors; // adjacency list of each vertice in the graph
    private String graphName; // graph name
    private List<Edge> edges; // graph edges

    /**
     * Method: UnDirectGraph
     * Description: UnDirectGraph constructor that creates a graph with a given
     *              name, edges, and vertices by creating an adjacency list with
     *              the edge and vertice objects.
     * Parameters:
     * @param graphName - String containing the vertex data
     * @param edges - List of Edge objects used to create adjacency list for vertices
     * @param vertices - Vertex list containing vertices of the graph
     */
    public UnDirectGraph(String graphName, List<Edge> edges, List<Vertex> vertices) {
        this.graphName = graphName;
        this.vertices = vertices.toArray(new Vertex[0]);
        this.edges = edges;
        createAdjacencyLists(edges, vertices.size());
    }

    /**
     * Method: UnDirectGraph
     * Description: UnDirectGraph constructor that creates a graph with a given
     *              name, vertices and prebuilt adjacency list.
     * Parameters:
     * @param graphName - String containing the vertex data
     * @param neighbors  - Prebuilt adjacency list for a graph
     * @param vertices - Vertex list containing vertices of the graph
     */
    public UnDirectGraph(String graphName, List<Vertex>[] neighbors, Vertex[] vertices) {
        this.graphName = graphName;
        this.neighbors = neighbors;
        this.vertices = vertices;
    }

    /**
     * Method: createAdjacencyLists
     * Description: Creates an adjacency list for a graph given a number
     *              of vertices and a list of edges for the graph.
     * Parameters:
     * @param edges - List of Edge objects representing the connections
     *                between the vertices in the graph.
     * @param numberOfVertices - integer representing the number of vertices
     *                           in the graph.
     */
    public void createAdjacencyLists(List<Edge> edges, int numberOfVertices) {
        // instantiate array of linked lists
        neighbors = new LinkedList[numberOfVertices];

        // create a linked list of vertexs for each position in neighbor list
        // which contains each vertex in the graph
        for (int i = 0; i < numberOfVertices; i++) {
            neighbors[i] = new LinkedList<Vertex>();
        }

        for (Edge edge: edges) {
            neighbors[edge.getVertex_U().getVertexIndex()].add(edge.getVertex_V());
        }
    }

    public UnDirectGraph graphDeepCopy() {
        String nameCopy;
        List<Vertex>[] neighborCopy = new LinkedList[neighbors.length];
        Vertex[] verticeCopy = new Vertex[vertices.length];

        for (int i = 0; i < neighbors.length; i++)
            neighborCopy[i] = new LinkedList<Vertex>();

        for (Edge edge: this.edges) {
            neighborCopy[edge.getVertex_U().getVertexIndex()].add(new Vertex(edge.getVertex_V().getElement(),
                    edge.getVertex_V().getVertexIndex()));
        }

        for (int j = 0; j < vertices.length; j++)
            verticeCopy[j] = new Vertex(this.getVertex(j).getElement(), this.getVertex(j).getVertexIndex());

        nameCopy = this.getGraphName();

        return new UnDirectGraph(nameCopy, neighborCopy, verticeCopy);

    }

    /**
     * Method: getGraphName
     * Description: Accessor method that returns the name of a undirected
     *              graph object.
     * Returns:
     * @return String - undirected graph object's name
     */
    public String getGraphName() {
        return graphName;
    }

    /**
     * Method: getNumberOfVertices
     * Description: Accessor method that returns the number of vertices in the
     *              undirected graph object.
     * Returns:
     * @return int - an integer representing the number of vertices in the graph
     */
    public int getNumberOfVertices() {
        return vertices.length;
    }

    /**
     * Method: getVertices
     * Description: Accessor method that returns the vertices of an
     *              undirected graph.
     * Returns:
     * @return Vertex[] - the Vertex object array containing the vertices
     *                    of the undirected graph object.
     */
    public Vertex[] getVertices() {
        return vertices;
    }

    /**
     * Method: getVertex
     * Description: Accessor method that returns the Vertex object from
     *              the graph vertice array given a Vertex index v.
     * Parameters:
     * @param v - an integer representing the index of a vertice
     * Returns:
     * @return Vertex - A Vertex object with vertex index v
     */
    public Vertex getVertex(int v) {
        return vertices[v];
    }

    /**
     * Method: getIndexOfVertex
     * Description: Accessor method that returns the index of a vertex.
     *
     * Parameters:
     * @param v - A Vertex object
     * Returns:
     * @return int - an integer representing the index of the parameter Vertex
     *               object
     */
    public int getIndexOfVertex(Vertex v) {
        for (int i = 0; i < vertices.length; i++)
            if (v.equals(vertices[i]))
                return i;
        return -1;
    }

    /**
     * Method: getAdjacencyList
     * Description: Accessor method that returns the adjacency list of all
     *              vertices in the undirected graph object.
     * Returns:
     * @return List<Vertex> - Adjacency list of the undirected graph object
     */
    public List<Vertex>[] getAdjacencyList() {
        return neighbors;
    }

    /**
     * Method: getAdjListForVertex
     * Description: Accessor method that returns the adjacency list of a
     *              specific vertex index v.
     * Parameters:
     * @param v - an integer representing the index of the vertice of which
     *            to acquire the adjacency list of.
     */
    public List<Vertex> getAdjListForVertex(int v) {
        return neighbors[v];
    }

    /**
     * Method: getDegree
     *
     * Description: Accessor method that returns the degree of a vertex
     *
     * Parameters:
     * @param v - integer representing a Vertex object index.
     * Returns:
     * @return int - an integer representing the number of the edges incident
     *               to vertex v.
     */
    public int getDegree(int v) {
        return neighbors[v].size();
    }

    /**
     * Method: getPath
     *
     * Description: Accessor method that returns a path between source and
     *              destination vertices in a graph along with vertices to
     *              avoid if any are specified.
     *
     * Parameters:
     * @param src - vertice index representing the path source vertex
     * @param dest - vertice index representing the path destination vertex
     * @param avoidVert - array of vertice indexes to avoid
     */
    public void getPath(int src, int dest, int[] avoidVert) {
        GraphTree tree = DFS(src, avoidVert); // get path from DFS tree
        tree.printPath(dest);
    }

    /**
     * Method: getConnectedComponents
     *
     * Description: Accessor method that returns the connected components
     *              of a undirected graph.
     *
     * Returns:
     * @return List - A list of undirected subgraph objects
     */
    public List getConnectedComponents() {
        ArrayList<UnDirectGraph> componentList = new ArrayList<UnDirectGraph>();
        GraphTree currTree; // BFS generated tree
        List<Vertex>[] compNeighbors;
        Vertex[] compVertices;
        // marker keeps track of what vertices and adj. lists need to be added to the component
        // graphs.
        int marker = 0, labelName = 1, tempMarker = 0;

        while (marker < vertices.length) {
            currTree = BFS(marker);

            // if all the vertices are not in the BFS tree
            if (currTree.getNumVerticesFound() <= vertices.length) {

                    // create a new graph with it's component adjacency list and
                    // vertices.
                    compNeighbors = new LinkedList[currTree.getNumVerticesFound()];
                    compVertices = new Vertex[currTree.getNumVerticesFound()];

                    // get component adjacency lists from component graph
                    for (int i = 0; i < compNeighbors.length; i++) {
                        compNeighbors[i] = this.getAdjListForVertex(tempMarker);
                        tempMarker++;
                    }

                    tempMarker = marker;

                    // get component vertices from component graph
                    for (int i = 0; i < compVertices.length; i++) {
                        compVertices[i] = this.getVertex(tempMarker);
                        tempMarker++;
                    }

                    // add new graph to list
                    componentList.add(new UnDirectGraph(this.getGraphName() + labelName,
                            compNeighbors, compVertices));

                    labelName++;
                    marker += currTree.getNumVerticesFound();
                    tempMarker = marker;
            }

        }

        return componentList;
       
    }

    public Stack<Integer> findEulerianTour(int v) {
        Stack<Integer> tour = new Stack<Integer>();
        int oddVertCount = 0;
        int oddVertStart = 0;

        // check to make sure there are two odd vertices only, if not there is no tour
        // mark odd vert start
        for (int i = 0; i < vertices.length; i++)
            if (getDegree(i) % 2 == 1) {
                oddVertStart = i;
                oddVertCount++;
            }


        if (oddVertCount == 2)
            findEulerianTour(tour, oddVertStart);
        else
            findEulerianTour(tour, v);

        return tour;
       
    }

    private void findEulerianTour(Stack<Integer> tour, int u) {
        if (neighbors[u].size() == 0) // if vertex has no neighbors, push on stack
            tour.push(u);
        else {
            while (neighbors[u].size() > 0) { // if vertex u has neighbors, process them by
                int edge = neighbors[u].get(neighbors[u].size() - 1).getVertexIndex();
                neighbors[u].remove(neighbors[u].size() - 1); // removing them from the graph

                // remove the symmetric portion as well, i.e. remove the edge at the other
                // vertice list represented by the index edge
                for (int i = 0; i < neighbors[edge].size(); i++) {
                    if (neighbors[edge].get(i).getVertexIndex() == u) {
                        neighbors[edge].remove(i);
                        break;
                    }
                }

                findEulerianTour(tour, edge); // recurse on the neighbor

            }
            tour.push(u); // when finished deleting all neighbors, push on stack
        }

    }

    /**
     * Method: printAdjacencyLists
     *
     * Description: Accessor method that returns the degree of a vertex
     *
     * Parameters:
     * @param v - integer representing a Vertex object index.
     * Returns:
     * @return int - an integer representing the number of the edges incident
     *               to vertex v.
     */
    public void printAdjacencyLists() {
        System.out.print(graphName + " = { ");

        for (int i = 0; i < neighbors.length; i++) {
            if (i == neighbors.length - 1) 
                System.out.print(vertices[i].toString() + " }");
            else
                System.out.print(vertices[i].toString() + ", ");
        }

        System.out.println();

        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[i].size(); j++) {
                System.out.println("(" + vertices[i].getElement().toString() + ", " +
                    neighbors[i].get(j).getElement().toString() + ")");
            }
        }
    }

    public GraphTree DFS(int v, int[] vertAvoid) {
        List<Integer> searchOrder = new ArrayList<Integer>();
        int[] parent = new int[vertices.length];
        for (int i = 0; i < parent.length; i++)
            parent[i] = -1;

        boolean[] visited = new boolean[vertices.length];

        if (vertAvoid != null)
            for (int i = 0; i < vertAvoid.length; i++)
                visited[vertAvoid[i]] = true;

        DFS(v, parent, searchOrder, visited);

        return new GraphTree(v, parent, searchOrder, vertices);
    }

    private void DFS(int v, int[] parent, List<Integer> searchOrder, boolean[] visited) {
        searchOrder.add(v);
        visited[v] = true;

        for (Vertex w : neighbors[v]) {
            if (!visited[w.getVertexIndex()]) {
                parent[w.getVertexIndex()] = v;
                DFS(w.getVertexIndex(), parent, searchOrder, visited);
            }
        }
    }

    /**
     * Method: printAdjacencyLists
     *
     * Description: Accessor method that returns the degree of a vertex
     *
     * Parameters:
     * @param v - integer representing a Vertex object index.
     * Returns:
     * @return int - an integer representing the number of the edges incident
     *               to vertex v.
     */
    public GraphTree BFS(int v) {
        List<Integer> searchOrder = new ArrayList<Integer>();
        int[] parent = new int[vertices.length];
        for (int i = 0; i < parent.length; i++)
            parent[i] = -1;

        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean[] visited = new boolean[vertices.length];
        queue.offer(v);
        visited[v] = true;

        while(!queue.isEmpty()) {
            int u = queue.poll();
            searchOrder.add(u);
            for (Vertex w: neighbors[u]) {
                if (!visited[w.getVertexIndex()]) {
                    queue.offer(w.getVertexIndex());
                    parent[w.getVertexIndex()] = u;
                    visited[w.getVertexIndex()] = true;
                }
            }
        }

        return new GraphTree(v, parent, searchOrder, vertices);
    }



}
