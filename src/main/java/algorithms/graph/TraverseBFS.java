package algorithms.graph;

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

  // BFS from the specified vertex
  public List<Integer> traverse(int from) {
    final List<Integer> result = new LinkedList<>();
    final boolean[] visited = new boolean[graph.getVertexCount()]; // false by default, thankful to Java
    final LinkedList<Integer> process_ = new LinkedList<>();
    visited[from] = true;
    process_.add(from);
    while (!process_.isEmpty()) {
      int current = process_.poll();
      result.add(current);
      for (int to: graph.getEdgesFrom(current)) {
        if (!visited[to]) {
          visited[to] = true;
          process_.add(to);
        }
      }
    }
    return result;
  }

}
