package algorithms.l11unionfind;

/**
 * first.
 * naive implementation.
 *
 * easy to implement
 * but to expensive:
 * to join two elements
 * we have O(N)
 * quadratic algorithms don't scale.
 */
public class QuickFindUF {
  private final int[] ids;

  public QuickFindUF(int size) {
    this.ids = new int[size];
    for (int i = 0; i < size; i++) {
      ids[i] = i;
    }
  }

  public boolean connected(int p, int q) {
    return ids[p] == ids[q];
  }

  public void union(int p, int q) {
    int pid = ids[p];
    int qid = ids[q];
    for (int i = 0; i < ids.length; i++) {
      if(ids[i] == pid) ids[i] = qid;
    }
  }

}
