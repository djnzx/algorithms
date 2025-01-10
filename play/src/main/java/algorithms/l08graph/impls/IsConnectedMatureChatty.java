package algorithms.l08graph.impls;

import algorithms.l08graph.ops.IsConnected;
import algorithms.l08graph.rep.Graph;

import java.util.Collection;

import static algorithms.l08graph.Tools.print_visited_array;

/**
 * plain recursive
 * DFS
 * with Loop Detection
 */
public class IsConnectedMatureChatty implements IsConnected {

  private final Graph g;

  public IsConnectedMatureChatty(Graph g) {
    this.g = g;
  }

  private boolean dfs(int src, int dst, boolean[] visited) {
    System.out.printf("tracing path from %d to %d\n", src, dst);
    print_visited_array(visited);
    if (src == dst) {
      System.out.println("from == to. returning because we reached destination");
      return true;
    }
    if (visited[src]) return false;
    visited[src] = true;
    Collection<Integer> children = g.children(src);
    System.out.printf("looking for path to destination via: %s\n", children);
    for (int child: children) {
      if (dfs(child, dst, visited)) return true;
    }
    System.out.printf("looking for path to destination via: %s - NOT FOUND\n", children);
    return false;
  }

  @Override
  public boolean isConnected(int src, int dst) {
    return dfs(src, dst, new boolean[g.v()]);
  }

}
