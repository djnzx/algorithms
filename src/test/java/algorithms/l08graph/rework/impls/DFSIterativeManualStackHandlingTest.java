package algorithms.l08graph.rework.impls;

import algorithms.l08graph.rework.rep.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class DFSIterativeManualStackHandlingTest {

  private final Graph g = GraphData.graph1_dfs();
  private DFSIterativeManualStackHandling dfs;

  @BeforeEach
  void setUp() {
    dfs = new DFSIterativeManualStackHandling(g);
  }

  @Test
  void traverse() {
    assertIterableEquals(
      GraphData.graph1_dfs_expected,
      dfs.traverse(0)
    );
  }

}
