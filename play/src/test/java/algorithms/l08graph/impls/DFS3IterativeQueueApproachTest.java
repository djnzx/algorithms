package algorithms.l08graph.impls;

import algorithms.l08graph.rep.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class DFS3IterativeQueueApproachTest {

  private final Graph g = GraphData.graph1_dfs;
  private final DFS3IterativeQueueApproach dfs = new DFS3IterativeQueueApproach(g);

  @Test
  void traverse() {
    assertIterableEquals(
      GraphData.graph1_dfs_expected,
      dfs.traverse(0)
    );
  }

}
