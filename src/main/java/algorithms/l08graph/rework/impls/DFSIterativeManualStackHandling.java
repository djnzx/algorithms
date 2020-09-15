package algorithms.l08graph.rework.impls;

import algorithms.l08graph.rework.ops.TraverseDFS;
import algorithms.l08graph.rework.rep.Graph;
import sun.jvm.hotspot.StackTrace;

import java.util.*;

/**
 * TODO:
 * DFS
 * Iterative
 * with Manual Stack Handling
 */
public class DFSIterativeManualStackHandling implements TraverseDFS {

  private final Graph g;
  private final boolean[] visited;

  public DFSIterativeManualStackHandling(Graph g) {
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

  private void markAndAdd(int curr, Collection<Integer> seq) {
    visited[curr] = true;
    seq.add(curr);
  }

  static class StackFrame {
    public final int curr;
    public final Iterator<Integer> children;

    StackFrame(int curr, Iterator<Integer> children) {
      this.curr = curr;
      this.children = children;
    }
  }

  private void dfs(int curr, LinkedList<Integer> seq) {
    Stack<StackFrame> stack = new Stack<>();


    markAndAdd(curr, seq);

    Iterator<Integer> children = g.children(curr).iterator();

    while (children.hasNext()) {
      int child = children.next();
      if (!visited[child])
        dfs(child, seq);
    }

  }


}
