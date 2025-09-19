package algorithms.l08graph.impls;

import algorithms.l08graph.ops.TraverseBFS;
import algorithms.l08graph.rep.Graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * BFS
 * Iterative
 * with Loop Detection
 */
public class BFSIterative implements TraverseBFS {

  private final Graph g;
  private final boolean[] visited;

  public BFSIterative(Graph g) {
    this.g = g;
    visited = new boolean[g.v()];
  }

  @Override
  public Collection<Integer> traverse(int src) {
    // prepare
    Arrays.fill(visited, false);
    LinkedList<Integer> outcome = new LinkedList<>();
    LinkedList<Integer> process = new LinkedList<>();
    process.push(src);
    // run the algorithm
    bfs(process, outcome);
    // return the result
    return outcome;
  }

  private void bfs(LinkedList<Integer> process, Collection<Integer> outcome) {
    while (!process.isEmpty()) {
      int curr = process.pop();

      if (!visited[curr]) {

        visited[curr] = true;
        outcome.add(curr);

        Collection<Integer> curr_children = g.children(curr);
        process.addAll(curr_children);
      }
    }
  }

}
