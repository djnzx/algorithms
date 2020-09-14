package algorithms.l08graph.rework.impls;

import algorithms.l08graph.rework.ops.TraverseDFS;
import algorithms.l08graph.rework.rep.Graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class DFSRecursivePlain implements TraverseDFS {

  private final Graph g;
  private final boolean[] visited;

  public DFSRecursivePlain(Graph g) {
    this.g = g;
    visited = new boolean[g.v()];
  }

  @Override
  public Collection<Integer> traverse(int src) {
    Arrays.fill(visited, false);
    LinkedList<Integer> outcome = new LinkedList<>();
    dfs(src, outcome);
    return outcome;
  }

  private void dfs(int curr, LinkedList<Integer> seq) {
    if (visited[curr]) return;

    visited[curr] = true;
    seq.add(curr);

    Collection<Integer> curr_children = g.children(curr);
    for (int child: curr_children)
      dfs(child, seq);
  }


}
