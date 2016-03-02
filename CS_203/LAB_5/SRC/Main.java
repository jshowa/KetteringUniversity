import java.io.*;
import java.util.*;
/**
 * Name: Jacob Howarth
 * Course: CS-203, Spring 2009
 * Class Name: Main
 *
 * Description: This class is the main class that executes the program. This
 *              class also handles the menu and user interaction for the
 *              program.
 *
 * @author jshowa
 */
public class Main {

    /**
     * Method: Main
     *
     * Description: Method that loads the file and begins program
     *              execution
     *
     * Parameters:
     * @param args - program command line arguments
     */
    public static void main(String[] args) {
        String input = "";
        UnDirectGraph graph;
        
        graph = loadNewGraph(); // load graph
        run(graph, input); // display menu and run actual program if file load is successful
            

    }

    /**
     * Method: run
     *
     * Description: This method prints the menu and handles all user input
     *              for menu manipulation.
     *
     * Parameters:
     * @param graph - undirected graph object loaded from file
     * @param input - input string to store user input from console
     */
    public static void run(UnDirectGraph graph, String input) {
        boolean exit = false;
        Scanner scanInput = new Scanner(System.in);

        while (!exit && graph != null) {
            System.out.print(printMenuOptions()); // print menu
            input = scanInput.nextLine();

            while (!input.matches("[0-7]{1}")) { // error check menu option selection
                System.out.println("Invalid option: Please choose a number from the menu.");
                System.out.print("Enter one of the options above: ");
                input = scanInput.nextLine();
            }

            switch (Integer.parseInt(input)) {
                case 0: graph = loadNewGraph(); // load new graph from file
                        break;
                case 1: printConnectedComp(graph); // print connected components of graph
                        break;
                case 2: printAPath(graph); // print a path in the graph given two vertices
                        break;
                case 3: printShortestPath(graph); // print shortest path given two vertices
                        break;
                case 5: printAPath_AvoidVert(graph); // print a path avoiding vertices
                        break;
                case 6: printEulerianTour(graph); // print a eulerian tour
                        break;
                default: exit = true;
            }

        }
        System.out.println("Quitting...");
        System.exit(0);


    }

    /**
     * Method: printMenuOptions
     *
     * Description: This method returns the menu display as a string.
     *
     * Parameters:
     * @return String - menu user interface
     */
    public static String printMenuOptions() {
        String menu = "";

        menu += "***********MENU OPTIONS**************\n" +
                "*                                   *\n" +
                "* 0) Load new graph                 *\n" +
                "* 1) Print connected components     *\n" +
                "* 2) Print path between two given   *\n" +
                "*    nodes                          *\n" +
                "* 3) Print the shortest path        *\n" +
                "*    between two nodes              *\n" +
                "* 4) Print a path between two nodes *\n" +
                "*    given a set of avoided edges   *\n" +
                "* 5) Print a path between two nodes *\n" +
                "*    avoiding given vertices        *\n" +
                "* 6) Print a Eulerian tour          *\n" +
                "* 7) Quit                           *\n" +
                "*                                   *\n" +
                "*************************************\n\n" +
                "Enter one of the options above: ";

        return menu;

    }

    /**
     * Method: printEulerianTour
     *
     * Description: This method prints a eulerian tour to console output
     *              starting at a vertex specified by the user.
     *
     * Parameters:
     * @param graph - undirected graph object loaded from file
     */
    public static void printEulerianTour(UnDirectGraph graph) {
        Stack<Integer> tour;
        String a, temp = "";
        boolean secondSeq = false;
        Vertex start;
        int indexOfStart;
        Scanner userInput = new Scanner(System.in);

        System.out.print("Enter a vertice a which to start the tour: ");
        a = userInput.nextLine();

        start = new Vertex(a);

        indexOfStart = graph.getIndexOfVertex(start);

        // Copy the graph and perform the tour
        UnDirectGraph graphCopy = graph.graphDeepCopy();

        // Perform the tour on the copied graph because vertices will be deleted
        if (indexOfStart != -1) {
            tour = graphCopy.findEulerianTour(indexOfStart);

        while (!tour.isEmpty()) {
                temp = graphCopy.getVertex(tour.pop()).toString();
                // return path in special sequence as specified in requirements
                // sequence ex. (A,C) = [A,(A,B),B,(B,C),C]
                if (!secondSeq) {
                    System.out.print(temp + ",(" + temp + ",");
                    secondSeq = true;
                }
                else if (tour.isEmpty())
                    System.out.println(temp + ")," + temp + "]");
                else
                    System.out.print(temp + ")," + temp + ",(" + temp + ",");
            }
        }
        else
            System.out.println("The vertex " + a + "does not exist in the graph.");
    }

    /**
     * Method: printShortestPath
     *
     * Description: This method prints the shortest path from two vertices specified
     *              by the user.
     *
     * Parameters:
     * @param graph - undirected graph object loaded from file
     */
    public static void printShortestPath(UnDirectGraph graph) {
        String a, b;
        Scanner userInput = new Scanner(System.in);
        int indexOf_U, indexOf_V;
        
        System.out.print("Enter a source vertex: ");
        a = userInput.nextLine();
        
        System.out.print ("Enter destination vertex: ");
        b = userInput.nextLine();

        Vertex u = new Vertex(a);
        Vertex v = new Vertex(b);

        indexOf_U = graph.getIndexOfVertex(u);
        indexOf_V = graph.getIndexOfVertex(v);

        if (indexOf_U != -1 && indexOf_V != -1) {
            System.out.println();
            GraphTree bfs = graph.BFS(indexOf_U);
            bfs.printPath(indexOf_V);
            System.out.println();
        }
        else if (indexOf_U == -1)
            System.out.println("\nVertex " + a + " does not exist in the current graph.\n");
        else if (indexOf_V == -1)
            System.out.println("\nVertex " + b + " does not exist in the current graph.\n");
        

    }

