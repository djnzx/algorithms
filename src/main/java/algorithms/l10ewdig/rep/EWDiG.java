package algorithms.l10ewdig.rep;

import java.util.stream.Stream;

public interface EWDiG {
  int v();
  void addEdge(DiEdge e);
  Iterable<DiEdge> adj(int v);
  Stream<DiEdge> adjStream(int v);
  Iterable<DiEdge> edges();
  Stream<DiEdge> edgesStream();
}
