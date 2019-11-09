package warmup;

class IsPrimeDynamic {
  private final int origin;
  private final int[] primes;
  private final int length;

  IsPrimeDynamic(int num, int[] primes, int len) {
    origin = num;
    this.primes = primes;
    length = len;
  }

  private boolean restIsZero(int orig, int idx) {
    return (orig % primes[idx]) == 0;
  }

  private boolean toBeAnalyzed(int idx, int max) {
    return primes[idx]<=max;
  }

  boolean is() {
    if (origin==1) return false;
    if (origin==2) return true;
    int maxToAnalyze = (int) Math.sqrt(origin);
    for (int idx=0; idx<length; idx++) {
      if (toBeAnalyzed(idx, maxToAnalyze) && restIsZero(origin, idx)) {
        return false;
      }
    }
    return true;
  }
}
