package algorithms.l09edgeweightedgraph.impl;

import algorithms.l09edgeweightedgraph.ops.MinimalSpanningTree;
import algorithms.l09edgeweightedgraph.rep.EWG;
import algorithms.l09edgeweightedgraph.rep.Edge;
import algorithms.l11unionfind.impl.UnionFindV4;
import algorithms.l11unionfind.rep.UnionFind;

import java.util.Comparator;
import java.util.stream.Collectors;

public class KruskalMST implements MinimalSpanningTree {
  private final EWG g;

  public KruskalMST(EWG g) {
    this.g = g;
  }

  private boolean isNotConnected(UnionFind uf, Edge e) {
    int v = e.either();
    int w = e.other(v);
    return !uf.isConnected(v, w);
  }

  @Override
  public Iterable<Edge> minSpanTree() {
    UnionFind uf = new UnionFindV4(g.v());
    // TODO: or just PriorityQueue with removal already visited
    return g.edgesStream().sorted(Comparator.comparingDouble(e -> e.weight))
      .filter(e -> isNotConnected(uf, e))
      .limit(g.v() - 1)
      .map(e -> {
        int v = e.either();
        int w = e.other(v);
        uf.union(v, w);
        return e;
      })
      .collect(Collectors.toList());
  }
}
