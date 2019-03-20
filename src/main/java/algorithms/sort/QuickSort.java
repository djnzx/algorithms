package algorithms.sort;

import java.util.Scanner;

public class QuickSort {
  private static int counter_check = 0;
  private static int counter_permutation = 0;
  private static int counter = 0;

  private static Scanner in = new Scanner(System.in);

  // mutate the array
  private static void doSort(final int[] data, final int start, final int end) {
    if (start >= end) return;
    int left = start;
    int right = end;
    // by design - middle of the array
    int cur = left - (left - right) / 2;
    while (left < right) {
      // skip already sorted left side
      while (data[left] <= data[cur] && left < cur) {
        counter_check++;
        left++;
      }
      // skip already sorted right side
      while (data[cur] <= data[right] && cur < right) {
        counter_check++;
        right--;
      }
      // now, actually sort
      if (left < right) {
        int tmp = data[left];
        data[left] = data[right];
        data[right] = tmp;
        counter++;
        counter_permutation++;
        if (cur == left) { cur = right; }
        else if (cur == right) { cur = left; }
      }
    }
    doSort(data, start, cur);
    doSort(data, cur + 1, end);
  }

  private static int[] sort(int[] origin) {
    int[] process = origin.clone();
    // algorithm will be there
    doSort(process, 0, process.length-1);
    return process;
  }

  public static void main(String[] args) {
    int[] origin = Utils.create_random_data(100);
    Utils.printArray("Source array:", origin);
    int[] sorted = sort(origin);
    Utils.printArray("Sorted array:", sorted);
    System.out.printf("Checks count: %d\nPermutations count: %d\n", counter_check, counter_permutation);
    System.out.printf("Total: %d\n", counter_check * 2 + counter_permutation * 4);
    System.out.println(counter);
  }
}
