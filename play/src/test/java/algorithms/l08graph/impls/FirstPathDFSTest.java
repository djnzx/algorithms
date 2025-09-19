package algorithms.l08graph.impls;

import algorithms.l08graph.rep.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirstPathDFSTest {

  private final Graph g = GraphData.graph3_path;
  private final FirstPathDFS path = new FirstPathDFS(g);

  @Test
  void path1() {
    assertEquals(
      path.path(0, 15),
      GraphData.graph3_path0to15
    );
  }

  @Test
  void path2() {
    assertEquals(
      path.path(6, 13),
      GraphData.graph3_path6to13
    );
  }

  @Test
  void path3() {
    assertEquals(
      path.path(2, 3),
      GraphData.graph3_path2to3
    );
  }
}
