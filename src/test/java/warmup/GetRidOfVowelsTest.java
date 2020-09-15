package warmup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetRidOfVowelsTest {

  private GetRidOfVowels c;

  @BeforeEach
  public void a() {
    c = new GetRidOfVowels();
  }

  @Test
  void filter() {
    assertEquals("qwrty", c.filter("qwerty") );
  }
}
