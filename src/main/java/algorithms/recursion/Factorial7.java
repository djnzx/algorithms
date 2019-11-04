package algorithms.recursion;

public class Factorial7 {
  public static void main(String[] args) {
    Factorial7 app = new Factorial7();
    int f5 = app.fact(5);
    System.out.println(f5);
  }

  private int fact(int val) {
    if (val == 1) return 1;
    int fn = fact(val - 1) * val;
    return fn;
  }
}
