package algorithms.l09edgeweightedgraph.rep;

import java.util.stream.Stream;

public interface EWG {
  int v();
  void addEdge(Edge e);
  Iterable<Edge> adj(int v);
  Iterable<Edge> edges();
  Stream<Edge> edgesStream();
}
