package algorithms.bitwise;

import java.util.Scanner;

public class Bitwise3convertor {
    public static void main(String[] args) {
        int one = '1';  // 49
        int zero = '0'; // 48

        String str = new Scanner(System.in).next();
        // 01001
        int result = 0;
        for (int i = 0; i < str.length(); i++) {
            int charAt = str.charAt(i); // '1' or '0'
            int digitAt = Character.digit(charAt, 2); // convert '0' -> 0, '1' -> 1
            int part = (int) (digitAt * Math.pow(2 , str.length() - i - 1));

            result += part;
        }
        System.out.println(result);
    }
}
