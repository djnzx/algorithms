package algorithms.l12binaryheap;

import java.util.stream.Stream;

/**
 * In the binary heap -
 * children must be smaller than their parents
 *
 * the root is the max value
 *
 * we represent tree in an array:
 * parent of K is k/2
 * children of K are 2k and 2k+1
 * (indices)
 *
 * actually, it's a PriorityQueue with:
 * - O(logN) insertion
 * - O(logN) deletion
 * - O(1) peek
 */
public class BinaryHeapApp {

  public static void main(String[] args) {
    BinaryHeap<String> bh = new BinaryHeap<>(30);

    System.out.println("adding");
    Stream.generate(() -> String.valueOf((char)(Math.random()*('Z'-'A')+'A')))
        .distinct().limit(20)
        .forEach(x -> {
          bh.insert(x);
          System.out.println(bh);
        });

    System.out.println("removing");
    while (!bh.isEmpty()) {
      bh.delMax();
      System.out.println(bh);
    }
  }

}
