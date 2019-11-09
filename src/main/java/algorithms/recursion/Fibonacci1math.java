package algorithms.recursion;

public class Fibonacci1math {
  // recursive implementation
  private static int fibo(int number) {
    if (number == 1 || number == 2) return 1;
    int t1 = fibo(number - 1);
    int t2 = fibo(number - 2);
    return t1 + t2;
  }

  public static void main(String[] args) {
    int N = 10;
    int f10 = fibo(10);
    System.out.println(f10);
  }
}
