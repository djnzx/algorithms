package algorithms.l02bitwise;

public class IntegerOverflow {

  int strictAdd_v1(int a, int b) {
    int r = a + b;
    if ((a < 0 && b < 0 && r > 0) || (a > 0 && b > 0 && r < 0))
      throw new ArithmeticException("Integer Overflow #1");
    return r;
  }

  int strictAdd_v2(int a, int b) {
    if ((a | b) < Integer.MAX_VALUE)
      throw new ArithmeticException("Integer Overflow #2");
    return a + b;
  }

  int strictAdd_v3(int a, int b) {
    int r = a + b;
    if (((a ^ r) & (b ^ r)) < 0) {
      throw new ArithmeticException("integer overflow #3");
    }
    return r;
  }

}
