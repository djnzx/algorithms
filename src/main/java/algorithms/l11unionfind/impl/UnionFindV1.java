package algorithms.l11unionfind.impl;

import algorithms.l11unionfind.rep.UnionFind;

/**
 * 1. Naive Implementation.
 *
 * easy to implement
 * but to expensive:
 * 
 * union - O(N)
 * check - O(1)
 * 
 * quadratic algorithms don't scale.
 */
public class UnionFindV1 implements UnionFind {
  private final int[] ids;

  public UnionFindV1(int size) {
    this.ids = new int[size];
    for (int i = 0; i < size; i++) {
      ids[i] = i;
    }
  }

  @Override
  public int v() {
    return ids.length;
  }

  @Override
  public void union(int p, int q) {
    int pid = ids[p];
    int qid = ids[q];
    for (int i = 0; i < ids.length; i++) {
      if(ids[i] == pid) ids[i] = qid;
    }
  }

  @Override
  public boolean isConnected(int p, int q) {
    return ids[p] == ids[q];
  }

}
