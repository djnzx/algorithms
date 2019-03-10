package algorithms.sort;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Utils {
  public static int[] create_random_data(int amount) {
    Random r = new Random();
    return Stream.generate(() -> r.nextInt(100))
        .distinct()
        .limit(amount)
//        .sorted((o1, o2) -> o2-o1)
        .mapToInt(i -> i)
        .toArray();
  }
  public static void printArray(String msg, int[] data) {
    System.out.printf("%s %s\n", msg, Arrays.toString(data));
  }
}
