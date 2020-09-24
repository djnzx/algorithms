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

  private boolean isNotConnected(UnionFind uf, Edge e) {
    int v = e.either();
    int w = e.other(v);
    return !uf.isConnected(v, w);
  }

  private void connect(UnionFind uf, Edge e) {
    int v = e.either();
    int w = e.other(v);
    uf.union(v, w);
  }

  // TODO: WRONG!!!. needs to be fixed
  @Override
  public Iterable<Edge> minSpanTree() {
    PriorityQueue<Edge> edges = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));
    LinkedList<Edge> mst = new LinkedList<>();
    boolean[] visited = new boolean[g.v()];
    UnionFind uf = new UnionFindV4(g.v());

    // we assume that 0 is connected
    int pt = 0;
    g.adj(pt).forEach(edges::add);

    while (!edges.isEmpty() &&  mst.size() < g.v() - 1 ) {
      Edge e = edges.poll();
      System.out.printf("Edge: %s\n", e);
      System.out.printf("Queue: %s\n", edges);

      if (isNotConnected(uf, e)) {
        System.out.printf("Adding to MST: %s\n", e);
        connect(uf, e);
        mst.add(e);
      }

      visited[pt] = true;
      pt = e.other(pt);

      if (!visited[pt]) {
        System.out.print("Adding to QUEUE: ");
        for (Edge ex : g.adj(pt)) {
          if (!visited[ex.other(pt)]) {
            System.out.print(ex);
            edges.add(ex);
          }
        }
        System.out.println();
      }

    }

    return mst;
  }
}
