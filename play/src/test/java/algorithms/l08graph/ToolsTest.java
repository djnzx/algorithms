package algorithms.l08graph;

import algorithms.l08graph.rep.Graph;
import algorithms.l08graph.rep.dir.GraphAL;
import org.junit.jupiter.api.Test;

import static algorithms.l08graph.Tools.copyGraph;

class ToolsTest {

  @Test
  void copyGraphTest() {
    GraphAL g1 = new GraphAL(6, new int[][]{
      {0, 1},
      {1, 2},
      {1, 3},
      {1, 4},
      {2, 5},
      {3, 5},
      {4, 5},
    });
    Graph g2 = copyGraph(
      g1,
      () -> new GraphAL(g1.v())
    );
    g1.addEdge(0, 5);
    g2.addEdge(0, 4);
    System.out.println(g1);
    System.out.println();
    System.out.println(g2);
  }
}
