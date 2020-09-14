package algorithms.l08graph;

import java.util.Collection;
import java.util.LinkedList;

public class TraverseDFSr2 implements Traversable<Integer> {

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
   * - FUNCTIONAL approach
   *
   * @param source - vertex to traverse from
   *
   * runner method
   */
  @Override
  public Collection<Integer> traverse(Integer source) {
    // run the recursive solution with clean 'visited' variable and empty 'result'
    return traverse(source,
        new boolean[graph.getVertexCount()],
        new LinkedList<>());
  }

  /**
   * real recursive implementation
   */
  private Collection<Integer> traverse(int source, boolean[] visited, Collection<Integer> list) {
    // skip iteration if already visited (loop detection)
    if (visited[source]) return list;
    // mark as visited
    visited[source] = true;
    // put into resulted collection
    list.add(source);
    // get all children
    Collection<Integer> children = graph.getEdgesFrom(source);
    // iterate over all children
    for (int vertex: children) {
      traverse(vertex, visited, list);
    }
    // finish with the modified list
    return list;
  }

}
