package algorithms.l09edgeweightedgraph.impl;

import algorithms.l09edgeweightedgraph.ops.MinimalSpanningTree;
import algorithms.l09edgeweightedgraph.rep.EWG;
import algorithms.l09edgeweightedgraph.rep.Edge;
import common.RX;

public class PrimMST implements MinimalSpanningTree {
  private final EWG g;

  public PrimMST(EWG g) {
    this.g = g;
  }

  @Override
  public Iterable<Edge> MinimalSpanningTree() {
    throw RX.NI;
  }
}