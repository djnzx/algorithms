package algorithms.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TraverseUnordered {

  private final XGraph graph;

  public TraverseUnordered(XGraph graph) {
    this.graph = graph;
  }

  public List<Integer> traverse() {
    LinkedList<Integer> result = new LinkedList<>();
    for (int vertex = 0; vertex < graph.getVertexCount() ; vertex++) {
      LinkedList<Integer> edgesFromVertex = graph.getEdgesFrom(vertex);
      if (!edgesFromVertex.isEmpty()) {
        result.add(vertex);
        edgesFromVertex.forEach(to -> result.add(to));
      }
    }
    return result.stream().distinct().collect(Collectors.toList());
  }

}
