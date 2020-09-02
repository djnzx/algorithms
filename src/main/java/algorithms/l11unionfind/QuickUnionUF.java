package algorithms.l11unionfind;

/**
 * second.
 * tree based implementation.
 *
 * trees can get tall and long
 * so, connected is too expensive
 */
public class QuickUnionUF {
  private final int[] ids;

  public QuickUnionUF(int size) {
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

  public boolean connected(int p, int q) {
    return root(p) == root(q);
  }

  public void union(int p, int q) {
    int i = root(p);
    int j = root(q);
    ids[i] = j;
  }

}
