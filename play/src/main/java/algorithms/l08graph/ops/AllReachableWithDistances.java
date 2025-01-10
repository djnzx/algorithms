package algorithms.l08graph.ops;

import java.util.Collection;

public interface AllReachableWithDistances {

  class Distance {
    public final int vertex;
    public final int distance;

    public Distance(int vertex, int distance) {
      this.vertex = vertex;
      this.distance = distance;
    }

    @Override
    public String toString() {
      return String.format("Dist[V=%d, d=%d]", vertex, distance);
    }
  }

  Collection<Distance> allReachableWithDistances(int src);
}
