package algorithms.graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class TraverseUnordered implements Traversable<Integer>{

  private final XGraph graph;

  public TraverseUnordered(XGraph graph) {
    this.graph = graph;
  }

  /**
   * @param ignored - can be any variable
   *                presented here only for interface compatibility
   * @return
   */
  @Override
  public Collection<Integer> traverse(Integer ignored) {
    LinkedList<Integer> result = new LinkedList<>();
    for (int vertex = 0; vertex < graph.getVertexCount() ; vertex++) {
      Collection<Integer> edgesFromVertex = graph.getEdgesFrom(vertex);
      if (!edgesFromVertex.isEmpty()) {
        result.add(vertex);
        edgesFromVertex.forEach(to -> result.add(to));
      }
    }
    return result.stream().distinct().collect(Collectors.toList());
  }

}
