package algorithms.l08graph.impls;

import algorithms.l08graph.ops.TopologicalSorting;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortingImpl2Test {

  @Test
  void topologicalSortingOK() {
    TopologicalSorting ts = new TopologicalSortingImpl2(GraphData.graph51_topological);

    assertEquals(
      GraphData.graph51_topological_exp,
      ts.topologicalSorting()
    );
  }
}
