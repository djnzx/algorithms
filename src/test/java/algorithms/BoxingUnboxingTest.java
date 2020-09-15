package algorithms;

import core.BoxingUnboxing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxingUnboxingTest {

  private BoxingUnboxing bx;

  @BeforeEach
  void setup() {
    this.bx = new BoxingUnboxing();
  }

  @Test
  void boxing() {
    assertEquals(new Integer(5), bx.boxing(5));
  }

  @Test
  void unboxing() {
    assertEquals(5, bx.unboxing(new Integer(5)));
  }

  @Test
  void absurd() {
    assertEquals(true, false);
  }
}
