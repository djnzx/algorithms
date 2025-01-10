package algorithms.l01sort;

import java.util.Arrays;

/**
 * https://www.geeksforgeeks.org/radix-sort/
 * in-place sort
 */
public class RadixSort {

  static void countSort(int[] data, int n, int exp) {
    int[] output = new int[n]; // output array
    int[] count = new int[10]; // in Java it's by default filled y 0

    // Store count of occurrences in count[]
    for (int i = 0; i < n; i++) {
      count[(data[i] / exp) % 10]++;
    }

    // Change count[i] so that count[i] now contains
    // actual position of this digit in output[]
    for (int i = 1; i < 10; i++) {
      count[i] += count[i - 1];
    }

    // Build the output array
    for (int i = n - 1; i >= 0; i--) {
      output[count[ (data[i]/exp)%10 ] - 1] = data[i];
      count[ (data[i]/exp)%10 ]--;
    }

    if (n >= 0) System.arraycopy(output, 0, data, 0, n);
  }

  static void radixsort(int[] data, int n) {
    // we need the max number to know number of digits
    int max = Arrays.stream(data).max().orElseThrow(RuntimeException::new);

    for (int exp = 1; max / exp > 0; exp *= 10) // 1, 10, 100, 1000, ...
      countSort(data, n, exp);
  }

  public static void main (String[] args) {
    int[] data = Utils.create_random_data(20);
    System.out.println(Utils.arrToString(data));
    int n = data.length;
    radixsort(data, n);
    System.out.println(Utils.arrToString(data));
  }
}
