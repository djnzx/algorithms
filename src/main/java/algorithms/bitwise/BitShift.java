package algorithms.bitwise;

public class BitShift {
  public static void main(String[] args) {
    byte x = 12;
    /**
     * there is no difference between '>>' and '>>>'
     * with positive numbers
     */
    System.out.println(x >> 1); // 24
    System.out.println(x >>> 1); // 6
    System.out.println(x << 1); // 6
    byte y = -12;
    /**
     * but there is a BIG difference between '>>' and '>>>'
     * with it comes to negative numbers
     * >> - does care the sign
     * >>> - doesn't
     */
    System.out.println(y << 1); // -24
    System.out.println(y >> 1); // -6 .. we preserve the sign and shift
    System.out.println((y >>> 1)&0b1111111); // 250
    /**
     *  12 === 00001100 = 8 + 4
     * -12 === 11110011 = invert + 1
     * 122 === 01111010
     */
  }
}
