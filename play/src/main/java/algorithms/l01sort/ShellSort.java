package algorithms.l01sort;

/**
 * Move more than one elements in a single pass
 * h-sort
 */
public class ShellSort {

  private void swap(int[] a, int i, int j) {
    int t = a[i];
    a[i] = a[j];
    a[j] = t;
  }

  public void sort(int[] a) {
    int N = a.length;
    int h = 1;
    while (h < N/3) h = h * 3 + 1; // 3n+1 (1,4,13,40) calculate the max point to partial sort
    while (h >= 1) {
      for (int i = h; i < N; i++) {
        for (int j = i; j >= h && a[j] < a[j - h]; j -=h ) {
          swap(a, j, j - h);
        }
      }
      h /= 3;
    }
  }

  public static void main(String[] args) {
    int[] data = Utils.create_random_data(10);
    int[] sorted = data.clone();
    ShellSort app = new ShellSort();
    // running sort
    app.sort(sorted);

    System.out.println("ShellSort: complexity: O( N^(3/2) )");
    System.out.printf("Source array: %s\n", Utils.arrToString(data));
    System.out.printf("Sorted array: %s\n", Utils.arrToString(sorted));
  }
}
