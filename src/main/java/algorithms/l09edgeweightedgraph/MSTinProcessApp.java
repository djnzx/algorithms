package algorithms.l09edgeweightedgraph;

import algorithms.l09edgeweightedgraph.impl.KruskalMST;
import algorithms.l09edgeweightedgraph.impl.PrimMST;
import algorithms.l09edgeweightedgraph.rep.EWG;
import algorithms.l09edgeweightedgraph.rep.EdgeWeightedGraph;

import java.io.FileNotFoundException;

/**
 * TODO: move to impl  folder after finishing
 * TODO: move Union Find to Undirected Graphs parts
 * E * log E - sort
 * E - traverse
 * total E log E - linearithmic
 */
public class MSTinProcessApp {
  public static void main(String[] args) throws FileNotFoundException {
    EWG g = EdgeWeightedGraph.readFromFile("ewg.txt", 8);
    System.out.println("Kruskal");
    KruskalMST mst1 = new KruskalMST(g);
    mst1.minSpanTree().forEach(System.out::println);

    System.out.println("Prim");
    PrimMST mst2 = new PrimMST(g);
    mst2.minSpanTree().forEach(System.out::println);
  }

}
