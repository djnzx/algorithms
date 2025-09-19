package warmup.prime;

import warmup.prime.versions.PrimesDynamic;

import java.util.Arrays;

public class PrimesDynamicApp {
  private final static int MIN = 10000;
  private final static int MAX = 99999;

  public static void main(String[] args) {
    int[] primes = new PrimesDynamic(MIN, MAX).data();
    System.out.printf("Primes length:%d\n", primes.length);
    System.out.printf("Primes:%s\n", Arrays.toString(primes));
  }
}
