package algorithms.l08graph.impls;

import algorithms.l08graph.ops.PathFromTo;
import algorithms.l08graph.rep.Graph;

import java.util.Collection;
import java.util.Optional;
import java.util.Stack;

public class FirstPathDFS implements PathFromTo {

  private final Graph g;

  public FirstPathDFS(Graph g) {
    this.g = g;
  }

  @Override
  public Optional<Collection<Integer>> path(int src, int dst) {
    return path(src, dst, new boolean[g.v()], new Stack<>());
  }

  private Optional<Collection<Integer>> path(int src, int dst, boolean[] visited, Stack<Integer> trace) {
    // quit condition to detect loops
    if (visited[src]) return Optional.empty();
    // mark vertex as visited
    visited[src] = true;
    // push current to save the path for further usage
    trace.push(src);
    // quit condition if we reached destination
    if (src == dst) return Optional.of(trace);
    // iterate over all children
    Collection<Integer> children = g.children(src);
    for (int child: children) {
      Optional<Collection<Integer>> found = path(child, dst, visited, trace);
      // quit if we found the solution
      if (found.isPresent()) return found;
    }
    // pop current because nothing found
    trace.pop();
    // return empty
    return Optional.empty();
  }
}
