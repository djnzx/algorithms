package algorithms.l08graph.ops;

import java.util.Collection;
import java.util.Set;

/**
 * find the shortest path from the given in thw set
 * to the rest of the graph vertices
 *
 * given { 1,2,3 } and graph { 1.2.3.4.5.6 }
 *
 * 1-4:x
 * 1-5:x
 * 1-6:x
 *
 * 2-4:x
 * 2-5:x
 * 2-6:x
 *
 * 3-4:x
 * 3-5:x
 * 3-6:x
 *
 * BFS, just enqueue with many sources
 */
public interface ShortestPathMultipleSource {

  class Distance {
    public final int src;
    public final int dst;
    public final int len;

    public Distance(int src, int dst, int len) {
      this.src = src;
      this.dst = dst;
      this.len = len;
    }

    @Override
    public String toString() {
      return String.format("Dist[src=%d, dst=%d, len=%d]", src, dst, len);
    }
  }

  Collection<Distance> shortestPathMultipleSource(Set<Integer> sources);
}
