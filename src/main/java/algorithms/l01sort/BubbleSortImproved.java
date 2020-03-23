package algorithms.l01sort;

/**
 * Bubble sort improved implementation
 * complexity: O(n^2 / 2)
 */
public class BubbleSortImproved {

  public static void swap(int[] origin, int i, int j) {
    int tmp = origin[i];
    origin[i] = origin[j];
    origin[j] = tmp;
  }

  public static int[] sort(int[] origin) {
    // make a copy not to mutate original array
    int[] sorted = origin.clone();
    // complexity: O(n^2 / 2)
    for (int i = 0; i < sorted.length; i++) {
      for (int j = i + 1; j < sorted.length; j++) {
        if (sorted[i] < sorted[j]) {
          swap(sorted, i , j);
        }
      }
    }
    return sorted;
  }

  public static void main(String[] args) {
    int[] data = Utils.create_random_data(10);
    int[] sorted = sort(data);
    System.out.println("Bubble sort, improved version: complexity: O(n^2 / 2)");
    System.out.printf("Source array: %s\n", Utils.arrToString(data));
    System.out.printf("Sorted array: %s\n", Utils.arrToString(sorted));
  }

}