    /**
     * Method: run
     *
     * Description: This method prints a path between two vertices as specified
     *              by the user.
     *
     * Parameters:
     * @param graph - undirected graph object loaded from file
     */
    public static void printAPath(UnDirectGraph graph) {
        String a, b;
        Scanner userInput = new Scanner(System.in);
        int indexOf_U, indexOf_V;

        System.out.print("Enter a source vertex: ");
        a = userInput.nextLine();

        System.out.print ("Enter destination vertex: ");
        b = userInput.nextLine();

        Vertex u = new Vertex(a);
        Vertex v = new Vertex(b);

        indexOf_U = graph.getIndexOfVertex(u);
        indexOf_V = graph.getIndexOfVertex(v);

        if (indexOf_U != -1 && indexOf_V != -1) {
            System.out.println();
            graph.getPath(indexOf_U, indexOf_V, null);
            System.out.println();
        }
        else if (indexOf_U == -1)
            System.out.println("\nVertex " + a + " does not exist in the current graph.\n");
        else if (indexOf_V == -1)
            System.out.println("\nVertex " + b + " does not exist in the current graph.\n");

    }

    /**
     * Method: printAPath_AvoidVert
     *
     * Description: This method prints a path between two vertices avoiding
     *              a list of vertices as given by the user in a comma seperated
     *              list.
     *
     * Parameters:
     * @param graph - undirected graph object loaded from file
     */
    public static void printAPath_AvoidVert(UnDirectGraph graph) {
        String a, b, avoidList;
        String avoidListArry[];
        Vertex[] avoidVert;
        Scanner userInput = new Scanner(System.in);
        int indexOf_U, indexOf_V;
        int[] avoidVertNum = new int[graph.getNumberOfVertices()];

        System.out.print("Enter a source vertex: ");
        a = userInput.nextLine();

        System.out.print ("Enter destination vertex: ");
        b = userInput.nextLine();

        System.out.print("Enter list of vertices to avoid each seperated by a comma: ");
        avoidList = userInput.nextLine();

        if (avoidList.isEmpty())
            avoidVertNum = null;
        else {
            avoidVert = graph.getVertices();
            avoidListArry = avoidList.split(","); // parse vertice list
            for (int i = 0; i < avoidListArry.length; i++) {
                for (int j = 0; j < avoidVert.length; j++) {
                    // compare the vertice list to avoid specified by user
                    // to graph vertices and store them in an array as a black list
                    // to pass to graph path method
                    if (avoidVert[j].getElement().equalsIgnoreCase(avoidListArry[i]))
                        avoidVertNum[i] = avoidVert[j].getVertexIndex();
                }
            }
        }

        Vertex u = new Vertex(a);
        Vertex v = new Vertex(b);

        indexOf_U = graph.getIndexOfVertex(u);
        indexOf_V = graph.getIndexOfVertex(v);

        if (indexOf_U != -1 && indexOf_V != -1) {
            System.out.println();
            graph.getPath(indexOf_U, indexOf_V, avoidVertNum); // get path avoiding vertices
            System.out.println();
        }
        else if (indexOf_U == -1)
            System.out.println("\nVertex " + a + " does not exist in the current graph.\n");
        else if (indexOf_V == -1)
            System.out.println("\nVertex " + b + " does not exist in the current graph.\n");


    }

    /**
     * Method: printConnectedComp
     *
     * Description: This method prints the connected components of the graph
     *
     * Parameters:
     * @param graph - undirected graph object loaded from file
     */
    public static void printConnectedComp(UnDirectGraph graph) {
        List<UnDirectGraph> subGraphList;

        subGraphList = graph.getConnectedComponents();

        System.out.println("\nCONNECTED COMPONENTS OF THE GRAPH");

        for(int i = 0; i < subGraphList.size(); i++)
            subGraphList.get(i).printAdjacencyLists();

        System.out.println();
    }

    /**
     * Method: loadNewGraph
     *
     * Description: This method loads a new graph from a file specified
     *              by the user.
     *
     * Parameters:
     * @param graph - undirected graph object loaded from file
     */
    public static UnDirectGraph loadNewGraph() {
        String input = "";
        Scanner userInput = new Scanner(System.in);
        UnDirectGraph graph;
        GraphFileReader newFile;

        while(!input.equalsIgnoreCase("Q")) {
            System.out.print("Please enter a valid graph file name: ");
            input = userInput.nextLine();

            newFile = null;
            newFile = new GraphFileReader(input);

            // error check try catch loop for file if a file entered by user
            // could not be read. if the user types Q the program quits because
            // the program can't do anything without file input.
            try {
                if(newFile.read()) {
                    graph = newFile.getGraph();
                    if (graph != null)
                        return graph;
                }
                else {
                    if (!input.equalsIgnoreCase("Q"))
                        System.out.println("Error: Could not read. Please try a new file.");
                }
            }
            catch(FileNotFoundException ex) {
                System.out.println("Error: Could not read. Please try a new file.");
            }
        }

        return null;
    }

}
