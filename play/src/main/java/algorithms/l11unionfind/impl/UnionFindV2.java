package algorithms.l11unionfind.impl;

import algorithms.l11unionfind.rep.UnionFind;

/**
 * 2. Tree based implementation.
 *
 * trees can get tall and long
 * so, connected is too expensive
 * 
 * union - O(1..N)
 * check - O(1..N)
 */
public class UnionFindV2 implements UnionFind {
  private final int[] ids;

  public UnionFindV2(int size) {
    this.ids = new int[size];
    for (int i = 0; i < size; i++) {
      ids[i] = i;
    }
  }

  private int root(int i) {
    while (i != ids[i]) {
      i = ids[i];
    }
    return i;
  }

  @Override
  public int v() {
    return ids.length;
  }

  @Override
  public void union(int p, int q) {
    int i = root(p);
    int j = root(q);
    ids[i] = j;
  }

  @Override
  public boolean isConnected(int p, int q) {
    return root(p) == root(q);
  }

}
