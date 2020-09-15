package algorithms.l08graph.impls;

import algorithms.l08graph.ops.IsConnected;
import algorithms.l08graph.rep.Graph;

import java.util.Collection;

/**
 * basic recursive
 * DFS implementation
 * NO LOOPS DETECTION
 * will hang on loops
 */
public class IsConnectedBasic implements IsConnected {

  private final Graph g;

  public IsConnectedBasic(Graph g) {
    this.g = g;
  }

  @Override
  public boolean isConnected(int src, int dst) {
    // we reached the final point
    if (src == dst) return true;
    // get the children from the 'from' vertex
    Collection<Integer> children = g.children(src);
    // iterate over them
    for (int child: children) {
      // try to find through the children
      if (isConnected(dst, child)) return true;
    }
    // no way from current is found - return false
    return false;
  }
}
