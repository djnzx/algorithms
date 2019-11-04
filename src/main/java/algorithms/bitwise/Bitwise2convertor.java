package algorithms.bitwise;

import java.util.Scanner;

public class Bitwise2convertor {
    public static void main(String[] args) {
        int val = new Scanner(System.in).nextInt();

        for (int index = 15; index >= 0; index--) {
            int val_shifted = val >> index;

            int MASK = 0b0000000000000001;
            int one_bit = val_shifted & MASK; // 0 or 1 only !

            System.out.print(one_bit);
        }

        System.out.println();
    }
}
