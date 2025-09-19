package algorithms.l02bitwise;

import algorithms.l02bitwise.conversion2.BinToDec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinToDecTest {

  private BinToDec c;

  @BeforeEach
  void setUp() {
    this.c = new BinToDec();
  }

  @Test
  void binToDec1() {
    assertEquals(0, c.binToDec(""));
  }

  @Test
  void binToDec2() {
    assertEquals(0, c.binToDec("0"));
  }

  @Test
  void binToDec3() {
    assertEquals(1, c.binToDec("1"));
  }

  @Test
  void binToDec4() {
    assertEquals(10, c.binToDec("1010"));
  }

  @Test
  void binToDec5() {
    assertEquals(255, c.binToDec("11111111"));
  }

  @Test
  void binToDec7() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> c.binToDec("1a"));
  }
}
