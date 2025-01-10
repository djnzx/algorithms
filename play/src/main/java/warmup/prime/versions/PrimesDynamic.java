package warmup.prime.versions;

import java.util.Arrays;

public class PrimesDynamic {
  private final int max;
  private final int min;
  private int[] primes;
  private int process_size = 0;
  private int counter = 0;

  public PrimesDynamic(int mn, int mx) {
    max = mx;
    min = mn;
    // this is very rough estimation
    primes = new int[this.max/10];
  }

  boolean is_prime(int value) {
    if (value == 1) return false;
    if (value == 2) return true;
    int maxToAnalyze = (int) Math.sqrt(value);
    for (int idx = 0; idx < process_size; idx++) {
      if (primes[idx] <= maxToAnalyze) {
        counter++;
        if(value % primes[idx] == 0) return false;
      }
    }
    return true;
  }

  public int[] data() {
    for (int value = 1; value <= max; value++) {
      if (is_prime(value)) primes[process_size++] = value;
    }
    int index_of_min = 0;
    while (index_of_min < process_size && primes[index_of_min] < min) index_of_min++;
    System.out.println(counter);
    return Arrays.copyOfRange(primes, index_of_min, process_size);
  }

}
