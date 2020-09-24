package algorithms.l11unionfind.impl;

import algorithms.l11unionfind.rep.UnionFind;

/**
 * 3. improvement #1.
 * connect smaller to bigger.
 *
 * we will keep number of element in each tree
 * and ALWAYS connect smaller tree to a larger one
 * so, we can guarantee that tree isn't tall
 */
public class UnionFindV3 implements UnionFind {
  private final int[] id;
  private final int[] sz;

  public UnionFindV3(int size) {
    this.id = new int[size];
    this.sz = new int[size];

    for (int i = 0; i < size; i++) {
      id[i] = i;
      sz[i] = 1;
    }
  }

  /** O(c) */
  private int root(int i) {
    while (i != id[i]) {
      i = id[i];
    }
    return i;
  }

  @Override
  public int v() {
    return id.length;
  }

  /** O(lg N) */
  @Override
  public void union(int p, int q) {
    int i = root(p);
    int j = root(q);
    if (i == j) return;
    if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
    else               { id[j] = i; sz[i] += sz[j]; }
  }

  /** O(lg N) */
  @Override
  public boolean isConnected(int p, int q) {
    return root(p) == root(q);
  }

}
