package algorithms.l09edgeweightedgraph.rep;

import java.util.Objects;

public class Edge {
  private final int v;
  private final int w;
  public final double weight;

  public Edge(int v, int w, double weight) {
    this.v = v;
    this.w = w;
    this.weight = weight;
  }

  public int either() {
    return v;
  }

  public int other(int vx) {
    return vx == v ? w : v;
  }

  @Override
  public String toString() {
    return String.format("Edge[%d - %d : %.2f]", v, w, weight);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Edge edge = (Edge) o;
    return v == edge.v &&
      w == edge.w &&
      Double.compare(edge.weight, weight) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(v, w, weight);
  }
}
