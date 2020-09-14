package algorithms.l08graph.rework.impls;

import algorithms.l08graph.rework.rep.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class DFSRecursive1Test {

  private final Graph g = GraphData.graph1_dfs();
  private DFSRecursivePlain dfs;

  @BeforeEach
  void setUp() {
    dfs = new DFSRecursivePlain(g);
  }

  @Test
  void traverse() {
    assertIterableEquals(
      GraphData.graph1_dfs_expected,
      dfs.traverse(0)
    );
  }
}
