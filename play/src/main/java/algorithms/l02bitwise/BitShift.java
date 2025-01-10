package algorithms.l02bitwise;

import algorithms.l02bitwise.conversion2.DecToBin;

public class BitShift {
  private DecToBin db = new DecToBin();

  String formatted(int val) {
    return String.format("decimal: %11d converted to binary: %s\n", val, db.decToBin(val));
  }

  void pf(int val) {
    System.out.print(formatted(val));
  }

  public static void main(String[] args) {
    BitShift app = new BitShift();
    byte x = 60;
    /**
     * there is no difference between '>>' and '>>>'
     * with positive numbers
     */
    app.pf(x);           // 60
    app.pf(x >> 2);  // 15
    app.pf(x >>> 2); // 15
    app.pf(x << 2);  // 240
    /**
     * but there is a BIG difference between '>>' and '>>>'
     * when it comes to negative numbers
     * >> - does care the sign
     * >>> - doesn't
     */
    int y = -60;
    app.pf(y);            // -60
    app.pf(y <<  8);  // -60*2^8=-15360
    app.pf(y >>  6);  // -1         ... we preserve the sign and shift
    app.pf(y >>> 6);  // 67108863   ... we don't care about sign, just move the bits
  }
}
