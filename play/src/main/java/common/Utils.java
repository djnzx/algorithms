package common;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

  public static String spaces(int n) {
    byte[] bytes = new byte[n];
    Arrays.fill(bytes, (byte) 0x20);
    return new String(bytes);
  }

  public static String indexes(int before, int from, int count, int width) {
    String indexes = "Indexes:";
    String format = "%"+String.format("%d", width)+"d";
    return indexes + spaces(before-indexes.length()) + IntStream.range(from, from+count)
        .mapToObj(n -> String.format(format, n)).collect(Collectors.joining());
  }

  public static String centered(String content, int width) {
    int occupied = content.length();
    int left_size = (width - occupied) >> 1;
    int right_size = width - left_size - occupied;
    return String.format("%s%s%s", spaces(left_size), content, spaces(right_size));
  }

  public static int widthByLevelDepth(int level, int depth, int SIZE) {
    return SIZE << (depth - level);
  }

}
