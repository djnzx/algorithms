package algorithms.l08graph.impls;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HasCyclesImplTest {

  @Test
  void hasCycles1() {
    assertEquals(
      new HasCyclesImpl(GraphData.graph41_cycles).hasCycles(),
      GraphData.graph41_hasCycles
    );
  }

  @Test
  void hasCycles2() {
    assertEquals(
      new HasCyclesImpl(GraphData.graph42_cycles).hasCycles(),
      GraphData.graph42_hasCycles
    );
  }
}
