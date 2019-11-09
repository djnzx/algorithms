package warmup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetRidOfVowelsTest {

  private GetRidOfVowels c;

  @BeforeEach
  public void a() {
    c = new GetRidOfVowels();
  }

  @Test
  void filter() {
    assertEquals("qwrt", c.filter("qwerty") );
  }
}
