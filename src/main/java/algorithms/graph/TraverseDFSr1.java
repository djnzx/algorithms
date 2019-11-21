package algorithms.graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TraverseDFSr1 implements Traversable<Integer> {

  private final XGraph graph;
  private boolean[] visited;

  public TraverseDFSr1(XGraph graph) {
    this.graph = graph;
  }

  /**
   * DFS traverse
   * recursive version
   * WITH LOOPS DETECTION
   *
   * - with inner class
   * - with global variable visited
   * - with global variable path
   *
   * @param source - vertex to traverse from
   */
  @Override
  public Collection<Integer> traverse(Integer source) {
    DFSRecursive d = new DFSRecursive();
    this.visited = new boolean[graph.getVertexCount()];
    d.dfs(source);
    return d.path_;
  }

  // nested class with real recursive solution
  class DFSRecursive {
    private final List<Integer> path_ = new LinkedList<>();

    void dfs(int src) {
      if (visited[src]) return;
      visited[src] = true;
      path_.add(src);
      // get all children
      Collection<Integer> children = graph.getEdgesFrom(src);
      children.forEach(vertex -> dfs(vertex));
    }
  }
}
