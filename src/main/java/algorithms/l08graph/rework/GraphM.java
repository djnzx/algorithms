package algorithms.l08graph.rework;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * matrix based implementation
 */
public class GraphM implements Graph {

  private final int v;
  // first index - from
  // second index - to
  private final boolean[][] m;

  public GraphM(int v) {
    this.v = v;
    this.m = new boolean[v][v];
  }

  @Override
  public int v() {
    return this.v;
  }

  @Override
  public void addEdge(int v, int w) {
    m[v][w] = true;
  }

  @Override
  public Collection<Integer> children(int v) {
    return IntStream.range(0, v)
      .filter(w -> m[v][w])
      .boxed()
      .collect(Collectors.toList());
  }
}
