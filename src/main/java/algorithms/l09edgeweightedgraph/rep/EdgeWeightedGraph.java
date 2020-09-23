package algorithms.l09edgeweightedgraph.rep;

import common.RX;

import java.util.Collections;
import java.util.LinkedList;

public class EdgeWeightedGraph implements EWG {
  private final int v;
  private final LinkedList<Edge>[] adj;

  @SuppressWarnings("unchecked")
  public EdgeWeightedGraph(int v) {
    this.v = v;
    this.adj = (LinkedList<Edge>[]) new LinkedList[v];
    for (int i = 0; i < v; i++) {
      adj[i] = new LinkedList<>();
    }
  }

  @Override
  public int v() {
    return this.v;
  }

  @Override
  public void addEdge(Edge e) {
    int v = e.either();
    int w = e.other(v);
    adj[v].add(e);
    adj[w].add(e);
  }

  @Override
  public Iterable<Edge> adj(int v) {
    return Collections.unmodifiableCollection(adj[v]);
  }

  public Iterable<Edge> edges() {
    throw RX.NI;
  }

}
