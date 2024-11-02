package djnz.hackerrank.fp.a3structures

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/matrix-rotation/problem */
object P2MatrixRotation {

  def next() = scala.io.StdIn.readLine()

  type Matrix = List[List[Int]]

  /** we need an orientation for the most inner scalp */
  sealed trait Orientation
  case object Hor extends Orientation
  case object Ver extends Orientation
  case class Scalp(data: List[Int], or: Option[Orientation])

  def shift(scalp: Scalp, n: Int): Scalp = scalp.copy(
    data = scalp.data.splitAt(n % scalp.data.length) match {
      case (a, b) => b ++ a
    }
  )

  def toMatrix(s: Scalp): Matrix = s match {
    case Scalp(_, None)         => sys.error("doesn't make sense for not the most inner one")
    case Scalp(data, Some(Hor)) =>
      data.splitAt(data.length / 2) match {
        case (top, down) => List(top, down.reverse)
      }
    case Scalp(data, Some(Ver)) =>
      val x1 = data.head
      val (col2, col1r) = data.tail.splitAt(data.length / 2)
      val col1 = x1 :: col1r.reverse

      (col1 zip col2).map { case (a, b) => List(a, b) }
  }

  def inner(m: Matrix) = m.tail.init.map(_.tail.init)

  def scalp(m: Matrix): (Scalp, Option[Matrix]) = {
    val (col1, colN) = m.tail.init.map(m => m.head -> m.last).unzip
    val row1 = m.head
    val rowN = m.last
    val scalp = row1 ++ colN ++ rowN.reverse ++ col1.reverse

    (m.length, m.head.length) match {
      case (2, _) => Scalp(scalp, Some(Hor)) -> None
      case (_, 2) => Scalp(scalp, Some(Ver)) -> None
      case _      => Scalp(scalp, None)      -> Some(inner(m))
    }
  }

  def combine(scalp: Scalp, m: Matrix): Matrix = {
    val h = m.size
    val w = m.head.size + 2

    scalp.data.splitAt(w) match {
      case (row1, residual) =>
        residual.splitAt(h) match {
          case (colN, residual) =>
            residual.splitAt(w) match {
              case (rowNr, col1r) =>
                val core =
                  (col1r.reverse zip m zip colN)
                    .map { case ((x0, xs), xn) => (x0 :: xs) :+ xn }

                (row1 :: core) :+ rowNr.reverse
            }
        }
    }
  }

  def rotate(m: Matrix, r: Int): Matrix =
    scalp(m) match {
      case (s, None)    =>
        val shifted = shift(s, r)
        toMatrix(shifted)
      case (s, Some(m)) =>
        val scalpShifted = shift(s, r)
        val nestedRotated = rotate(m, r)
        combine(scalpShifted, nestedRotated)
    }

  def main(args: Array[String]): Unit = {
    val (m, n, r) = next().split(" ").map(_.toInt) match { case Array(a, b, c) => (a, b, c) }

    val matrix = (1 to m).map { _ =>
      next().split(" ").map(_.toInt).take(n).toList
    }.toList

    val matrix2 = rotate(matrix, r)

    matrix2.map(_.mkString(" "))
      .foreach(println)
  }

  def show(m: Matrix) = {
    val w = m.map(_.max).max.toString.length

    m.map(_.map(xi => s"%${w}d".formatted(xi)).mkString(" "))
      .foreach(println)
  }

}

class P2MatrixRotation extends ASuite {
  import P2MatrixRotation._

  val input = """4 4 1
                |1 2 3 4
                |5 6 7 8
                |9 10 11 12
                |13 14 15 16
                |""".stripMargin

  val matrix = input
    .split("\n").filter(_.nonEmpty).drop(1)
    .map(_.split(" ").map(_.toInt).toList)
    .toList

  test("rotate") {
    show(matrix)
    val m2 = rotate(matrix, 1)
    println
    show(m2)
  }

  test("inner") {
    show(matrix)
    val m2 = inner(matrix)
    println
    show(m2)
  }

  test("scalp") {
    show(matrix)
    val (scalp0, m2) = scalp(matrix)
    pprint.log(scalp0)
    show(m2.get)
  }

  test("shift") {
    pprint.log(shift(Scalp((1 to 10).toList, Some(Hor)), 22))
  }

  test("scalp => matrix") {
    val s = Scalp(List(1, 2, 3, 4, 5, 6), Some(Hor))
    val m = toMatrix(s)
    show(m)
  }

  test("hor") {
    val m: List[List[Int]] =
      (1 to 8).map(y =>
        (1 to 10).map(x => 100 + y * 10 + x).toList
      ).toList
    show(m)

    val r = rotate(m, 40)
    println
    show(r)
  }

  test("ver") {
    val m: List[List[Int]] =
      (1 to 10).map(y =>
        (1 to 8).map(x => 100 + y * 10 + x).toList
      ).toList
    show(m)

    val r = rotate(m, 40)
    println
    show(r)
  }
}
