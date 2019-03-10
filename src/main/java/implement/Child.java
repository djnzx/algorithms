package implement;

public class Child implements Human, Mother, Father {
  @Override
  public String hello() {
    return "it's me!";
  }
}
