package warmup.prime;

import java.util.Arrays;

public class PrimesApp {
  private final static int MIN = 10000;
  private final static int MAX = 99999;

  public static void main(String[] args) {
    int[] primes = new PrimesDynamic(MIN, MAX).data();
    System.out.printf("Primes length:%d\n", primes.length);
    System.out.printf("Primes:%s\n", Arrays.toString(primes));
  }
}
