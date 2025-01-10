package algorithms.l10ewdig.test;

import algorithms.l10ewdig.impl.ShortestPathDijkstra1;
import algorithms.l10ewdig.rep.EWDiG;

public class ShortestPathDesign {

  public void test(EWDiG g, int s) {
    ShortestPathDijkstra1 sp = new ShortestPathDijkstra1(g);
    for (int v = 0; v < g.v(); v++) {
      int vf = v;
      sp.distanceTo(s, v).ifPresent(dist -> System.out.printf("%d -> %d (%.2f)", s, vf, dist));
      sp.shortestPathTo(s, v).ifPresent(path -> path.forEach(System.out::print));

      System.out.println();
    }

  }
}
