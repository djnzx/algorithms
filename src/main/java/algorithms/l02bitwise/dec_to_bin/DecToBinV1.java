package algorithms.l02bitwise.dec_to_bin;

import java.util.Scanner;

/**
 * http://www.asciitable.com/index/asciifull.gif
 * https://cdn.cs50.net/2016/x/psets/0/pset0/bulbs.html
 * http://sticksandstones.kstrom.com/appen.html
 */
public class DecToBinV1 {
  public static void main(String[] args) {
    int value = new Scanner(System.in).nextInt(); // 12
    StringBuilder binary = new StringBuilder();
    while (value > 0) {
      int rem = value % 2;
      binary.append(rem);
      value = value / 2;
    }
    System.out.println(binary.reverse().toString()); // 00001100
  }
}
