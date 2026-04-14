package algorithms.l02bitwise.usecases;

public class Order {
  private final static byte SMOKE = 0b00000001;
  private final static byte PET   = 0b00000010;
  private final static byte AC    = 0b00000100;
  private final static byte TOUR  = 0b00001000;

  byte prop;

  public void setTouring() {
    prop = (byte)(prop | 0b00001000);
  }

  public void setTouring2() {
    prop = (byte)(prop | TOUR);
  }

  public boolean isSmoke() {
    return (prop & SMOKE) == SMOKE;
  }

  public boolean isPet() {
    return (prop & 0b00000010) == 0b00000010;
  }

  public boolean isPet3() {
    return ((prop >> 1) & 0b00000001) == 0b00000001;
  }

  public boolean isAC() {
    return ((prop >> 2) & 0b00000001) == 0b00000001;
  }

  public boolean isPet2() {
    return (prop & PET) == PET;
  }

  // lazy, up to first TRUE
  public boolean qwe1(boolean a, boolean b, boolean c) {
    return a || b || c;
  }

  // lazy, up to first FALSE
  public boolean qwe1a(boolean a, boolean b, boolean c) {
    return a && b && c;
  }

  public int qwe1(int a, int b, int c) {
    return a | b | c;
  }

  public int qwe1a(int a, int b, int c) {
    return a & b & c;
  }



}
