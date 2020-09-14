package algorithms.l08graph;

/**
 * we represent Vertices
 * as integers from 0 to V-1
 */
public interface AGraph {

//  AGraph(int V) {}

  void addEdge(int v, int w);

  Iterable<Integer> adj(int v);
  int V();// {}
  int E();// {}
}
