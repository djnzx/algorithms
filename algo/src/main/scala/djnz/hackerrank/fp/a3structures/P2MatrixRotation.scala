package djnz.hackerrank.fp.a3structures

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/matrix-rotation/problem */
object P2MatrixRotation {

  def next() = scala.io.StdIn.readLine()

  type Matrix = List[List[Int]]
  sealed trait Orientation
  case object Hor extends Orientation
  case object Ver extends Orientation
  case class Scalp(data: List[Int], or: Option[Orientation])

  def shift(scalp: Scalp, n: Int): Scalp =
    scalp.copy(
      data = scalp.data.splitAt(n % scalp.data.length) match {
        case (a, b) => b ++ a
      }
    )

  def toMatrix(s: Scalp): Matrix = s match {
    case Scalp(_, None)         => ???
    case Scalp(data, Some(Hor)) => data.splitAt(data.length / 2) match {
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

  test("not-failed") {
    val m: List[List[Int]] =
      (1 to 8).map(y =>
        (1 to 10).map(x => 100 + y * 10 + x).toList
      ).toList
    show(m)

    val r = rotate(m, 40)
    println
    show(r)
  }

  test("failed") {
    val m: List[List[Int]] =
      (1 to 10).map(y =>
        (1 to 8).map(x => 100 + y * 10 + x).toList
      ).toList
    show(m)

    val r = rotate(m, 40)
    println
    show(r)
  }

  test("failed2") {
    val in =
      """
        |9718805 60013003 5103628 85388216 21884498 38021292 73470430 31785927
        |69999937 71783860 10329789 96382322 71055337 30247265 96087879 93754371
        |79943507 75398396 38446081 34699742 1408833 51189 17741775 53195748
        |79354991 26629304 86523163 67042516 54688734 54630910 6967117 90198864
        |84146680 27762534 6331115 5932542 29446517 15654690 92837327 91644840
        |58623600 69622764 2218936 58592832 49558405 17112485 38615864 32720798
        |49469904 5270000 32589026 56425665 23544383 90502426 63729346 35319547
        |20888810 97945481 85669747 88915819 96642353 42430633 47265349 89653362
        |55349226 10844931 25289229 90786953 22590518 54702481 71197978 50410021
        |9392211 31297360 27353496 56239301 7071172 61983443 86544343 43779176
        |""".stripMargin
    val matrix = in
      .split("\n").filter(_.nonEmpty).drop(1)
      .map(_.split(" ").map(_.toInt).toList)
      .toList
    show(matrix)
    val r = rotate(matrix, 40)
    println
    show(r)
  }
}
