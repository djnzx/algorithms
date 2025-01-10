package algorithms.l08graph.rep.undir;

import java.util.HashSet;

/**
 * adjacency based implementation
 * adjacency list stored in the Set
 * we CAN'T guarantee the order of vertices
 */
public class GraphAS extends GraphA<HashSet<Integer>> {

  public GraphAS(int v) {
    super(v, HashSet::new);
  }

}
