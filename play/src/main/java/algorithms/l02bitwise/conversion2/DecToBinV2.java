package algorithms.l02bitwise.conversion2;

import java.util.Scanner;

public class DecToBinV2 {
  public static void main(String[] args) {
    int value = new Scanner(System.in).nextInt(); // 12
    StringBuilder binary = new StringBuilder();
    while (value > 0) {
      int rem = value & 0b1;
      binary.append(rem);
      value = value >> 1;
    }
    System.out.println(binary.reverse().toString()); // 00001100
  }
}
