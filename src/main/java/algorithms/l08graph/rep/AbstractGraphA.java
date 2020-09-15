package algorithms.l08graph.rep;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Abstract Graph.
 * it handles general structure
 * Array[Collection[Int]]
 * with maximum number of V vertices
 */
public abstract class AbstractGraphA<L extends Collection<Integer>> implements Graph {
  protected final int v;
  protected final L[] adj;

  /** just allocate the memory */
  @SuppressWarnings("unchecked")
  public AbstractGraphA(int v, Supplier<L> constructorRef) {
    this.v = v;
    this.adj = (L[]) new Collection[v];
    for (int i = 0; i < v; i++) {
      adj[i] = constructorRef.get();
    }
  }

  /** just max number of vertices */
  @Override
  public int v() {
    return this.v;
  }

  @Override
  public abstract void addEdge(int v, int w);

  @Override
  public abstract void removeEdge(int v, int w);

  /**
   * I want to be ensured that nobody will add edges
   * just by abusing getting underlying data
   * which can be modified
   */
  @Override
  public Collection<Integer> children(int v) {
    return Collections.unmodifiableCollection(adj[v]);
  }

  public Stream<Edge> represent() {
    return IntStream.range(0, v)
      .filter(s -> !adj[s].isEmpty())
      .boxed()
      .flatMap(s -> adj[s].stream().map(d -> new Edge(s, d)));
  }

  @Override
  public String toString() {
    return represent()
      .map(Edge::toString)
      .collect(Collectors.joining("\n"));
  }

  public IntStream vertices() {
    return IntStream.range(0, v);
  }

  public Stream<Edges> edges() {
    return vertices().mapToObj(src -> new Edges(src, adj[src]));
  }

}
