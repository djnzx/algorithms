package algorithms.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class XGraph {
  private final int vertex_count;
  private final List<LinkedList<Integer>> edges;

  /**
   * constructor must be provided with
   * number of vertices.
   * 10 means vertex number 0..9
   */
  public XGraph(int vertex_count) {
    this.vertex_count = vertex_count;
    this.edges = new ArrayList<>(vertex_count);
    for (int i = 0; i < vertex_count; i++) {
      edges.add(new LinkedList<>());
    }
  }

  /**
   * adds an edge from `vertex_from` to `vertex_to`
   * @param vertex_from
   * @param vertex_to
   */
  public void add(int vertex_from, int vertex_to) {
    LinkedList<Integer> vertex = edges.get(vertex_from);
    vertex.add(vertex_to);
  }

  /**
   * removes an edge from `vertex_from` to `vertex_to`
   * @param vertex_from
   * @param vertex_to
   */
  public void remove(int vertex_from, int vertex_to) {
    LinkedList<Integer> vertex = edges.get(vertex_from);
    vertex.remove(Integer.valueOf(vertex_to));
  }

  /**
   * method to check whether the vertices are connected
   * @param vertex_from
   * @param vertex_to
   */
  public boolean check(int vertex_from, int vertex_to) {
    LinkedList<Integer> vertex = edges.get(vertex_from);
    return vertex.contains(new Integer(vertex_to));
  }

  /**
   * method to obtain count of vertices
   */
  public int getVertexCount() {
    return vertex_count;
  }

  /**
   * method to obtain edges from the specified vertex
   */
  public LinkedList<Integer> getEdgesFrom(int vertex_from) {
    LinkedList<Integer> destinations = edges.get(vertex_from);
    return destinations;
  }

}
