package algorithms.l08graph;

import algorithms.l08graph.rep.Graph;
import algorithms.l08graph.rep.undir.GraphAS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GraphASTest {

  private Graph g;

  @BeforeEach
  void create() {
    g = new GraphAS(10);
  }

  @Test
  void v() {
    assertEquals(10, g.v());
  }

  @Test
  void children() {
    g.addEdge(0,5);
    g.addEdge(0,6);

    assertIterableEquals(Arrays.asList(5, 6), g.children(0));
  }
}
