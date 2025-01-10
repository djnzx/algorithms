package algorithms.l01sort;

import java.util.Comparator;

/**
 * Merge sort implementation
 * complexity: O(n * log(N))
 *
 * https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Merge_sort_algorithm_diagram.svg/300px-Merge_sort_algorithm_diagram.svg.png
 */
public class MergeSortCustomComparator {

  void merge(int[] part, int idx_l, int idx_m, int idx_r, Comparator<Integer> cmp) {
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
      if (cmp.compare(left[i], right[j]) < 0) {
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

  void sort(int[] data, int left, int right, Comparator<Integer> cmp) {
    // left  - means index of the left part of array
    // right - means index of the right part of array
    if (left < right) {
      int mid = (left + right) / 2;
      sort(data, left, mid, cmp);
      sort(data, mid + 1, right, cmp);
      merge(data, left, mid, right, cmp);
    }
  }

  public static void main(String[] args) {
    // create a random array
    int[] data = Utils.create_random_data(10);
    // make a copy not to mutate original data
    int[] sorted = data.clone();
    // create the new instance of our class
    MergeSortCustomComparator app = new MergeSortCustomComparator();
    // running sort
    app.sort(sorted, 0, data.length - 1, (i1, i2) -> i2 - i1);

    // print
    System.out.println("Merge sort: complexity: O(n*log(N))");
    System.out.printf("Source array: %s\n", Utils.arrToString(data));
    System.out.printf("Sorted array: %s\n", Utils.arrToString(sorted));
  }

}
