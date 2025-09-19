package algorithms.l08graph.rep;

import algorithms.l08graph.rep.dir.GraphAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class AbstractGraphARepresentTest {

  private GraphAL g;

  @BeforeEach
  void create() {
    g = new GraphAL(10);
    g.addEdge(0,5);
    g.addEdge(0,6);
    g.addEdge(6,7);
  }

  @Test
  void represent() {
    assertIterableEquals(
      Arrays.asList(new Edge(0, 5), new Edge(0, 6), new Edge(6, 7)),
      g.represent().collect(Collectors.toList())
    );
  }

  @Test
  void testToString() {
    assertEquals(
      "0 -> 5\n0 -> 6\n6 -> 7",
      g.toString()
    );
  }
}
