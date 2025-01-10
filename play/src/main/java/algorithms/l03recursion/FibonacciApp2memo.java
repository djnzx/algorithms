package algorithms.l03recursion;

import java.util.HashMap;
import java.util.Map;

public class FibonacciApp2memo {
  private static final Map<Integer, Integer> memo = new HashMap<>();

  private static boolean has(int number) {
    return memo.containsKey(number);
  }

  private static void put(int pos, int value) {
    memo.put(pos, value);
  }

  private static int get(int pos) {
    return memo.get(pos);
  }

  static int fibo_basic(int n) {
    if (n == 1 || n == 2) return 1;
    return fibo_basic(n - 1 ) + fibo_basic(n - 2);
  }

  static int fibo(int n) {
    int nth;

    if (n == 1 || n == 2) {
      nth = 1;
    } else if (has(n)) {
      nth = get(n);
    } else {
      nth = fibo(n - 1 ) + fibo(n - 2);
    }
    // put element into memo
    put(n, nth);
    return nth;
  }

  public static void main(String[] args) {
    int N = 55;
    int fibo45th = fibo(N);
    System.out.println(fibo45th);
  }
}
