package algorithms.l08graph.impls;

import algorithms.l08graph.ops.HasCycles;
import algorithms.l08graph.rep.Graph;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class HasCyclesImpl implements HasCycles {

  private final Graph g;

  public HasCyclesImpl(Graph g) {
    this.g = g;
  }

  private boolean hasCycles(int src, boolean[] visited, Set<Integer> path) {
    visited[src] = true;
    path.add(src);

    for (int child: g.children(src)) {
      if (!visited[child]) {
        if (hasCycles(child, visited, path)) return true;
      } else if (path.contains(child)) {
        return true;
      }
    }

    path.remove(src);
    return false;
  }

  @Override
  public boolean hasCycles() {
    boolean[] visited = new boolean[g.v()];
    Set<Integer> path = new HashSet<>();

    return IntStream.range(0, g.v())
      .filter(v -> !visited[v])
      .anyMatch(v -> hasCycles(v, visited, path));
  }


}
