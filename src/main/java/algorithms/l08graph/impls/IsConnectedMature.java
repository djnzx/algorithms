package algorithms.l08graph.impls;

import algorithms.l08graph.ops.IsConnected;
import algorithms.l08graph.rep.Graph;

import java.util.Collection;

/**
 * plain recursive
 * DFS
 * with Loop Detection
 */
public class IsConnectedMature implements IsConnected {

  private final Graph g;

  public IsConnectedMature(Graph g) {
    this.g = g;
  }

  private boolean dfs(int src, int dst, boolean[] visited) {
    if (src == dst) return true;
    if (visited[src]) return false; // to solve cycles
    visited[src] = true;            // we need only these two lines
    Collection<Integer> children = g.children(src);
    for (int child: children) {
      if (dfs(child, dst, visited)) return true;
    }
    return false;
  }

  @Override
  public boolean isConnected(int src, int dst) {
    return dfs(src, dst, new boolean[g.v()]);
  }

}
