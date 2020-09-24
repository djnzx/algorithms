package algorithms.l09edgeweightedgraph.rep;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO: move to impl  folder after finishing
 * TODO: move Union Find to Undirected Graphs parts
 * E * log E - sort
 * E - traverse
 * total E log E - linearithmic
 */
public class KruskalMSTinProcess {
  public static void main(String[] args) throws FileNotFoundException {
    EWG g = EdgeWeightedGraph.readFromFile("ewg.txt", 8);
    boolean[] visited = new boolean[g.v()];
    List<Edge> mst = new LinkedList<>();
    // TODO: or just PriorityQueue
    g.edgesStream().sorted(Comparator.comparingDouble(e -> e.weight))
      .filter(e -> {
        // TODO: WRONG !!!
        // TODO: chek for the cycles (DFS: O(V) or Union Find: O(LogV) ), not for just existence both
        int v = e.either();
        int w = e.other(v);
        return !(visited[v] && visited[w]);
        // return !uf.connected(u, v)
      })
      .limit(g.v() - 1)
      .forEach(e -> {
        int v = e.either();
        int w = e.other(v);
        visited[v] = true;
        visited[w] = true;
        // uf.union(u, v)
        mst.add(e);
      });

    System.out.println(mst);
  }


}
