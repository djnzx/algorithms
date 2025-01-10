package warmup.prime;

import java.util.ArrayList;

public class PrimesNaiveApp {
  private final static int MIN = 10000;
  private final static int MAX = 99999;
  private static long counter = 0;

  static boolean check(int origin) {
    if (origin == 2) return true;
    for (int i = 2; i <= Math.sqrt(origin); i++) {
      counter++;
      if (origin % i == 0) return false;
    }
    return true;
  }

  public static void main(String[] args) {
    ArrayList<Integer> primes_a = new ArrayList<>();
    for (int i = 10000; i < 100000; i++) {
      if (check(i)) primes_a.add(i);
    }
    System.out.printf("Iteration count:%d\n", counter); // 2_628_167 vs 744_435
    System.out.printf("Count of numbers generated:%d\n", primes_a.size());
  }
}
