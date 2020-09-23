package algorithms.l09edgeweightedgraph.rep;

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
}
