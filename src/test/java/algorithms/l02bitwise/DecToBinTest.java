package algorithms.l02bitwise;

import algorithms.l02bitwise.conversion2.DecToBin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecToBinTest {

  private DecToBin c;

  @BeforeEach
  void before() {
    this.c = new DecToBin();
  }

  @Test
  void decToBin1() {
    assertEquals("00000000", c.decToBin(0, 8));
  }

  @Test
  void decToBin2() {
    assertEquals("00001111", c.decToBin(15,8));
  }

  @Test
  void decToBin3() {
    assertEquals("11111111", c.decToBin(255,8));
  }

  @Test
  void decToBin4() {
    assertEquals("01000010", c.decToBin(66,8));
  }

}
