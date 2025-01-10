package algorithms.l13lee;

public class LPoint {
  public final int x;
  public final  int y;

  public LPoint(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static LPoint of(int x, int y) {
    return new LPoint(x, y);
  }

  LPoint move(int dx, int dy) {
    return LPoint.of(x + dx, y + dy);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LPoint that = (LPoint) o;
    return this.x == that.x && this.y == that.y;
  }

  @Override
  public int hashCode() {
    return x << 16 + y;
  }

  @Override
  public String toString() {
    return String.format("[%d:%d]", x, y);
  }

}
