package algorithms.l01sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static common.RX.NE;
import static common.RX.NI;

public class GrahamConvexHull {

  static class Point {
    final double x, y;

    Point(double x, double y) {
      this.x = x;
      this.y = y;
    }

    static Point random() {
      return new Point(
          (int) (Math.random() * 10),
          (int) (Math.random() * 10)
      );
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Point that = (Point) o;

      return that.x == this.x
          && that.y == this.y;
    }

    @Override
    public int hashCode() {
      int result;
      long temp;
      temp = Double.doubleToLongBits(x);
      result = (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(y);
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      return result;
    }

    public Comparator<Point> byAngle() {
      return new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
          return 0;
        }
      };
    }

    public static int ccw(Point a, Point b, Point c) {
      double area = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
      if (area < 0) return -1; // cw
      if (area > 0) return +1; // ccw
      return 0;
    }

    public final static Comparator<Point> byY = Comparator.comparingDouble(p -> p.y);
    public final static Comparator<Point> byAngle = (o1, o2) -> { throw NI; };
  }

  // min Y
  private Point findStart(List<Point> points) {
    return points.stream().min(Point.byY).orElseThrow(() -> NE);
  }

  private double angle(Point p, Point p0) {
    return 0;
  }

  public List<Point> findConvex(List<Point> points) {
    points.sort(Point.byY);
    points.sort(points.get(0).byAngle());
    Stack<Point> outcome = new Stack<>();
    outcome.push(points.get(0));
    outcome.push(points.get(1));
    // TODO: walk through the sorted, detect direction, skip wrong direction
    points.stream().skip(2)
        .forEach(p -> { throw NI; });
    return outcome;
  }

  public static void main(String[] args) {
    List<Point> points = Stream.generate(Point::random)
        .limit(10)
        .collect(Collectors.toList());
    GrahamConvexHull app = new GrahamConvexHull();
    List<Point> convex = app.findConvex(points);

    System.out.printf("Source: %s\n", points);
    System.out.printf("Convex: %s\n", convex);
  }
}
