package algorithms.l11unionfind.impl;

import algorithms.l11unionfind.rep.UnionFind;

/**
 * 4. improvement 2.
 * path compression.
 *
 * each time we are looking for item,
 * we rewrite the parent.
 */
public class UnionFindV4 implements UnionFind {
  private final int[] id;
  private final int[] sz;

  public UnionFindV4(int size) {
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
      // only one line, rewrite the path
      id[i] = id[id[i]];
      i = id[i];
    }
    return i;
  }

  @Override
  public int v() {
    return id.length;
  }

  /** O(lg N) */
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
