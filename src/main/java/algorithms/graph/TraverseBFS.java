package algorithms.graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TraverseBFS {

  private final XGraph graph;

  public TraverseBFS(XGraph graph) {
    this.graph = graph;
  }

  public List<Integer> traverse() {
    return traverse(0); // start from the vertex=0 (from the root of the graph)
  }

  /**
   * BFS from the specified vertex
   * with loop detection
   *
   * we need extra space
   * 'process_' to track our process
   * 'visited_' to mark visited vertices
   */
  public List<Integer> traverse(int from) {
    // the final result will be stored here
    final List<Integer> result = new LinkedList<>();

    // variables to track process
    final boolean[] visited_ = new boolean[graph.getVertexCount()]; // false by default, thankful to Java
    final LinkedList<Integer> process_ = new LinkedList<>();

    // initiating the iteration process
    visited_[from] = true;
    process_.add(from);

    while (!process_.isEmpty()) {
      int current = process_.poll();
      result.add(current);
      Collection<Integer> possible = graph.getEdgesFrom(current);
      for (int dst: possible) {
        if (visited_[dst]) continue; // I'm not interested in already visited vertices
        visited_[dst] = true;
        process_.add(dst);
      }
    }

    return result;
  }

}
