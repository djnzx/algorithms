package algorithms.l02bitwise.conversion;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntToBin {

  public String toBin2(int value) {
    String outcome = "";
    for (int i = 7; i >= 0; i--) {
      int bit = (value >> i) & 0b00000001;
      outcome = outcome + bit;
    }
    return outcome;
  }

  public String toBin3(int value) {
    StringBuilder sb = new StringBuilder();
    for (int i = 7; i >= 0; i--) {
      int bit = (value >> i) & 0b00000001;
      sb.append(bit);
    }
    return sb.toString();
  }

  public String toBin(int value) {
    return IntStream.rangeClosed(0, 7)     // 0..7
      .map(x -> 7 - x)                     // 7..0
      .map(i -> (value >> i) & 0b00000001) // 0 or 1
      .mapToObj(Integer::toString)
      .collect(Collectors.joining());
  }

}

class IntToBinTest {

  private final IntToBin ib = new IntToBin();

  @Test
  public void test1() {
    assertEquals("00000000", ib.toBin(0));
  }

  @Test
  public void test2() {
    assertEquals("00000101", ib.toBin(5));
  }
}
