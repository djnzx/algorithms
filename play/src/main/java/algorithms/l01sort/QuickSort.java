package algorithms.l01sort;

public class QuickSort {

  private static void swap(int[] a, int i, int j) {
    int t = a[i];
    a[i] = a[j];
    a[j] = t;
  }
  /**
   * sort in place
   */
  private static void doSort(final int[] data, final int L, final int R) {
    if (L >= R) return; // we done
    int l = L;
    int r = R;
    // by design - middle of the array, it can be anything
    int pivot = l - (l - r) / 2;
    while (l < r) {
      while (data[l] <= data[pivot] && l < pivot) { l++; } // skip already sorted left side
      while (data[pivot] <= data[r] && pivot < r) { r--; } // skip already sorted right side
      // now, actually sort
      if (l < r) {
        swap(data, l, r);
        if      (pivot == l) { pivot = r; }
        else if (pivot == r) { pivot = l; }
      }
    }
    doSort(data, L, pivot);
    doSort(data, pivot + 1, R);
  }

  public static void main(String[] args) {
    int[] data = Utils.create_random_data(24);
    int[] sorted = data.clone();

    doSort(sorted, 0, sorted.length-1);

    System.out.printf("Source array: %s\n", Utils.arrToString(data));
    System.out.printf("Sorted array: %s\n", Utils.arrToString(sorted));
  }
}
