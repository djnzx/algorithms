package algorithms.l09edgeweightedgraph;

import algorithms.l09edgeweightedgraph.impl.KruskalMST;
import algorithms.l09edgeweightedgraph.impl.PrimMST;
import algorithms.l09edgeweightedgraph.rep.EWG;
import algorithms.l09edgeweightedgraph.rep.Edge;
import algorithms.l09edgeweightedgraph.rep.EdgeWeightedGraph;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO: move to impl  folder after finishing
 * TODO: move Union Find to Undirected Graphs parts
 * E * log E - sort
 * E - traverse
 * total E log E - linearithmic
 */
class MSTTests {

  MSTTests() throws FileNotFoundException {
  }

  private final EWG g = EdgeWeightedGraph.readFromFile("ewg.txt", 8);

  public static <A> LinkedList<A> itToLL(Iterable<A> data) {
    LinkedList<A> as = new LinkedList<>();
    data.forEach(as::add);
    return as;
  }

  public static <A> LinkedList<A> toLL(Iterable<A> data) {
    return data instanceof LinkedList ? (LinkedList<A>)data : itToLL(data);
  }

  @Test
  public void one() {
    Iterable<Edge> kruskal = new KruskalMST(g).minSpanTree();
    Iterable<Edge> prim = new PrimMST(g).minSpanTree();
    assertEquals(
      new HashSet<>(toLL(kruskal)),
      new HashSet<>(toLL(prim))
    );
  }
}
