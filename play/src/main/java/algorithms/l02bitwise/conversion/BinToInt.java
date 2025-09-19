package algorithms.l02bitwise.conversion;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinToInt {

  private void check(String s) {
    if (s == null || s.length() != 8) throw new IllegalArgumentException("hasn't covered yet");
  }

  public int pow(int a, int b) {
    return IntStream.rangeClosed(1, b).reduce(1, (x, y) -> x * a);
  }

  public int toInt2(String s) {
    check(s);

    int outcome = 0;
    for (int i = 0; i < 8; i++) {
      int bit = s.charAt(i) - '0';
      outcome += bit * pow(2, 7 - i);
    }
    return outcome;
  }

  public int toIntStream(String s) {
    check(s);

    return IntStream.rangeClosed(0, 7)
        .map(i -> {
          // s.charAt(i) => 48 / 49
          int bit = s.charAt(i) - '0'; // 0 / 1
          return bit * pow(2, 7 - i);
        })
        .sum();
  }

  public int toInt3(String s) {
    check(s);

    return IntStream.rangeClosed(0, 7)
        .map(i -> (s.charAt(i) - '0') * pow(2, 7 - i))
        .sum();
  }

  public int toInt(String s) {
    check(s);

    return IntStream.rangeClosed(0, 7)
        .map(i -> (s.charAt(i) - '0') * (1 << (7 - i)))
        .sum();
  }

}

class BinToIntSpec {
  private final BinToInt bi = new BinToInt();

  @Test
  public void test_pow1() {
    assertEquals(8, bi.pow(2,3));
  }

  @Test
  public void test_pow2() {
    assertEquals(1, bi.pow(10,0));
  }

  @Test
  public void test1() {
    assertEquals(5, bi.toInt("00000101"));
  }

  @Test
  public void test2() {
    assertEquals(64+32+2+1, bi.toInt("01100011"));
  }

}
