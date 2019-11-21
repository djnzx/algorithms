package algorithms.graph;

import java.util.List;
import java.util.StringJoiner;

public class XGraphApp {

  public XGraph create() {
    XGraph g = new XGraph(17);
    g.add(0, 1);

    g.add(1, 2);
    g.add(1, 3);

    g.add(2, 4);
    g.add(2, 5);

    g.add(4, 8);
    g.add(4, 9);

    g.add(5, 10);
    g.add(5, 11);

    g.add(3, 6);
    g.add(3, 7);

    g.add(6, 12);

    g.add(7, 13);
    g.add(7, 14);

    g.add(12, 15);

    // cycle - will produce stack overflow in Graph.isConnectedBasic(from, to)
    g.add(6, 16);
    g.add(16, 3);

    return g;
  }

  private static String list_to_string(List<Integer> vertices) {
    StringJoiner sj = new StringJoiner(", ", "<", ">");
    vertices.forEach(n -> sj.add(String.valueOf(n)));
    return sj.toString();
  }

  void print_traverse(String msg, List<Integer> data) {
    System.out.printf(msg, list_to_string(data));
  }

  public static void main(String[] args) {
    XGraphApp app = new XGraphApp();
    XGraph g = app.create();
    TraverseDFS dfs = new TraverseDFS(g);
    TraverseBFS bfs = new TraverseBFS(g);
    TraverseUnordered rnd = new TraverseUnordered(g);
    Paths paths = new Paths(g);

//    System.out.printf("Path (Basic) from 0 to 15:%b\n", paths.isConnectedBasic(0, 15)); // true
//    System.out.printf("Path (Basic) from 6 to 13:%b\n", paths.isConnectedBasic(6, 13)); // false
    System.out.printf("== Path from 0 to 15:%b\n", paths.isConnected(0, 15)); // true
    System.out.printf("== Path from 6 to 13:%b\n", paths.isConnected(6, 13)); // false
    System.out.printf("== Path from 13 to 6:%b\n", paths.isConnected(13, 6)); // false
//    System.out.printf("== Path from 2 to 1:%b\n", paths.isConnected(2, 1)); // false
//    app.print_traverse("Unordered traversal      : %s\n", rnd.traverse());
//    app.print_traverse("BFS traversal            : %s\n", bfs.traverse());
//    app.print("DFS traversal (recursive): %s\n", dfs.traverse_recursive(0));
//    app.print("DFS traversal (iterative): %s\n", dfs.traverse_iterative(0));
  }

}
