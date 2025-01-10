package algorithms.l08graph.impls;

import algorithms.l08graph.rep.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class DFS1Recursive1Test {

  private final Graph g = GraphData.graph1_dfs;
  private final DFS1RecursivePlain dfs = new DFS1RecursivePlain(g);

  @Test
  void traverse() {
    assertIterableEquals(
      GraphData.graph1_dfs_expected,
      dfs.traverse(0)
    );
  }
}
