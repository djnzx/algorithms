package algorithms.recursion;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
    private static final Map<Integer, Integer> memo = new HashMap<>();

    private static boolean has(int number) {
        return memo.containsKey(number);
    }

    private static void put(int pos, int value) {
        memo.put(pos, value);
    }

    private static int get(int pos) {
        return memo.get(pos);
    }

    static int fibo2(int n) {
        if (n <= 2) return 1;
        return fibo2(n - 1 ) + fibo2(n - 2);
    }

    static int fibo(int n) {
        int nth;

        if (has(n)) {
            nth = get(n);
        } else if (n <= 2) {
            nth = 1;
        } else {
            nth = fibo(n - 1 ) + fibo(n - 2);
        }

        put(n, nth);
        return nth;
    }

    public static void main(String[] args) {
        int number = 45;
//        int fibo10th = fibo2(number);
        int fibo10th = fibo(number);
        System.out.println(fibo10th);
    }

}
