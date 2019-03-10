import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class QuickSort {
  private final static int SIZE = 20;

  private static int[] data(int amount) {
    Random r = new Random();
    return Stream.generate(() -> r.nextInt(100)).distinct().limit(amount).mapToInt(i -> i).toArray();
  }

  public static void main(String[] args) {
    int[] origin = data(SIZE);
    System.out.println(Arrays.toString(origin));
  }
}
