package djnz.hackerrank.fp.a2recursion

// https://en.wikipedia.org/wiki/Concave_polygon
/** https://www.hackerrank.com/challenges/lambda-march-concave-polygon/problem */
object P19IsConcavePolygon {

  import scala.math.atan2
  import scala.math.toDegrees

  implicit class BoolOps(x: Boolean) {
    def yn = if (x) "YES" else "NO"
  }

  sealed trait Loc
  case object L extends Loc
  case object R extends Loc

  case class Pt(x: Double, y: Double)
  case class Line(a: Pt, b: Pt) {

    /** a bit of math:
      * https://math.stackexchange.com/questions/274712/calculate-on-which-side-of-a-straight-line-is-a-given-point-located
      * sign = (x-x1)(y2-y1)-(y-y1)(x2-x1)
      */
    def loc(pt: Pt): Option[Loc] =
      (pt.x - b.x) * (a.y - b.y) - (a.x - b.x) * (pt.y - b.y) match {
        case 0.0 | -0.0 => None
        case r if r > 0 => Some(R)
        case r if r < 0 => Some(L)
        case _          => None
      }
  }

  private def centroid(points: List[Pt]): Pt = {
    val cnt = points.length
    val sum = points.reduce((p1, p2) => Pt(p1.x + p2.x, p1.y + p2.y))
    Pt(sum.x / cnt, sum.y / cnt)
  }

  private def sort(pts: List[Pt]): List[Pt] = if (pts.length <= 3) pts
  else {
    def angle(dx: Double, dy: Double) = (toDegrees(atan2(dx, dy)) + 360) % 360
    val c = centroid(pts)

    pts.sortWith { (a, b) =>
      val a1 = angle(a.x - c.x, a.y - c.y)
      val a2 = angle(b.x - c.x, b.y - c.y)
      a1 < a2
    }
  }

  def isConcave(ps: List[Pt]): Boolean = {
    // it fails if these 3 points on the same line
    val sign0 = Line(ps(0), ps(1)).loc(ps(2))

    //                         keep go, sign
    def go(ps: List[Pt], acc: (Boolean, Option[Loc])): Boolean = ps match {
      case a :: (tail @ b :: c :: _) =>
        Line(a, b).loc(c) match {
          case None             => go(tail, (true, sign0)) // point on the line, don't count
          case s if s == acc._2 => go(tail, (true, sign0)) // same side, move on
          case _                => false                   // wrong side
        }
      case _                         => sign0 == acc._2
    }

    val convex = go(ps.tail, (true, sign0))
    !convex
  }

  def solve(ps0: List[Pt]) = {
    val ps = sort(ps0)
    isConcave(ps ::: ps.take(2))
  }

  def readLine = scala.io.StdIn.readLine()
  def readPoint: Pt = readLine.split(" ").map(_.toInt) match { case Array(x, y) => Pt(x, y) }
  def readPoints(n: Int, acc: List[Pt]): List[Pt] = n match {
    case 0 => acc.reverse
    case _ => readPoints(n - 1, readPoint :: acc)
  }

  def main(p: Array[String]): Unit = {
    val n = readLine.toInt
    val points = readPoints(n, Nil)
    val r = solve(points)
    println(r.yn)
  }

}
