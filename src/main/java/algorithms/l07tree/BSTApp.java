package algorithms.l07tree;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BSTApp {
  public static void main(String[] args) {
    BST<Integer, Integer> tree = new BST<>();
    List<Integer> data = new Random().ints(10, 100).limit(20).boxed().distinct()
        .collect(Collectors.toList());
    data.forEach(n -> tree.put(n, n));
    System.out.println(tree.keys());
    tree.remove(data.get((int) (Math.random()*data.size())));
    System.out.println(tree.keys());
  }
}
