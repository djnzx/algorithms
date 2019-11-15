package algorithms.graph;

import java.util.LinkedList;
import java.util.List;

public class TraverseDFS {

  private final XGraph graph;

  public TraverseDFS(XGraph graph) {
    this.graph = graph;
  }

  public List<Integer> traverse_iterative(int source) {
    // real path
    final List<Integer> path_ = new LinkedList<>();
    // track the process
    LinkedList<Integer> process = new LinkedList<>();
    // start vertex
    process.add(source);
    // main loop
    while (!process.isEmpty()) {
      int current = process.poll(); // head
      path_.add(current);
      graph.getEdgesFrom(current).descendingIterator()
          // add to head in reverse order
          .forEachRemaining(process::addFirst);
    }
    return path_;
  }

  public List<Integer> traverse_recursive(int from) {
    DFSRecursive d = new DFSRecursive();
    d.dfs(from);
    return d.path_;
  }

  // nested class with real recursive solution
  class DFSRecursive {
    private final List<Integer> path_ = new LinkedList<>();

    void dfs(int source) {
      path_.add(source);
      graph.getEdgesFrom(source).forEach(vertex -> dfs(vertex));
    }
  }
}
