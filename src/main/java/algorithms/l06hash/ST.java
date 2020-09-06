package algorithms.l06hash;

public class ST<K extends Comparable<K>,V> {

  private K[] keys;
  private V[] values;
  private final int N;

  @SuppressWarnings("unchecked")
  public ST(int size) {
    N = size;
    keys =   (K[])new Comparable[N];
    values = (V[])new Object[N];
  }

  /**
   * binary:
   *   search - LogN
   *   insert - N
   * plain array:
   *   search - N
   *   insert - N
   */
  private int rank (K key) {
    int lo = 0;
    int hi = N-1;
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2;
      int cmp = key.compareTo(keys[mid]);
      if      (cmp<0) hi = mid - 1;
      else if (cmp>0) lo = mid + 1;
      else return mid;
    }
    return lo;
  }

  public V get(K key) {
    if (isEmpty()) return null;
    int idx = rank(key);
    if (idx < N && keys[idx].compareTo(key) == 0) return values[idx];
    else return null;
  }

  public boolean isEmpty() {
    return false;
  }

}
