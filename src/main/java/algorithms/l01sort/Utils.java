package algorithms.l01sort;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Utils {
  public static int[] create_random_data(int amount) {
    Random r = new Random();
    return Stream.generate(() -> r.nextInt(200))
        .distinct()
        .limit(amount)
//        .sorted((o1, o2) -> o2-o1)
        .mapToInt(i -> i)
        .toArray();
  }

  public static String arrToString(int[] data) {
    return arrToString(data, 0, data.length - 1);
  }

  public static String arrToString(int[] data, int l, int r) {
    int len = r - l + 1;
    if (data.length == len) return Arrays.toString(data);
    int[] slice = new int[len];
    System.arraycopy(data, l, slice, 0, len);
    return Arrays.toString(slice);
  }
}
