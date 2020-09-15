package algorithms.l08graph.rework.impls;

import algorithms.l08graph.rework.ops.TraverseDFS;
import algorithms.l08graph.rework.rep.Graph;

import java.util.*;

/**
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

  static class StackFrame {
    public final int curr;
    public final Iterator<Integer> children;

    StackFrame(int curr, Iterator<Integer> children) {
      this.curr = curr;
      this.children = children;
    }

    static StackFrame of(int vertex, Graph g) {
      return new StackFrame(vertex, g.children(vertex).iterator());
    }
  }

  private void dfs(int src, LinkedList<Integer> seq) {
    Stack<StackFrame> stack = new Stack<>();
    stack.push(StackFrame.of(src, g));

    while (!stack.isEmpty()) {
      StackFrame sf = stack.peek();

      if (!visited[sf.curr]) {
        visited[sf.curr] = true;
        seq.add(sf.curr);
      }

      if (sf.children.hasNext()) {
        int chi = sf.children.next();

        if (!visited[chi]) {
          stack.push(StackFrame.of(chi, g));
        }

      } else stack.pop();

    }

  }

}
