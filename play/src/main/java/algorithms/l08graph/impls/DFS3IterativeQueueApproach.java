package algorithms.l08graph.impls;

import algorithms.l08graph.ops.TraverseDFS;
import algorithms.l08graph.rep.Graph;

import java.util.*;

import static algorithms.l08graph.Tools.toLL;

/**
 * DFS traverse
 * Iterative version (Queue)
 * WITH LOOPS DETECTION
 */
public class DFS3IterativeQueueApproach implements TraverseDFS {

  private final Graph g;
  private final boolean[] visited;

  public DFS3IterativeQueueApproach(Graph g) {
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

  private void dfs(int src, LinkedList<Integer> outcome) {
    LinkedList<Integer> process = new LinkedList<>();
    process.add(src);
    while (!process.isEmpty()) {
      int curr = process.poll();
      if (visited[curr]) continue;
      visited[curr] = true;
      outcome.add(curr);
      Collection<Integer> children = g.children(curr);
      // add in front of queue, because we need DEPTH FIRTH
      toLL(children).descendingIterator().forEachRemaining(process::addFirst);
    }
  }

}
