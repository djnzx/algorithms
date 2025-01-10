package warmup.amazon;

public class GCD {
  public int gcd_brute_force(int num, int[] arr) {
    int min = arr[0];
    for (int i = 1; i < num; i++) {
      min = Math.min(min, arr[i]);
    }

    int gcd_max = 1;
    int gcd = min;
    while (gcd > 1) {
      boolean ok = true;
      for (int i=0; i < num; i++) {
        if (arr[i] % gcd != 0) {
          ok = false;
          break;
        }
      }
      if (ok) {
        gcd_max = gcd;
        break;
      }
      gcd--;
    }
    return gcd_max;
  }
}
