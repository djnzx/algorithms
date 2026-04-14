package algorithms.l01sort;

/**
 * find the min (of the rest)
 * and swap with the current
 *
 * actually, it's a BubbleSort
 * but never call it BubbleSort!
 */
public class SelectionSort {

  private void swap(int[] a, int i, int j) {
    int t = a[i];
    a[i] = a[j];
    a[j] = t;
  }

  /**
   * finds the index of minimal value
   * in the range [i, j)
   */
  private int findMinIdx(int[] a, int l, int r) {
    int minV = a[l];
    int minI = l;
    for (int i = l+1; i < r; i++) {
      if (a[i] < minV) {
        minV = a[i];
        minI = i;
      }
    }
    return minI;
  }

  public void sort(int[] a) {
    for (int i = 0; i < a.length - 1; i++) { // we don't need to touch the las one, it will be already in order
      int min_idx = findMinIdx(a, i, a.length);
      if (min_idx != i) swap(a, min_idx, i);
    }
  }

  public static void main(String[] args) {
    int[] data = Utils.create_random_data(10);
    int[] sorted = data.clone();
    SelectionSort app = new SelectionSort();
    // running sort
    app.sort(sorted);

    System.out.println("SelectionSort: complexity: O(n*n/2) ~ o(n^2)");
    System.out.printf("Source array: %s\n", Utils.arrToString(data));
    System.out.printf("Sorted array: %s\n", Utils.arrToString(sorted));
  }

}
