package algorithms.l11unionfind;

/**
 * fourth.
 * improvement 2.
 * path compression.
 *
 * each time we are looking for item,
 * we rewrite the parent.
 */
public class QuickUnionWeightedPathCompressionUF {
  private final int[] id;
  private final int[] sz;

  public QuickUnionWeightedPathCompressionUF(int size) {
    this.id = new int[size];
    for (int i = 0; i < size; i++) {
      id[i] = i;
    }

    this.sz = new int[size];
    for (int i = 0; i < size; i++) {
      sz[i] = 1;
    }
  }

  /** O(c) */
  private int root(int i) {
    while (i != id[i]) {
      // only one line
      id[i] = id[id[i]];
      i = id[i];
    }
    return i;
  }

  /** O(lg N) */
  public boolean connected(int p, int q) {
    return root(p) == root(q);
  }

  /** O(lg N) */
  public void union(int p, int q) {
    int i = root(p);
    int j = root(q);
    if (i == j) return;
    if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
    else               { id[j] = i; sz[i] += sz[j]; }
  }

}
