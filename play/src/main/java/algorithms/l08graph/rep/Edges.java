package algorithms.l08graph.rep;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class Edges {
  public final int src;
  public final Collection<Integer> dst;

  public Edges(int src, Collection<Integer> dst) {
    this.src = src;
    this.dst = dst;
  }

  @Override
  public String toString() {
    return String.format("[%d -> %s]", src,
      dst.stream().map(Objects::toString).collect(Collectors.joining(","))
    );
  }
}
