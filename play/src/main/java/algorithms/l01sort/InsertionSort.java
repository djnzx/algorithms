package algorithms.l01sort;

/**
 * we iterate over the elements
 * and try to check current with all to left
 * until it's sorted
 *
 * Performance depends on data
 * best case:
 * - (n-1) compares
 * - 0 swaps
 *
 * worst case:
 * - n^2/2 compares
 * - n^2/2 swaps
 *
 * works extremely well with inverted pairs of data
 * partially sorted - if we need only c < N swaps
 */
public class InsertionSort {

  private void swap(int[] a, int i, int j) {
    int t = a[i];
    a[i] = a[j];
    a[j] = t;
  }

  /**
   * checks left element and swap if need
   * if swapped -> false
   * if in order -> true
   */
  private boolean checkAtLeft(int[] a, int pos) {
    if (a[pos] < a[pos - 1]) {
      swap(a, pos, pos - 1);
      return false;
    }
    return true;
  }

  void sortToLeft(int[] a, int pos) {
    for (int i = pos; i > 0 ; i--) { // we don't need to touch the first element
      if (checkAtLeft(a, i)) break;
    }
  }

  void sort(int[] a) {
    for (int i = 1; i < a.length; i++) {
      sortToLeft(a, i);
    }
  }

  public static void main(String[] args) {
    int[] data = Utils.create_random_data(10);
    int[] sorted = data.clone();
    InsertionSort app = new InsertionSort();
    // running sort
    app.sort(sorted);

    System.out.println("Insertion Sort: complexity: O( < n^2)");
    System.out.printf("Source array: %s\n", Utils.arrToString(data));
    System.out.printf("Sorted array: %s\n", Utils.arrToString(sorted));
  }

}
