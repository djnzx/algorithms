package algorithms.graph;

import java.util.List;
import java.util.StringJoiner;

public class XGraphApp {

  public XGraph create() {
    XGraph g = new XGraph(15);
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

    return g;
  }

  private static String list_to_string(List<Integer> vertices) {
    StringJoiner sj = new StringJoiner(", ", "<", ">");
    vertices.forEach(n -> sj.add(String.valueOf(n)));
    return sj.toString();
  }

  void print(String msg, List<Integer> data) {
    System.out.printf(msg, list_to_string(data));
  }

  public static void main(String[] args) {
    XGraphApp app = new XGraphApp();
    XGraph g = app.create();
    TraverseDFS dfs = new TraverseDFS(g);
    TraverseBFS bfs = new TraverseBFS(g);
    TraverseUnordered rnd = new TraverseUnordered(g);

    app.print("Unordered traversal      : %s\n", rnd.traverse());
    app.print("BFS traversal            : %s\n", bfs.traverse());
    app.print("DFS traversal (recursive): %s\n", dfs.traverse_recursive(0));
    app.print("DFS traversal (iterative): %s\n", dfs.traverse_iterative(0));
  }
}
