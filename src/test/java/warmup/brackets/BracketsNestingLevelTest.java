package warmup.brackets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BracketsNestingLevelTest {

  private BracketsNestingLevel app;

  @BeforeEach
  void before() {
    this.app = new BracketsNestingLevel();
  }

  @Test
  void calc1_should_ok() {
    assertEquals(1,
        app.calc("()()()()()"));
  }

  @Test
  void calc2_should_ok() {
    assertEquals(2,
        app.calc("(()()()()())"));
  }

  @Test
  void calc3_should_ok() {
    assertEquals(3,
        app.calc("()((()()))()(())"));
  }

  @Test
  void calc4_should_throw() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> app.calc("z"));
  }

  @Test
  void calc5_should_throw() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> app.calc("(()"));
  }

  @Test
  void calc6_should_throw() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> app.calc(")"));
  }
}
