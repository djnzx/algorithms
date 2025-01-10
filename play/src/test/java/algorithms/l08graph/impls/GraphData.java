package algorithms.l08graph.impls;

import algorithms.l08graph.rep.Graph;
import algorithms.l08graph.rep.dir.GraphAL;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GraphData {

  public static final Graph graph1_dfs = new GraphAL(12, new int[][]{
    {0, 1},
    {0, 2},
    {0, 3},
    {1, 4},
    {2, 5},
    {5, 8},
    {5, 9},
    {3, 10},
    {10, 11},
    {11, 3},
  });

  public static final Iterable<Integer> graph1_dfs_expected = Arrays.asList(0,1,4,2,5,8,9,3,10,11);

  public static final Graph graph2_bfs = new GraphAL(102, new int[][]{
    {0, 101},
    {0, 2},
    {0, 13},

    {101, 4},
    {2, 51},
    {13, 6},

    {6, 7},
    {0, 99},
    {101, 41},
    {101, 42},
  });

  public static final Iterable<Integer> graph2_bfs_expected =
    Arrays.asList(0, 101,2,13,99, 4,41,42,51,6, 7);

  public static Graph graph3_path = new GraphAL(50, new int[][]{
    {0, 1},
    {1, 2},
    {1, 3},
    {2, 4},
    {2, 5},
    {4, 8},
    {4, 9},
    {5, 10},
    {5, 11},
    {3, 6},
    {3, 7},
    {6, 12},
    {7, 13},
    {7, 14},
    {12, 15},
    // will produce stack overflow in Graph.isConnectedBasic(from, to)
    {6, 16},
    {16,3}
  });

  public static final Optional<Iterable<Integer>> graph3_path0to15 = Optional.of(Arrays.asList(0, 1, 3, 6, 12, 15));
  public static final Optional<Iterable<Integer>> graph3_path6to13 = Optional.of(Arrays.asList(6, 16, 3, 7, 13));
  public static final Optional<Iterable<Integer>> graph3_path2to3 = Optional.empty();

  public static final Graph graph41_cycles = new GraphAL(10, new int[][]{
    {0, 1},
    {0, 2},
    {2, 3},
    {1, 3},
  });

  public static Graph graph42_cycles = new GraphAL(10, new int[][]{
      {0, 1},
      {1, 2},
      {2, 3},
      {3, 1},
    });

  public static final boolean graph41_hasCycles = false;
  public static final boolean graph42_hasCycles = true;

  public static Graph graph51_topological = new GraphAL(4, new int[][]{
    {0, 1},
    {0, 2},
    {0, 3},
    {1, 2},
    {1, 3},
    {3, 2}
  });

  public static Graph graph52_topological = new GraphAL(5, new int[][]{
    {0, 1},
    {0, 2},
    {0, 3},
    {1, 2},
    {2, 3},
    {3, 1}
  });

  public static Optional<List<Integer>> graph51_topological_exp = Optional.of(Arrays.asList(0, 1, 3, 2));
  public static Optional<List<Integer>> graph52_topological_exp = Optional.empty();

}
