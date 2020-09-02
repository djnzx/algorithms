package algorithms.l01sort;

import java.util.stream.IntStream;

/**
 * Effective Shuffling in O(N)
 */
public class Shuffle {

  private void swap(int[] a, int i, int j) {
    int t = a[i];
    a[i] = a[j];
    a[j] = t;
  }

  public int randomTo(int n) {
    return (int) (Math.random() * (n + 1));
  }

  public void shuffle(int[] a) {
    IntStream.range(0, a.length).forEach(n -> swap(a, n, randomTo(n)));
  }

  public static void main(String[] args) {
    int[] data = IntStream.rangeClosed(1, 25).toArray();
    int[] shuffled = data.clone();
    Shuffle app = new Shuffle();
    // running sort
    app.shuffle(shuffled);

    System.out.printf("Original array: %s\n", Utils.arrToString(data));
    System.out.printf("Shuffled  array: %s\n", Utils.arrToString(shuffled));
  }
}
