package algorithms.l10ewdig.rep;

public class DiEdge {
  public final int from;
  public final int to;
  public final double weight;

  public DiEdge(int from, int to, double weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  @Override
  public String toString() {
    return String.format("DiEdge[%d -> %d : %.2f]", from, to, weight);
  }
}
