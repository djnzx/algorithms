package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/lambda-march-compute-the-area-of-a-polygon/problem */
object P25PolygonArea {

  case class Pt(x: Double, y: Double)

  def sq(x: Double): Double = x * x
  def distance(a: Pt, b: Pt): Double =
    scala.math.sqrt(sq(a.x - b.x) + sq(a.y - b.y))

  def area(ps: Seq[Pt]): Double = {
    val points = ps :+ ps.head
    val idx1 = points.indices
    val idx2 = points.indices.drop(1)

    val x2 = (idx1.iterator zip idx2.iterator)
      .map { case (i1, i2) => points(i1) -> points(i2) }
      .foldLeft(0.0) { case (sum, (p1, p2)) => sum + p1.x * p2.y - p1.y * p2.x }

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
