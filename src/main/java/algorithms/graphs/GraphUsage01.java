package algorithms.graphs;

public class GraphUsage01 {
    public static void main(String[] args) {
        Graph g = new Graph(11);

        g.addEdge(1, 2);
        g.addEdge(1, 3);

        g.addEdge(2, 4);
        g.addEdge(2, 10);
        g.addEdge(2, 5);

        g.addEdge(3, 6);
        g.addEdge(3, 7);

        g.addEdge(7, 8);

        g.addEdge(4, 9);

        g.printAllUnordered(); // <1, 2, 3, 4, 5, 6, 7, 8, 9, 10>
        g.printAllBFS();       // <1, 2, 3, 4, 10, 5, 6, 7, 9, 8>

        g.printAllDFSrec();    // <1, 2, 4, 9, 10, 5, 3, 6, 7, 8>
        g.printAllDFSnr();     // <1, 2, 4, 9, 10, 5, 3, 6, 7, 8>
    }
}
