package algorithms.l03recursion;

import java.util.Arrays;
import java.util.Random;

public class ArraySumApp {

  // iterative approach
  static int sum_iterative (int[] data) {
    int sum = 0;
    for (int el: data) {
      sum += el;
    }
    return sum;
  }

  // head recursion, implementation
  static int sumr_head(int[] data, int index) {
    // termination condition
    if (index == data.length) return 0;
    // recursive call
    int acc = sumr_head(data, index + 1);
    // producing result
    return data[index] + acc;
  }

  // head recursion, runner
  static int sumr_head(int[] data) {
    return sumr_head(data, 0);
  }

  // tail recursion, implementation
  private static int sumr_tail(int[] data, int index, int acc) {
    if (index == data.length) return acc;
    int newAcc = acc + data[index];
    // tail-recursive. recursive call in the last line
    return sumr_tail(data, index + 1, newAcc);
  }

  // tail recursion, runner
  static int sumr_tail(int[] data) {
    return sumr_tail(data, 0, 0);
  }

  public static void main(String[] args) {
    // our random data
    int[] d = new Random().ints(1, 10).limit(10).toArray();
    System.out.printf("Source array: %s\n", Arrays.toString(d));
    System.out.printf("Sum, iterative          : %d\n", sum_iterative(d));
    System.out.printf("Sum, recursive, head rec: %d\n", sumr_head(d));
    System.out.printf("Sum, recursive, tail rec: %d\n", sumr_tail(d));
  }
}
