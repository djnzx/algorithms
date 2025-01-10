package core;

public class BoxingUnboxing {

  public Integer boxing(int val) {
    return val;
  }

  public int unboxing(Integer val) {
    return val;
  }

  public static void main(String[] args) {

    // primitive
    int i51 = 51;

    // class wrapper
    Integer i52 = new Integer(5);

    // auto-boxing
    Integer i53 = 53;

    // unboxing
    int i54 = i52;
  }
}
