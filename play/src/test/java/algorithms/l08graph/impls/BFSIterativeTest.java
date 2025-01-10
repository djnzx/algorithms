package algorithms.l08graph.impls;

import algorithms.l08graph.rep.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class BFSIterativeTest {

  private final Graph g = GraphData.graph2_bfs;
  private final BFSIterative bfs = new BFSIterative(g);

  @Test
  void traverse() {
    assertIterableEquals(
      GraphData.graph2_bfs_expected,
      bfs.traverse(0)
    );
  }

}
