package algorithms.sort;

/**
 * https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Merge_sort_algorithm_diagram.svg/300px-Merge_sort_algorithm_diagram.svg.png
 */

public class MergeSort {
  private static int counter_check = 0;
  private static int counter_permutation = 0;

  void merge(int[] arr, int l_index, int middle_index, int r_index) {
    int size_l = middle_index - l_index + 1;
    int size_r = r_index - middle_index;

    int[] left = new int[size_l];
    int[] right = new int[size_r];

    // Copy
    for (int i=0; i < size_l; ++i) {
      left[i] = arr[l_index + i];
      counter_check++;
    }
    for (int j=0; j < size_r; ++j) {
      right[j] = arr[middle_index + 1 + j];
      counter_check++;
    }

    // Merge
    int i = 0, j = 0;
    // Initial index of merged sub-array
    int k = l_index;
    while (i < size_l && j < size_r) {
      if (left[i] <= right[j]) {
        arr[k] = left[i++];
      } else {
        arr[k] = right[j++];
      }
      k++;
      counter_permutation++;
    }

    while (i < size_l) {
      arr[k++] = left[i++];
    }

    while (j < size_r) {
      arr[k++] = right[j++];
    }
  }

  void sort(int[] arr, int l, int r) {
    if (l < r) {
      int m = (l + r) / 2;
      sort(arr, l, m);
      sort(arr, m + 1, r);
      merge(arr, l, m, r);
    }
  }

  public static void main(String[] args) {
    int[] data = Utils.create_random_data(30);
    Utils.printArray("Source array", data);

    MergeSort ms = new MergeSort();
    ms.sort(data, 0, data.length-1);

    Utils.printArray("Sorted array", data);

    System.out.printf("Checks count: %d\nPermutations count: %d\n", counter_check, counter_permutation);
    System.out.printf("Total: %d\n", counter_check * 2 + counter_permutation * 4);
  }
}
