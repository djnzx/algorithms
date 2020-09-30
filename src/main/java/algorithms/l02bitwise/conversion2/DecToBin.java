package algorithms.l02bitwise.conversion2;

import java.util.Scanner;

public class DecToBin {

  public String decToBin(int value) {
    // 8 - byte, 16 - short, 32 - int
    return decToBin(value, 32);
  }

  public String decToBin(int value, int SIZE) {
    StringBuilder sb = new StringBuilder();
    final int MASK = 0b0000000000000001;

    for (int index = SIZE-1; index >= 0; index--) {
      int value_shifted = value >> index;
      int one_bit = value_shifted & MASK; // 0 or 1 only !
      sb.append(one_bit);
      if ((index % 8 == 0)&&(index < SIZE)) sb.append(" ");
    }

    return sb.toString().trim();
  }

  public static void main(String[] args) {
    DecToBin app = new DecToBin();
    System.out.print("Enter decimal value to convert: ");
    int decimal = new Scanner(System.in).nextInt();
    String binary = app.decToBin(decimal);
    System.out.printf("Decimal value %d converted to binary: %s\n", decimal, binary);
  }

}
