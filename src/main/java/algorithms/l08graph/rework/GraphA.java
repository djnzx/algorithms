package algorithms.l08graph.rework;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * I intentionally made it abstract to force user
 * to create a new instance with the new underlying structure
 * by declaring the new class and further usage it
 */
abstract public class GraphA<L extends Collection<Integer>> implements Graph {
  protected final int v;
  protected final L[] adj;

  /** just allocate the memory */
  @SuppressWarnings("unchecked")
  public GraphA(int v, Supplier<L> constructorRef) {
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

  /**
   * I don't care if given v index
   * isn't in my array
   */
  @Override
  public void addEdge(int v, int w) {
    adj[v].add(w);
  }

  /**
   * I want to be ensured that nobody will add edges
   * just by abusing getting underlying data
   * which can be modified
   */
  @Override
  public Collection<Integer> children(int v) {
    return Collections.unmodifiableCollection(adj[v]);
  }

}
