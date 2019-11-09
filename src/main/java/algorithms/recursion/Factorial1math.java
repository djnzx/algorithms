package algorithms.recursion;

public class Factorial1math {
  // basic recursion
  public static int factorial(int n) {
    if (n < 0) throw new IllegalArgumentException("number less than zero given");
    if (n <= 1) return 1;
    int value = n * factorial(n - 1);
    return value;
  }

  public static void main(String[] args) {
    int N = 5;
    int fact = factorial(N);
    System.out.println(fact);
  }
}
