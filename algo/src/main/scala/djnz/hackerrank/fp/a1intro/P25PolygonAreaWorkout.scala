package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/lambda-march-compute-the-area-of-a-polygon/problem */
object P25PolygonAreaWorkout {

  def sq(x: Double): Double = x * x

  case class Pt(x: Double, y: Double) {
    def this(xy: Array[Int]) = this(xy(0), xy(1))
    def isAtRightFrom(line: Line): Boolean = line.isPtAtRight(this)
    def isAtLeftFrom(line: Line): Boolean = line.isPtAtLeft(this)
    def isOn(line: Line): Boolean = line.isPtOn(this)
  }
  case class Line(a: Pt, b: Pt) {

    /** a bit of math:
      * https://math.stackexchange.com/questions/274712/calculate-on-which-side-of-a-straight-line-is-a-given-point-located
      * sign = (x-x1)(y2-y1)-(y-y1)(x2-x1)
      */
    def sign(pt: Pt): Double =
      (pt.x - b.x) * (a.y - b.y) - (a.x - b.x) * (pt.y - b.y) match {
        case r if r > 0 => 1
        case r if r < 0 => -1
        case _          => 0
      }
    def isPtAtLeft(pt: Pt): Boolean = sign(pt) > 0
    def isPtAtRight(pt: Pt): Boolean = sign(pt) < 0
    def isPtOn(pt: Pt): Boolean = sign(pt) == 0
  }

  import scala.math.sqrt
  def distance(a: Pt, b: Pt): Double = sqrt(sq(a.x - b.x) + sq(a.y - b.y))

  case class Triangle(a: Pt, b: Pt, c: Pt) {
    def sides: (Double, Double, Double) =
      (distance(a, b), distance(a, c), distance(b, c))
    def isInside(p: Pt): Boolean = {
      val d1 = Line(a, b).sign(p)
      val d2 = Line(b, c).sign(p)
      val d3 = Line(c, a).sign(p)
      val neg = d1 < 0 && d2 < 0 && d3 < 0
      val pos = d1 > 0 && d2 > 0 && d3 > 0
      neg ^ pos
    }
    def isNoneInside(pts: List[Pt]): Boolean = pts.forall(!isInside(_))
    def isAnyInside(pts: List[Pt]): Boolean = pts.exists(isInside)
    def area = {
      val (lab, lac, lbc) = sides
      val s2 = (lab + lac + lbc) / 2
      sqrt(s2 * (s2 - lab) * (s2 - lac) * (s2 - lbc))
    }
  }

  @scala.annotation.tailrec
  def listToTrianglesConcave(pts: List[Pt], trios: List[Triangle]): List[Triangle] = pts match {
    case a :: b :: c :: Nil  => Triangle(a, b, c) :: trios
    case a :: b :: c :: tail =>
      val t = Triangle(a, b, c)
      val ac = Line(a, c)
      if (ac.isPtAtRight(b) && t.isNoneInside(pts))
        listToTrianglesConcave(a :: c :: tail, t :: trios)
      else listToTrianglesConcave(b :: c :: tail ::: (a :: Nil), trios)
    case _                   => ???
  }

  /** https://www.mathopenref.com/coordpolygonarea.html
    * https://en.wikipedia.org/wiki/Shoelace_formula
    * works 100%
    */
  def area(ps: Seq[Pt]): Double = {
    val pts = (ps :+ ps.head).toArray
    val x2 = (1 until pts.length).foldLeft(0.0) { (sum, i) =>
      sum + pts(i - 1).x * pts(i).y - pts(i - 1).y * pts(i).x
    }
    math.abs(x2 / 2)
  }

  def next = scala.io.StdIn.readLine()

  def main(p: Array[String]): Unit = {
    val points = (1 to next.toInt)
      .map(_ => next)
      .map(_.split(" "))
      .map(_.map(_.toInt))
      .map { case Array(x, y) => Pt(x, y) }
    val p = area(points)
    println(p)
  }

}
