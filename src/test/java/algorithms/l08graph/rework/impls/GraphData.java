package algorithms.l08graph.rework.impls;

import algorithms.l08graph.rework.rep.Graph;
import algorithms.l08graph.rework.rep.dir.GraphAL;

import java.util.Arrays;

public class GraphData {

  public static Graph graph1_dfs() {
    GraphAL g = new GraphAL(12);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(0, 3);

    g.addEdge(1, 4);
    g.addEdge(2, 5);

    g.addEdge(5, 8);
    g.addEdge(5, 9);

    g.addEdge(3, 10);
    g.addEdge(10, 11);
    g.addEdge(11, 3);

    return g;
  }

  public static Iterable<Integer> graph1_dfs_expected = Arrays.asList(0,1,4,2,5,8,9,3,10,11);

  public static Graph graph2_bfs() {
    GraphAL g = new GraphAL(102);
    g.addEdge(0, 101);
    g.addEdge(0, 2);
    g.addEdge(0, 13);

    g.addEdge(101, 4);
    g.addEdge(2, 51);
    g.addEdge(13, 6);

    g.addEdge(6, 7);
    g.addEdge(0, 99);
    return g;
  }

  public static Iterable<Integer> graph2_bfs_expected = Arrays.asList(0, 101,2,13,99, 4,51,6,7);

}
