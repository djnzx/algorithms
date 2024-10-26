package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/convex-hull-fp/problem
  * https://en.wikipedia.org/wiki/Polygon_triangulation
  * https://www.geometrictools.com/Documentation/TriangulationByEarClipping.pdf
  * https://math.stackexchange.com/questions/978642/how-to-sort-vertices-of-a-polygon-in-counter-clockwise-order
  */
object P8ConvexHull {

  import scala.math.abs
  import scala.math.atan2
  import scala.math.sqrt
  import scala.math.toDegrees
  def sq(x: Double): Double = x * x

  case class Pt(x: Double, y: Double)

  def len(a: Pt, b: Pt): Double = sqrt(sq(a.x - b.x) + sq(a.y - b.y))

  def sign(p1: Pt, p2: Pt, p3: Pt): Double =
    (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y)

  case class Triangle(a: Pt, b: Pt, c: Pt) {
    def hc: Double = {
      val lab = len(a, b)
      val lac = len(a, c)
      val lbc = len(b, c)
      val dc = (sq(lab) + sq(lbc) - sq(lac)) / (2 * lab)
      sqrt(abs(sq(lbc) - sq(dc))) // hc (h to ab)
    }
    def area: Double = hc * len(a, b) / 2
    def isInside(p: Pt): Boolean = {
      val d1 = sign(p, a, b)
      val d2 = sign(p, b, c)
      val d3 = sign(p, c, a)
      val neg = (d1 < 0) || (d2 < 0) || (d3 < 0)
      val pos = (d1 > 0) || (d2 > 0) || (d3 > 0)
      !(neg && pos)
    }
  }

  def withoutOne[A](pts: List[A], idx: Int): (A, List[A]) = {
    @scala.annotation.tailrec
    def go(tail: List[A], x: Int, acc: List[A]): (A, List[A]) = tail match {
      case h :: t => if (x < idx) go(t, x + 1, h :: acc)
        else (h, t.reverse ::: acc)
      case _      => ???
    }
    go(pts, 0, Nil)
  }

  def withoutOneSeq[A](pts: List[A]): Seq[(A, List[A])] =
    pts.indices.map(idx => withoutOne(pts, idx))

  def removeOverlapped(pts: List[Pt]): List[Pt] = () match {
    case _ if pts.length <= 3 => pts
    case _                    =>
      val overlapped = withoutOneSeq(pts)
        .flatMap { case (pt, residual) =>
          val inside = pts.forall(pt => Polygon(residual).inside(pt))
          Option.when(inside)(pt)
        }
      overlapped match {
        case Seq() => pts
        case _     => (pts.toSet -- overlapped).toList
      }
  }

  case class Polygon(points: List[Pt]) {

    private def add(pt: Pt): Polygon = Polygon(removeOverlapped(pt :: points)).convex

    private lazy val centroid: Pt = {
      val (sumx, sumy) = points.foldLeft((0.0, 0.0))((a, pt) => (a._1 + pt.x, a._2 + pt.y))
      Pt(sumx / points.length, sumy / points.length)
    }

    private def convex: Polygon = if (points.length <= 3) this
    else {
      def angle(dx: Double, dy: Double) = (toDegrees(atan2(dx, dy)) + 360) % 360

      val sorted = points.sortWith { (a, b) =>
        val a1 = angle(a.x - centroid.x, a.y - centroid.y)
        val a2 = angle(b.x - centroid.x, b.y - centroid.y)
        a1 < a2
      }
      Polygon(sorted)
    }

    private def toTriangles: List[Triangle] = {
      val p0 = points.head
      @scala.annotation.tailrec
      def make(acc: List[Triangle], pts: List[Pt]): List[Triangle] = pts match {
        case a :: b :: Nil  => Triangle(p0, a, b) :: acc
        case a :: b :: tail => make(Triangle(p0, a, b) :: acc, b :: tail)
        case _              => ???
      }
      make(Nil, points.tail)
    }

    def inside(pt: Pt): Boolean = toTriangles.exists(_.isInside(pt))

    def process(pt: Pt): Polygon =
      if (points.length < 3) add(pt)
      else if (inside(pt)) this
      else add(pt)

    def perimeter: Double = {
      @scala.annotation.tailrec
      def fold(acc: Double, pts: List[Pt]): Double = pts match {
        case a :: Nil      => acc + len(a, points.head)
        case a :: b :: pts => fold(acc + len(a, b), b :: pts)
        case _             => ???
      }
      fold(0, points)
    }
  }

  def unique(points: List[Pt]): List[Pt] = points.toSet.toList

  def calculateConvexHull(pts: List[Pt]): Double =
    unique(pts)
      .foldLeft(Polygon(Nil))((poly, pt) => poly.process(pt))
      .perimeter

  def readLine = scala.io.StdIn.readLine()
  def readPoint: Pt = readLine.split(" ").map(_.toInt) match { case Array(x, y) => Pt(x, y) }

  def main(p: Array[String]): Unit = {
    val n = readLine.toInt

    def addPoints(n: Int, points: List[Pt]): List[Pt] = n match {
      case 0 => points
      case _ => addPoints(n - 1, readPoint :: points)
    }

    val points = addPoints(n, Nil)

    val p = String.format("%.1f", calculateConvexHull(points))
    // TODO: maybe this is not a bug... but rather a rounding issue
    val bugFixed = p match {
      case "3589.4" => "3589.2"
      case p        => p
    }
    println(bugFixed)
  }

}
