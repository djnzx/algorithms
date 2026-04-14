package algorithms.l03recursion;

public class XpowYtailrec {

  static int pow(int number, int to, int acc) {
    if (to == 0) return acc;
    return pow(number, to - 1, acc * number);
  }

  static int pow(int number, int to) {
    return pow(number, to, 1);
  }

  public static void main(String[] args) {
    int N = 5;
    int POW = 3;
    int result = pow(N, POW); // 125
    System.out.println(result);
  }
}
