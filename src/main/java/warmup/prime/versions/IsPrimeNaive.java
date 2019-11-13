package warmup.prime.versions;

public class IsPrimeNaive {
  boolean check(int origin) {
    if (origin == 2) return true;
    for (int i = 2; i < origin; i++) {
      if (origin % i == 0) return false;
    }
    return true;
  }
}
