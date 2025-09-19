package algorithms.l08graph.rep.undir;

import algorithms.l08graph.rep.Graph;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * matrix based implementation
 */
public class GraphM implements Graph {

  private final int v;
  // first index - src vertex
  // second index - dst vertex
  // if we want to work with path cost
  // we need to change it to int[][]
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
    m[w][v] = true;
  }

  @Override
  public void removeEdge(int v, int w) {
    m[v][w] = false;
    m[w][v] = false;
  }

  @Override
  public Collection<Integer> children(int v) {
    return IntStream.range(0, v)
      .filter(w -> m[v][w])
      .boxed()
      .collect(Collectors.toList());
  }
}
