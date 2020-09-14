package algorithms.l08graph;

public class AGraphOps {

  public int degree(AGraph g, int v) {
    int d = 0;
    for (int w: g.adj(v)) d++;
    return d;
  }

  public int maxDegree(AGraph g) {
    int max = 0;
    for (int v = 0; v < g.V(); v++)
      max = Math.max(max, degree(g, v));
    return max;
  }

  public int numberOfSelfLoops(AGraph g) {
    int cnt = 0;
    for (int v = 0; v < g.V(); v++) {
      for (int w: g.adj(v)) {
        cnt += w == v ? 1 : 0;
      }
    }
    return cnt / 2;
  }

}
