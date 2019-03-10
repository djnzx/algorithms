package algorithms.sort;

public class MergeSort {
  private static int counter_check = 0;
  private static int counter_permutation = 0;

  void merge(int[] arr, int l, int m, int r) {
    int size_l = m - l + 1;
    int size_r = r - m;

    int[] left = new int[size_l];
    int[] right = new int[size_r];

    // Copy
    for (int i=0; i < size_l; ++i) {
      left[i] = arr[l + i];
      counter_check++;
    }
    for (int j=0; j < size_r; ++j) {
      right[j] = arr[m + 1 + j];
      counter_check++;
    }

    // Merge
    int i = 0, j = 0;
    // Initial index of merged sub-array
    int k = l;
    while (i < size_l && j < size_r) {
      counter_check++;
      if (left[i] <= right[j]) {
        counter_check++;
        arr[k] = left[i++];
      } else {
        counter_check++;
        arr[k] = right[j++];
      }
      k++;
    }

    while (i < size_l) {
      counter_check++;
      arr[k++] = left[i++];
    }

    while (j < size_r) {
      counter_check++;
      arr[k++] = right[j++];
    }
  }

  void sort(int[] arr, int l, int r) {
    if (l < r) {
      int m = (l+r)/2;
      sort(arr, l, m);
      sort(arr, m+1, r);
      merge(arr, l, m, r);
    }
  }

  public static void main(String[] args) {
    int[] arr = Utils.create_random_data(30);
    Utils.printArray("Source array", arr);
    new MergeSort().sort(arr, 0, arr.length-1);
    Utils.printArray("Sorted array", arr);
    System.out.printf("Checks count: %d\nPermutations count: %d\n", counter_check, counter_permutation);
    System.out.printf("Total: %d\n", counter_check * 2 + counter_permutation * 4);
  }
}
