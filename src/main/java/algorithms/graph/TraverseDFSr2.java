package algorithms.graph;

import java.util.Collection;
import java.util.LinkedList;

public class TraverseDFSr2 {

  private final XGraph graph;

  public TraverseDFSr2(XGraph graph) {
    this.graph = graph;
  }

  /**
   * DFS traverse
   * recursive version
   * WITH LOOPS DETECTION
   *
   * - WITHOUT inner class
   * - WITHOUT global variable visited
   * - WITHOUT global variable path
   * - PURE FUNCTIONAL approach
   *
   * @param from - vertex to traverse from
   *
   * runner method
   */
  public Collection<Integer> traverse(int from) {
    // run the recursive solution with clean 'visited' variable and empty 'result'
    return traverse(from,
        new boolean[graph.getVertexCount()],
        new LinkedList<>());
  }

  /**
   * real recursive implementation
   */
  private Collection<Integer> traverse(int from, boolean[] visited, Collection<Integer> list) {
    // skip iteration if already visited (loop detection)
    if (visited[from]) return list;
    // mark as visited
    visited[from] = true;
    // put into resulted collection
    list.add(from);
    // get all possible ways
    Collection<Integer> possible = graph.getEdgesFrom(from);
    // iterate over all possible
    for (int vertex: possible) {
      traverse(vertex, visited, list);
    }
    // finish with the modified list
    return list;
  }

}
