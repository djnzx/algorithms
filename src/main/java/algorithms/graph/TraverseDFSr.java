package algorithms.graph;

import java.util.LinkedList;
import java.util.List;

public class TraverseDFSr {

  private final XGraph graph;

  public TraverseDFSr(XGraph graph) {
    this.graph = graph;
  }

  public List<Integer> traverse(int from) {
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
