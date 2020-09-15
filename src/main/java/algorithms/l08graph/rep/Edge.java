package algorithms.l08graph.rep;

import java.util.Objects;

public class Edge {
  public final int s;
  public final int d;

  public Edge(int s, int d) {
    this.s = s;
    this.d = d;
  }

  @Override
  public String toString() {
    return String.format("%d -> %d", s, d);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Edge edge = (Edge) o;
    return s == edge.s
      && d == edge.d;
  }

  @Override
  public int hashCode() {
    return Objects.hash(s, d);
  }
}

