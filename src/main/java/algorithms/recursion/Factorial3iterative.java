package algorithms.recursion;

public class Factorial3iterative {

  // iterative approach
  public static int factorial(int number) {
    int result = 1;
    for (int next = 2; next <= number; next++) {
      result = result * next;
    }
    return result;
  }

  public static void main(String[] args) {
    int N = 5;
    int fact = factorial(N);
    System.out.println(fact);
  }
}
