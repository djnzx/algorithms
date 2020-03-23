package algorithms.l08graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TraverseBFS implements Traversable<Integer> {

  private final XGraph graph;

  public TraverseBFS(XGraph graph) {
    this.graph = graph;
  }

  /**
   * BFS from the specified vertex
   * iterative version
   * WITH LOOPS DETECTION
   *
   * we need extra space
   * 'process_' to track our process
   * 'visited_' to mark visited vertices
   */
  @Override
  public Collection<Integer> traverse(Integer source) {
    // the final result will be stored here
    final List<Integer> result = new LinkedList<>();

    // variables to track process
    final boolean[] visited_ = new boolean[graph.getVertexCount()]; // false by default, thankful to Java
    final LinkedList<Integer> process_ = new LinkedList<>();

    // initiating the iteration process
    visited_[source] = true;
    process_.add(source);

    while (!process_.isEmpty()) {
      int current = process_.poll();
      result.add(current);
      Collection<Integer> children = graph.getEdgesFrom(current);
      for (int dst: children) {
        if (visited_[dst]) continue; // I'm not interested in already visited vertices
        visited_[dst] = true;
        process_.add(dst);
      }
    }

    return result;
  }

}
