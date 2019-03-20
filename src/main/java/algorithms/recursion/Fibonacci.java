package algorithms.recursion;

public class Fibonacci {

    static int fibo(int n) {
        if (n <= 2) return 1;
        return fibo(n - 1 ) + fibo (n - 2);
    }

    public static void main(String[] args) {
        int number = 100;
        int fibo10th = fibo(number);
        System.out.println(fibo10th);
    }
}
