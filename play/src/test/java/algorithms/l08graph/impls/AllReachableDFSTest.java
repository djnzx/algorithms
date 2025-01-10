package algorithms.l08graph.impls;

import algorithms.l08graph.ops.AllReachable;
import algorithms.l08graph.rep.Graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AllReachableDFSTest {

  private final Graph g = MapData.graph();
  private final AllReachable ar = new AllReachableDFS(g);

  @Test
  void allReachable1() {
    assertIterableEquals(
      Arrays.asList(0,1,2,3,4,5,6,7,8,9),
      ar.allReachable(0)
    );
  }

  @Test
  void allReachable2() {
    assertIterableEquals(
      Arrays.asList(2, 4, 6, 8, 9),
      ar.allReachable(4)
    );
  }
}
