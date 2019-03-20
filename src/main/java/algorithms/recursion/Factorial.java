package algorithms.recursion;

public class Factorial {

    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public static void main(String[] args) {
        int N = 5;
        int fact = factorial(N);
        System.out.println(fact);
    }

}
