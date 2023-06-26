package algorithms.l08graph.impls;

import algorithms.l08graph.ops.TopologicalSorting;
import algorithms.l08graph.rep.Graph;

import java.util.*;
import java.util.stream.IntStream;

/**
 * This implementation implies
 * - having no cycles
 * - having no empty vertices
 * Actually DFS reversed.
 */
public class TopologicalSortingImpl2 implements TopologicalSorting {

  private final Graph g;
  private Stack<Integer> reversePost;

  public TopologicalSortingImpl2(Graph g) {
    this.g = g;
  }

  @Override
  public Optional<Collection<Integer>> topologicalSorting() {
    reversePost = new Stack<>();
    boolean[] marked = new boolean[g.v()];
    IntStream.range(0, g.v())
      .filter(v -> !marked[v])
      .forEach(v -> dfs(marked, v));

    Collections.reverse(reversePost);
    return Optional.of(reversePost);
  }

  private void dfs(boolean[] marked, int v) {
    marked[v] = true;
    g.children(v).forEach(w -> {
        if (!marked[w]) dfs(marked, w);
    });
    if (!g.children(v).isEmpty())
      reversePost.push(v);
  }

}
