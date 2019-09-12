package algorithms.bitwise;

import java.util.Scanner;

public class Bitwise2convertor {
    public static void main(String[] args) {
        int val = new Scanner(System.in).nextInt();

        for (int index = 15; index >= 0; index--) {
            int shifted = val >> index;

            int MASK = 0b0000000000000001;
            int bit = shifted & MASK;

            System.out.print(bit);
        }

        System.out.println();
    }
}
