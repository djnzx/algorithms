import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class QuickSort {
  private final static int SIZE = 20;

  private static int[] data(int amount) {
    Random r = new Random();
    return Stream.generate(() -> r.nextInt(100)).distinct().limit(amount).mapToInt(i -> i).toArray();
  }

  private static int[] sort(int[] origin) {
    int[] sorted = origin.clone();
    // algorithm will be there

    //
    return sorted;
  }

  public static void main(String[] args) {
    int[] origin = data(SIZE);
    System.out.printf("Source array: %s\n", Arrays.toString(origin));
    int[] sorted = sort(origin);
    System.out.printf("Sorted array: %s\n", Arrays.toString(sorted));
  }
}
