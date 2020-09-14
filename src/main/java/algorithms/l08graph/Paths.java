package algorithms.l08graph;

import java.util.*;

import static algorithms.l08graph.GUtils.print_visited_array;

public class Paths {

  private final XGraph graph;

  public Paths(XGraph graph) {
    this.graph = graph;
  }

  /**
   * basic implementation
   * NO LOOPS DETECTION
   *
   * @param from - vertex from
   * @param to - vertex to
   * @return - boolean whether the path is found
   */
  public boolean isConnectedBasic(int from, int to) {
    // we reached the final point
    if (from == to) return true;
    // get the children from the 'from' vertex
    Collection<Integer> children = graph.getEdgesFrom(from);
    // iterate over them
    for (int dst: children) {
      // the way found - return true
      if (isConnectedBasic(dst, to)) return true;
    }
    // no way from current is found - return false
    return false;
  }

  /**
   * advanced implementation
   * WITH LOOPS DETECTION
   *
   * @param from - vertex from
   * @param to - vertex to
   * @return - boolean whether the path is found
   *
   * runner method
   */
  public boolean isConnected(int from, int to) {
    return
//        isConnectedChatty
        isConnected
        (from, to, new boolean[graph.getVertexCount()]);
  }

  /**
   * recursive part with a lot of printing details
   * everything is the same, but we need to take into account
   * the visited[] array and mark the vertex, once if visited
   */
  private boolean isConnectedChatty(int from, int to, boolean[] visited) {
    System.out.printf("tracing path from %d to %d\n", from, to);
    print_visited_array(visited);
    if (from == to) {
      System.out.println("from == to. returning because we reached destination");
      return true;
    }
    if (visited[from]) return false;
    visited[from] = true;
    Collection<Integer> children = graph.getEdgesFrom(from);
    System.out.printf("looking for path to destination via: %s\n", children);
    for (int dst: children) {
      if (isConnectedChatty(dst, to, visited)) return true;
    }
    System.out.printf("looking for path to destination via: %s - NOT FOUND\n", children);
    return false;
  }

  /**
   * code is the same as in isConnectedChatty()
   * but all print statements removed for brevity
   */
  private boolean isConnected(int from, int to, boolean[] visited) {
    if (from == to) return true;
    if (visited[from]) return false; // to solve cycles
    visited[from] = true;            // we need only these two lines
    Collection<Integer> children = graph.getEdgesFrom(from);
    for (int dst: children) {
      if (isConnected(dst, to, visited)) return true;
    }
    return false;
  }

  /**
   * runner
   */
  public Collection<Integer> path(int from, int to) {
    return path(from, to, new boolean[graph.getVertexCount()], new Stack<>())
        .orElseGet(ArrayList::new);            // lazy ;)
//        .orElseGet(() -> new ArrayList<>());   // lazy ;)
//        .orElse(new ArrayList<>());            // eager :(
  }

  /**
   * recursive part
   * functional implementation
   */
  private Optional<Collection<Integer>> path(int from, int to, boolean[] visited, Stack<Integer> trace) {
    // quit condition to detect loops
    if (visited[from]) return Optional.empty();
    // mark vertex as visited
    visited[from] = true;
    // push current to save the path for further usage
    trace.push(from);
    // print step
//    System.out.println(trace);
    // quit condition if we reached destination
    if (from == to) return Optional.of(trace);
    // iterate over all children
    Collection<Integer> children = graph.getEdgesFrom(from);
    for (int dst: children) {
      Optional<Collection<Integer>> found = path(dst, to, visited, trace);
      // quit if we found the solution
      if (found.isPresent()) return found;
    }
    // pop current because nothing found
    trace.pop();
    // return empty
    return Optional.empty();
  }

}
