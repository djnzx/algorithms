package algorithms.l11unionfind.disjoint;

import java.util.*;
import java.util.function.IntConsumer;

/**
 * for detailed discussion
 * and optimized versions
 * go l11unionfind folder
 *
 */
public class DisjointSet {
  private final int[] sets;

  public DisjointSet(int count) {
    sets = new int[count];
    for (int i = 0; i < count; i++) {
      sets[i] = i;
    }
  }

  void add(int from, int to) {
    int rootTo = root(to);
    int rootFrom = root(from);
    sets[rootFrom] = rootTo;
  }

  private int root(int item) {
    while (item != sets[item]) {
      item = sets[item];
    }
    return item;
  }

  public List<Integer> sets() {
    Arrays.stream(sets).forEach(new IntConsumer() {
      @Override
      public void accept(int value) {
        System.out.printf("%d ",value);
      }
    });
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < sets.length; i++) {
      int elem = sets[i];
      if (elem != sets[i]) {
        int cnt = map.getOrDefault(elem, 0);
        map.put(elem,++cnt);
      }
    }
    ArrayList<Integer> l = new ArrayList<>();
    map.forEach((k, v) -> l.add(v));
    return l;
  }
}
