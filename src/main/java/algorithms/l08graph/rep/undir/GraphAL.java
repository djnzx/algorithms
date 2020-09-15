package algorithms.l08graph.rep.undir;

import java.util.LinkedList;

/**
 * adjacency based implementation
 * adjacency list stored in the LinkedList
 * we DO guarantee the order of vertices
 */
public class GraphAL extends GraphA<LinkedList<Integer>> {

  public GraphAL(int v) {
    super(v, LinkedList::new);
  }

}
