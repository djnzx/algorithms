package algorithms.l03recursion;

public class FibonacciApp {

  // head recursive implementation
  static int fibo(int n) {
    // termination condition
    if (n == 1 || n == 2) return 1;
    // recursive call #1
    int f1 = fibo(n-1);
    // recursive call #2
    int f2 = fibo(n-2);
    // combining result
    int fn = f1 + f2;
    return fn;
  }

  public static void main(String[] args) {
    int N = 10;
    int f10 = fibo(N);
    System.out.println(f10);
  }
}
