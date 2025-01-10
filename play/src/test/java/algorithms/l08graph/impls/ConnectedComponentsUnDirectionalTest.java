package algorithms.l08graph.impls;

import algorithms.l08graph.rep.Graph;
import algorithms.l08graph.rep.undir.GraphAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectedComponentsUnDirectionalTest {

  private ConnectedComponents cc;

  @BeforeEach
  void setUp() {
    Graph g = new GraphAL(10);
    g.addEdge(0,1);
    g.addEdge(0,2);

    g.addEdge(3,4);
    g.addEdge(4,5);

    g.addEdge(6,7);
    g.addEdge(6,8);
    g.addEdge(7,9);

    cc = new ConnectedComponents(g);
  }

  @Test
  void isConnectedGroups() {
    assertTrue(cc.isConnected(0,1));
    assertTrue(cc.isConnected(0,2));

    assertTrue(cc.isConnected(3,4));
    assertTrue(cc.isConnected(4,5));

    assertTrue(cc.isConnected(6,7));
    assertTrue(cc.isConnected(6,8));
    assertTrue(cc.isConnected(6,9));
  }

  @Test
  void isConnectedReflex() {
    assertTrue(cc.isConnected(1,1));
  }

  @Test
  void isConnectedSymmetric() {
    assertTrue(cc.isConnected(1,2));
    assertTrue(cc.isConnected(2,1));
  }

  @Test
  void isConnectedTransitive() {
    assertTrue(cc.isConnected(0,1));
    assertTrue(cc.isConnected(1,2));
  }

  @Test
  void isConnectedFalse() {
    assertFalse(cc.isConnected(0,3));
    assertFalse(cc.isConnected(3,9));
  }

  @Test
  void count() {
    assertEquals(3, cc.count());
  }

}
