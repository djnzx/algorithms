package algorithms.l08graph.rep;

import java.util.Collection;

/**
 * we represent Vertices
 * as integers from 0 to V-1
 */
public interface Graph {
  /**  number of vertices in the graph */
  int v();
  /** add an edge from v to w */
  void addEdge(int v, int w);
  void removeEdge(int v, int w);
  /** get a List of vertices connected directly to the given vertex */
  Collection<Integer> children(int v);
}
