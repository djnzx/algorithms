package algorithms.l08graph.rework.impls;

import algorithms.l08graph.rework.rep.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class BFSRecursiveIterativeTest {

  private final Graph g = GraphData.graph2_bfs();
  private BFSIterative bfs;

  @BeforeEach
  void setUp() {
    bfs = new BFSIterative(g);
  }

  @Test
  void traverse() {
    assertIterableEquals(
      GraphData.graph2_bfs_expected,
      bfs.traverse(0)
    );
  }

}
