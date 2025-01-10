package algorithms.l08graph.impls;

import algorithms.l08graph.rep.Graph;

public class CommonOperations {

  public int degree(Graph g, int v) {
    int d = 0;
    for (int w: g.children(v)) d++;
    return d;
  }

  public int maxDegree(Graph g) {
    int max = 0;
    for (int v = 0; v < g.v(); v++)
      max = Math.max(max, degree(g, v));
    return max;
  }

  public int numberOfSelfLoops(Graph g) {
    int cnt = 0;
    for (int v = 0; v < g.v(); v++) {
      for (int w: g.children(v)) {
        cnt += w == v ? 1 : 0;
      }
    }
    return cnt / 2;
  }

}
