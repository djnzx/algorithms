package algorithms.graph;

import java.util.Collection;
import java.util.LinkedList;

public class TraverseDFSitr implements Traversable<Integer> {

  private final XGraph graph;

  public TraverseDFSitr(XGraph graph) {
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
    // final result will be stored here
    final Collection<Integer> result = new LinkedList<>();
    // variable to track the visited vertices to avoid loops
    final boolean[] visited = new boolean[graph.getVertexCount()];
    // variable to track the process
    LinkedList<Integer> process_ = new LinkedList<>();
    // start vertex
    process_.add(source);
    // main loop
    while (!process_.isEmpty()) {
      int current = process_.poll();  // source head
      if (visited[current]) continue; // I don't need the already visited vertex
      visited[current] = true;        // mark as visited
      result.add(current);            // add to result
      // get all children
      LinkedList<Integer> children = graph.getEdgesFrom(current);
      children.descendingIterator()   // add all children to head in reverse order
          .forEachRemaining(process_::addFirst);
    }
    return result;
  }

}
