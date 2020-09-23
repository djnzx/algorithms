package algorithms.l09edgeweightedgraph.rep;

public interface EWG {
  int v();
  void addEdge(Edge e);
  Iterable<Edge> adj(int v);
}
