package algorithms.l08graph.impls;

import algorithms.l08graph.ops.TopologicalSorting;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TopologicalSortingImplTest {

  @Test
  void topologicalSortingOK() {
    TopologicalSorting ts = new TopologicalSortingImpl(GraphData.graph51_topological);

    assertEquals(
      GraphData.graph51_topological_exp,
      ts.topologicalSorting()
    );
  }

  @Test
  void topologicalSortingFAILED() {
    TopologicalSorting ts = new TopologicalSortingImpl(GraphData.graph52_topological);

    assertEquals(
      GraphData.graph52_topological_exp,
      ts.topologicalSorting()
    );
  }
}
