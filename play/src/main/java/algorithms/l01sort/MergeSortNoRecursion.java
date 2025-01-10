package algorithms.l01sort;

public class MergeSortNoRecursion {
  void sort(int[] arr) {
    int[] temp = new int[arr.length];
    int k;
    int size = 1, left_l, left_r, right_l, right_r;

    while (size < arr.length) {
      left_l = 0;
      k = 0;
      while (left_l + size < arr.length) {
        left_r = left_l + size - 1;
        right_l = left_r + 1;
        right_r = right_l + size - 1;
        if (right_r >= arr.length) {
          right_r = arr.length - 1;
        }
        int i = left_l;
        int j = right_l;
        while (i <= left_r && j <= right_r) {
          if (arr[i] <= arr[j]) temp[k++] = arr[i++];
                           else temp[k++] = arr[j++];
        }
        while (i <= left_r)  temp[k++] = arr[i++];
        while (j <= right_r) temp[k++] = arr[j++];
        left_l = right_r + 1;
      }
      System.arraycopy(temp, 0, arr, 0, left_l);
      size = size * 2;
    }
  }

  public static void main(String[] args) {
    // create a random array
    int[] data = Utils.create_random_data(10);
    // make a copy not to mutate original data
    int[] sorted = data.clone();
    // create the new instance of our class
    MergeSortNoRecursion app = new MergeSortNoRecursion();
    // running sort
    app.sort(sorted);

    // print
    System.out.println("Merge sort: complexity: O(n*log(N))");
    System.out.printf("Source array: %s\n", Utils.arrToString(data));
    System.out.printf("Sorted array: %s\n", Utils.arrToString(sorted));
  }
}
