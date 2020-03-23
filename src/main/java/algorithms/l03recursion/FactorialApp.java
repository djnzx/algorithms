package algorithms.l03recursion;

public class FactorialApp {

  // tail recursive implementation
  static int fact_tr(int number, int acc) {
    if (number == 1) return acc;
    return fact_tr(number - 1, number * acc);
  }

  // tail recursive runner
  static int fact_tr(int number) {
    return fact_tr(number, 1);
  }

  // head recursive implementation
  static int fact_hr(int number) {
    if (number == 1) return 1;
    int f1 = fact_hr(number - 1);
    return number * f1;
  }

  // iterative approach
  public static int fact_iter(int number) {
    if (number < 0) throw new IllegalArgumentException("number less than zero given");
    int result = 1;
    for (int next = 2; next <= number; next++) {
      result = result * next;
    }
    return result;
  }

  public static void main(String[] args) {
    int N = 5;
    System.out.printf("Iterative     :%d\n", fact_iter(N)); // 60
    System.out.printf("Head recursion:%d\n", fact_hr(N));
    System.out.printf("Tail recursion:%d\n", fact_tr(N));
  }
}
