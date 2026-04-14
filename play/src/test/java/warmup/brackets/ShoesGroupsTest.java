package warmup.brackets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import warmup.shoes.ShoesGroups;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoesGroupsTest {

  private ShoesGroups c;

  @BeforeEach
  void setUp() {
    this.c = new ShoesGroups();
  }

  @Test
  void calc1() {
    assertEquals(4, c.calc("RLRRLLRLRRLL"));
  }

  @Test
  void calc2() {
    assertEquals(4, c.calc("RLLLRRRLLR"));
  }

  @Test
  void calc3() {
    assertEquals(1, c.calc("LLRLRLRLRLRLRR"));
  }

  @Test
  void calc4() {
    assertEquals(2,c.calc("LRLR"));
  }

  @Test
  void calc5() {
    assertEquals(3,c.calc("LRRRLRLLLR"));
  }
}
