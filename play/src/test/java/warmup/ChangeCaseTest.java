package warmup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeCaseTest {

  private ChangeCase c;

  @BeforeEach
  void setUp() {
    this.c = new ChangeCase();
  }

  @Test
  void invert1() {
    assertEquals("", c.invert(""));
  }

  @Test
  void invert2() {
    assertEquals("abc", c.invert("ABC"));
  }

  @Test
  void invert3() {
    assertEquals("DEF", c.invert("def"));
  }

  @Test
  void invert4() {
    assertEquals("xYz", c.invert("XyZ"));
  }
}
