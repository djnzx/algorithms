package algorithms.l01sort;

/**
 * Merge sort implementation
 * complexity: O(n * log(N))
 *
 * https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Merge_sort_algorithm_diagram.svg/300px-Merge_sort_algorithm_diagram.svg.png
 */
public class MergeSortClean {

  void merge(int[] part, int idx_l, int idx_m, int idx_r) {
    // calculating lengths
    int size_l = idx_m - idx_l + 1;
    int size_r = idx_r - idx_m;

    // allocate extra space for arrays
    int[] left = new int[size_l];
    int[] right = new int[size_r];

    // copy source arrays to allocated space
    for (int i=0; i < size_l; ++i) {
      left[i] = part[idx_l + i];
    }
    for (int j=0; j < size_r; ++j) {
      right[j] = part[idx_m + 1 + j];
    }

    // merge these parts to new one
    int i = 0, j = 0;
    // initial index of merged sub-array
    int k = idx_l;
    while (i < size_l && j < size_r) {
      if (left[i] <= right[j]) {
        part[k++] = left[i++];
      } else {
        part[k++] = right[j++];
      }
    }

    // copy residual elements from the left part
    while (i < size_l) {
      part[k++] = left[i++];
    }

    // copy residual elements from the right part
    while (j < size_r) {
      part[k++] = right[j++];
    }
  }

  void split(int[] data, int left, int right) {
    // left  - means index of the left part of array
    // right - means index of the right part of array
    if (left < right) {
      int mid = (left + right) / 2;
      split(data, left, mid);
      split(data, mid + 1, right);
      merge(data, left, mid, right);
    }
  }

  void mergeSort(int[] data) {
    split(data, 0, data.length - 1);
  }

  public static void main(String[] args) {
    // create a random array
    int[] data = Utils.create_random_data(10);
    // make a copy not to mutate original data
    int[] sorted = data.clone();
    // create the new instance of our class
    MergeSortClean app = new MergeSortClean();
    // running sort
    app.mergeSort(sorted);
    // print
    System.out.println("Merge sort: complexity: O(n*log(N))");
    System.out.printf("Source array: %s\n", Utils.arrToString(data));
    System.out.printf("Sorted array: %s\n", Utils.arrToString(sorted));
  }

}
