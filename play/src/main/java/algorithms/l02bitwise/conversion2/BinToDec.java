package algorithms.l02bitwise.conversion2;

import java.util.Scanner;

public class BinToDec {

  public int binToDec(String origin) {
    int result = 0;
    for (int i = 0; i < origin.length(); i++) {
      int charAt = origin.charAt(i);                  // '1' or '0'
      if (charAt < '0' || charAt > '1') throw new IllegalArgumentException(String.format("wrong char in the original data: '%c'", (char)charAt));
      int digitAt = Character.digit(charAt, 2); // convert '0' -> 0, '1' -> 1
      int powerTo = origin.length() - i - 1;
      // way 1. math approach
//      int part = (int) (digitAt * Math.pow(2 , powerTo));
      // way 2. bitwise representation
      int part = digitAt << powerTo;
      result += part;
    }
    return result;
  }

  public static void main(String[] args) {
    BinToDec app = new BinToDec();
    System.out.print("Enter binary value to convert: ");
    String binary = new Scanner(System.in).next();
    int decimal = app.binToDec(binary);
    System.out.printf("Binary value %s converted to decimal: %d\n", binary, decimal);
  }
}
