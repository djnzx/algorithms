package algorithms.l09edgeweightedgraph.impl;

import algorithms.l09edgeweightedgraph.ops.MinimalSpanningTree;
import algorithms.l09edgeweightedgraph.rep.EWG;
import algorithms.l09edgeweightedgraph.rep.Edge;
import algorithms.l11unionfind.impl.UnionFindV4;
import algorithms.l11unionfind.rep.UnionFind;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class PrimMST implements MinimalSpanningTree {
  private final EWG g;

  public PrimMST(EWG g) {
    this.g = g;
  }

  @Override
  public Iterable<Edge> minSpanTree() {
    PriorityQueue<Edge> edges = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));
    LinkedList<Edge> mst = new LinkedList<>();
    boolean[] visited = new boolean[g.v()];
    UnionFind uf = new UnionFindV4(g.v());

    int pt = 0;
    g.adj(pt).forEach(edges::add);

    while (!edges.isEmpty() && mst.size() < g.v() - 1 ) {
      for (Edge ex : g.adj(pt))
        if (!visited[ex.other(pt)]) edges.add(ex);

      visited[pt] = true;

      Edge e = edges.remove();

      int v = e.either();
      int w = e.other(v);

      if (uf.isConnected(v, w)) continue;
      mst.add(e);
      uf.union(v, w);

      pt = visited[v] ? w : v;
    }

    return mst;
  }
}
