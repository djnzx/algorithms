package algorithms.l08graph.impls;

import algorithms.l08graph.ops.IsConnected;
import algorithms.l08graph.rep.Graph;

import java.util.stream.IntStream;

/**
 * Connected Components
 * is an approach
 * to run isConnected in a constant time
 * process is done in a linear time
 *
 * This solution works only for UnDirectional (BiDirectional) Graphs
 * The approach is similar to UnionFind
 */
public class ConnectedComponents implements IsConnected {

  private final Graph g;
  private final boolean[] visited;
  private final int[] areas;
  private int counter = 0;

  public ConnectedComponents(Graph g) {
    this.g = g;
    this.areas = new int[g.v()];
    this.visited = new boolean[g.v()];
    process();
  }

  // during dfs traverse, we fill the areas structure
  private void process() {
    IntStream.range(0, g.v())
      .filter(x -> !visited[x])
      .forEach(x -> {
        counter++;
        dfs(x, counter);
      });
  }

  private void dfs(int src, int cnt) {
    if (visited[src]) return;
    visited[src] = true;
    areas[src] = cnt;
    g.children(src).forEach(w -> dfs(w, cnt));
  }

  @Override
  public boolean isConnected(int src, int dst) {
    return areas[src] == areas[dst];
  }

  public int count() {
    return counter;
  }
}
