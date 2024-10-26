package djnz.hackerrank.fp.a1intro

import scala.annotation.tailrec

/** https://www.hackerrank.com/challenges/lambda-march-compute-the-perimeter-of-a-polygon/problem */
object P24PolygonPerimeter {

  case class Pt(x: Int, y: Int)

  def sq(x: Double): Double = x * x
  def distance(a: Pt, b: Pt): Double = scala.math.sqrt(sq(a.x - b.x) + sq(a.y - b.y))

  def perimeter(points: List[Pt]): Double = {

    @tailrec
    def go(points: List[Pt], acc: Double): Double = points match {
      case a :: (t @ b :: _) => go(t, acc + distance(a, b))
      case _                 => acc
    }

    go(points.last :: points, 0)
  }

  def next = scala.io.StdIn.readLine()

  def main(p: Array[String]): Unit = {
    val points = (1 to next.toInt)
      .map(_ => next)
      .map(_.split(" "))
      .map(_.map(_.toInt))
      .map { case Array(x, y) => Pt(x, y) }
      .toList
    val p = perimeter(points)
    println(p)
  }

}
