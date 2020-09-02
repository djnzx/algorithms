package algorithms.l04linkedlist;

import static common.RX.NI;

public class GCDStackSafe {
  /**
   * can easily lead to stack overflow
   */
  public int gcdx(int a, int b) {
    if (b == 0) return a;
    return gcdx(b, a % b);
  }

  /**
   * own version with manual stack
   * should be implemented
   */
  public int gcd(int a, int b) {
    if (b == 0) return a;
    throw NI;
  }

  public static void main(String[] args) {
    GCDStackSafe g = new GCDStackSafe();
    System.out.println(g.gcdx(24, 36));
    System.out.println(g.gcdx(36, 24));
  }

}
