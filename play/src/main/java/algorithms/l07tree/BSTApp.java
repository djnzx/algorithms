package algorithms.l07tree;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static common.Utils.indexes;

public class BSTApp {

  public static void main(String[] args) {
    BST<Integer, Integer> tree = new BST<>();

    List<Integer> data =
//        new Random().ints(10, 101).distinct().limit(16).boxed().collect(Collectors.toList());
        Stream.of(30,20,40,35,50,60,70,45,55,44,46,31,38,37,39,32,15,17,18,16,10,11,5,25,23,24,22,28,27,26,29)
            .filter(x -> x < 26)
            .collect(Collectors.toList());
    data.addAll(Arrays.asList(30,40,28,29));

    System.out.println(indexes(15, 0, data.size(), 4));
    System.out.printf("Data to insert: %s\n", data);

    data.forEach(n -> tree.put(n, n));

    System.out.println(tree.min().map(n -> String.format("Tree min key: %s", n)).orElse("Empty tree. No min"));
    System.out.println(tree.max().map(n -> String.format("Tree max key: %s", n)).orElse("Empty tree. No max"));
    System.out.printf("Tree height: %d\n", tree.height());
    System.out.printf("Tree width : %d\n", tree.width());
    System.out.printf("Tree keys (depth):   %s\n", tree.keys_traverse_depth());
    System.out.printf("Tree keys (breadth): %s\n", tree.keys_traverse_breadth());

    System.out.printf("Tree before removal: %s\n", tree.keys());
    System.out.println(tree.show());
    System.out.println();
    tree.remove(25);
    System.out.printf("Tree after removal: %s\n", tree.keys());
    System.out.println(tree.show());
  }
}
