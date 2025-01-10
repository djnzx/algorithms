package algorithms.l09edgeweightedgraph.impl;

import algorithms.l09edgeweightedgraph.ops.MinimalSpanningTree;
import algorithms.l09edgeweightedgraph.rep.EWG;
import algorithms.l09edgeweightedgraph.rep.Edge;
import algorithms.l11unionfind.impl.UnionFindV4;
import algorithms.l11unionfind.rep.UnionFind;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class KruskalMST implements MinimalSpanningTree {
  private final EWG g;

  public KruskalMST(EWG g) {
    this.g = g;
  }

  private boolean isConnected(UnionFind uf, Edge e) {
    int v = e.either();
    int w = e.other(v);
    return uf.isConnected(v, w);
  }

  @Override
  public Iterable<Edge> minSpanTree() {
    UnionFind uf = new UnionFindV4(g.v());

    PriorityQueue<Edge> edges =
      g.edgesStream()
        .collect(Collectors.toCollection(() -> new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight))));

    LinkedList<Edge> mst = new LinkedList<>();
    while (!edges.isEmpty() && mst.size() < g.v() - 1) {
      Edge e = edges.remove();
      if (isConnected(uf, e)) continue;
      mst.add(e);
      int v = e.either();
      int w = e.other(v);
      uf.union(v, w);
    }
    return mst;
  }
}
