package algorithms.l08graph.impls;

import algorithms.l08graph.rep.Graph;
import algorithms.l08graph.rep.dir.GraphAL;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MapData {

  public static final String[] cities = {
    "Київ", "Житомир", "Лубни", "Бориспіль", "Фастів", "Ніжин", "Умань", "Суми", "Хмельницький", "Миколаїв"};
    // 0       1          2        3            4         5         6        7       8               9

  public static final int[][] map = {
/* 0 Київ => ...      */ {1, 5, 7, 8, 9},
/* 1 Житомир => ...   */ {0, 2, 8},
/* 2 Лубни => ...     */ {4, 9},
/* 3 Бориспіль => ... */ {2, 5},
/* 4 Фастів => ...    */ {9},
/* 5 Ніжин => ...     */ {0, 3},
/* 6 Умань => ...     */ {8, 9},
/* 7 Суми => ...      */ {0, 2, 6},
/* 8 Хмельн. => ...   */ {6},
/* 9 Миколаїв => ..   */ {2, 6}
  };

  public static Graph graph() {
    Graph g = new GraphAL(10);

    IntStream.range(0, map.length).forEach(src ->
      Arrays.stream(map[src]).forEach(dst ->
        g.addEdge(src, dst)
      )
    );

    return g;
  }

}
