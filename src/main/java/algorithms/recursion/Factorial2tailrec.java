package algorithms.recursion;

public class Factorial2tailrec {
  // tail recursion with accumulator
  public static int factorial(int n, int ac) {
    if (n <= 1) return ac;
    return factorial(n - 1, ac * n);
  }

  // runner, thanks to overload
  public static int factorial(int n) {
    if (n < 0) throw new IllegalArgumentException("number less than zero given");
    return factorial(n, 1);
  }

  public static void main(String[] args) {
    int N = 5;
    int fact = factorial(N);
    System.out.println(fact);
  }
}
