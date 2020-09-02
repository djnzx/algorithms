package algorithms.l01sort;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * the main Idea
 * is to build BinaryHeap and mark elements with their indexes
 *
 * - In place, no extra memory
 * - 2NLogN ~> NLogN
 * - UNSTABLE
 * - cache is poorly used
 */
public class HeapSort<A extends Comparable<A>> {
  /**
   * start from an array
   * and try to sink all elements from next to last line
   * like we do in BFS but from the bottom
   */

  private void swap(A[] a, int i, int j) {
    A t = a[i];
    a[i] = a[j];
    a[j] = t;
  }

  private boolean less(A[] a, int i, int j) {
    return a[i].compareTo(a[j]) < 0;
  }

  private void swim(A[] a, int k) {
    while (k > 1 && less(a, k/2-1, k-1)) {
      swap(a, k-1, k/2-1);
      k /= 2;
    }
  }

  private void sink(A[] a, int k, int N) {
    while (2*k <= N) {
      int k2 = 2*k;
      if (k2 < N && less(a, k2-1, k2+1-1)) k2 += 1;
      if (!(less(a, k-1, k2-1))) break;
      swap(a, k-1, k2-1);
      k = k2;
    }
  }

  public void sort(A[] a) {
    // creating binary heap
    int N = a.length;
    for (int k = N/2; k >= 1; k--) sink(a, k, N);
    System.out.println(Arrays.toString(a));
    while (N > 1) {
      swap(a, 0, N-1);
      sink(a, 1, --N);
    }
  }

  /**
   * SORTEXAMPLE =>
   * XTSPLRAMOEE =>
   * AEELMOPRSTX
   */
  public static void main(String[] args) {
//    String[] data = "SORTEXAMPLE".chars().mapToObj(x -> String.valueOf((char) (x))).toArray(String[]::new);
    String[] data = Stream.generate(() -> String.valueOf((char) (Math.random() * ('Z' - 'A') + 'A')))
        .distinct().limit(11).toArray(String[]::new);
    String[] sorted = data.clone();
    HeapSort<String> app = new HeapSort<>();

    System.out.println(Arrays.toString(data));
    app.sort(sorted);

    System.out.println(Arrays.toString(sorted));
  }
}
