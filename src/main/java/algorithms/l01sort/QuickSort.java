package algorithms.l01sort;

public class QuickSort {
  /**
   * sort in place
   */
  private static void doSort(final int[] data, final int L, final int R) {
    if (L >= R) return;
    int l = L;
    int r = R;
    // by design - middle of the array, it can be anything
    int pivot = l - (l - r) / 2;
    while (l < r) {
      // skip already sorted left side
      while (data[l] <= data[pivot] && l < pivot) { l++; }
      // skip already sorted right side
      while (data[pivot] <= data[r] && pivot < r) { r--; }
      // now, actually sort
      if (l < r) {
        int tmp = data[l];
        data[l] = data[r];
        data[r] = tmp;
        if (pivot == l) { pivot = r; }
        else if (pivot == r) { pivot = l; }
      }
    }
    doSort(data, L, pivot);
    doSort(data, pivot + 1, R);
  }

  private static int[] sort(int[] origin) {
    int[] process = origin.clone();
    // algorithm will be there
    doSort(process, 0, process.length-1);
    return process;
  }

  public static void main(String[] args) {
    int[] data = Utils.create_random_data(100);

    System.out.printf("Source array: %s\n", Utils.arrToString(data));
    int[] sorted = sort(data);
    System.out.printf("Sorted array: %s\n", Utils.arrToString(sorted));
  }
}
