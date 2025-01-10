package algorithms.l03recursion;

public class XpowYbasic {

  static int pow(int number, int to) {
    if (to == 1) return number;
    return number * pow(number, to - 1);
  }

  public static void main(String[] args) {
    int N = 5;
    int POW = 3;
    int result = pow(N, POW); // 125
    System.out.println(result);
  }
}
