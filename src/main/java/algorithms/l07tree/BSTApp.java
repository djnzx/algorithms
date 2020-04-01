package algorithms.l07tree;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BSTApp {

  static String spaces(int n) {
    byte[] bytes = new byte[n];
    Arrays.fill(bytes, (byte) 0x20);
    return new String(bytes);
  }

  static String indexes(int before, int from, int count, int width) {
    String indexes = "Indexes:";
    String format = "%"+String.format("%d", width)+"d";
    return indexes + spaces(before-indexes.length()) + IntStream.range(from, from+count)
        .mapToObj(n -> String.format(format, n)).collect(Collectors.joining());
  }

  public static void main(String[] args) {
    BST<Integer, Integer> tree = new BST<>();
    List<Integer> data = new Random().ints(10, 100).distinct().limit(20).boxed()
        .collect(Collectors.toList());

    System.out.println(indexes(22, 0, 20, 4));
    System.out.printf("Random data to insert: %s\n", data);

//    int index = (int) (Math.random()*data.size());
//    int value = data.get(index);

//    System.out.printf("%s^^\n", spaces(23+4*(index)+1));
//    System.out.printf("Index %d, Value %d chosen to remove\n", index, value);

    data.forEach(n -> tree.put(n, n));
    System.out.printf("Tree before removal: %s\n", tree.keys());

//    tree.remove(value);
//    System.out.printf("Tree after  removal: %s\n", tree.keys());
    System.out.println(tree.min().map(n -> String.format("Tree min key: %s", n)).orElse("Empty tree. No min"));
    System.out.println(tree.max().map(n -> String.format("Tree max key: %s", n)).orElse("Empty tree. No max"));
    System.out.printf("Tree height: %d\n", tree.height());
    System.out.printf("Tree width : %d\n", tree.width());
    System.out.printf("Tree keys (depth):   %s\n", tree.keys_traverse_depth());
    System.out.printf("Tree keys (breadth): %s\n", tree.keys_traverse_breadth());
    System.out.printf("Tree keys (breadth): %s\n", tree.keys_traverse_breadth_it());
  }
}
