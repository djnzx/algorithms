package algorithms.l01sort;

/**
 * best case:
 *   compares: O(N lg (N))
 * worst case:
 *   compares: O(N^2 /2)
 * average case:
 *   1.39 * N * lgN (not log)!
 * + 40% compares to mergesort but less moving
 * Quicksort is not stable
 * improvements:
 *   - if data is small => insertion sort
 *
 */
public class QuickSort2 {

  private void swap(int[] a, int i, int j) {
    int t = a[i];
    a[i] = a[j];
    a[j] = t;
  }

  private int partition(int[] a, int lo, int hi) {
    int i = lo;
    int j = hi+1;
    // we take 1st element as a pivot: lo and a[lo]
    while (true) {
      while (a[++i] < a[lo]) if (i == hi) break; // move left pointer
      while (a[lo] < a[--j]) if (j == lo) break; // move right pointer
      if (i >= j) break;
      swap(a, i, j); // swap pair
    }
    swap(a, lo, j); // swap with partitioned (pivot)
    return j;
  }

  // original MUST be WELL-shuffled !!!
  void quicksort(int[] a, int lo, int hi) {
    if (hi <= lo) return;;
    int j = partition(a, lo, hi);
    quicksort(a, lo, j-1);
    quicksort(a, j+1, hi);
  }

  public static void main(String[] args) {
    int[] data = Utils.create_random_data(24);
    int[] sorted = data.clone();
    QuickSort2 app = new QuickSort2();

    app.quicksort(sorted, 0, sorted.length-1);

    System.out.printf("Source array: %s\n", Utils.arrToString(data));
    System.out.printf("Sorted array: %s\n", Utils.arrToString(sorted));
  }
}
