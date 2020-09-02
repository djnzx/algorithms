package algorithms.l12binaryheap;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * In the binary heap -
 * children must be smaller than their parents
 *
 * the root is the max value
 *
 * we represent tree in an array:
 * parent of K is k/2
 * children of K are 2k and 2k+1
 * (indices)
 *
 * actually, it's a PriorityQueue with:
 * - O(logN) insertion
 * - O(logN) deletion
 * - O(1) peek
 */
public class BinaryHeap<A extends Comparable<A>> {

  private int n;
  private A[] a;

  @SuppressWarnings("unchecked")
  public BinaryHeap(int cap) {
    this.a = (A[]) new Comparable[cap];
  }

  private void swap(int i, int j) {
    A t = a[i];
    a[i] = a[j];
    a[j] = t;
  }

  private boolean less(int i, int j) {
    return a[i].compareTo(a[j]) < 0;
  }

  /** API */
  public boolean isEmpty() {
    return n == 0;
  }

  public void insert(A v) {
    a[++n] = v;
    swim(n);
  }

  public A delMax() {
    A max = a[1]; // take the root is at the a[1]
    swap(1, n); // put the last at 1
    a[n] = null;  // to prevent loitering
    n -= 1;       // adjust pointer
    sink(1);   // sink to appropriate place
    return max;
  }

  /** implementation */
  private void swim(int k) {
    while (k > 1 && less(k/2, k)) {
      swap(k, k/2);
      k /= 2;
    }
  }

  private void sink(int k) {
    while (2*k <= n) {
      int k2 = 2*k;
      if (k2 < n && less(k2, k2+1)) k2 += 1;
      if (!(less(k, k2))) break;
      swap(k, k2);
      k = k2;
    }
  }

  @Override
  public String toString() {
    return String.format("%d : %s", n,
        Arrays.stream(a)
            .filter(Objects::nonNull)
            .map(Objects::toString)
            .collect(Collectors.joining(" -> ")));
  }

}
