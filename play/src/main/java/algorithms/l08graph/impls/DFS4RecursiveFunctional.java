package algorithms.l08graph.impls;

import algorithms.l08graph.ops.TraverseDFS;
import algorithms.l08graph.rep.Graph;

import java.util.*;

/**
 * DFS traverse
 * recursive version
 * WITH LOOPS DETECTION
 * almost functional (according to java syntax)
 * - no global variables
 */
public class DFS4RecursiveFunctional implements TraverseDFS {

  private final Graph g;

  public DFS4RecursiveFunctional(Graph g) {
    this.g = g;
  }

  @Override
  public Collection<Integer> traverse(int src) {
    return dfs(
      src,
      new boolean[g.v()]
    );
  }

  /**
   * strictly saying,
   * visited[] should be returned also from dfs,
   * because dfs modifies the visited list also.
   */
  private Collection<Integer> dfs(int curr, boolean[] visited) {
    if (visited[curr]) return Collections.emptyList();
    Collection<Integer> out = new LinkedList<Integer>() {{ add(curr); }};
    visited[curr] = true;
    g.children(curr).forEach(chi -> out.addAll(dfs(chi, visited)));
    return out;
  }

}
