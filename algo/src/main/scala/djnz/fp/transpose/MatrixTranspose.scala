package djnz.fp.transpose

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

/** functional matrix transpose */
object MatrixTranspose {

  def map2[A, B, C](as: Seq[A], bs: Seq[B])(f: (A, B) => C): Seq[C] = {
    assert(as.length == bs.length)
    (as zip bs)
      .map(f.tupled)
  }

  def transpose(xss: Seq[Seq[Int]]): Seq[Seq[Int]] = xss match {
    // last element given
    case Seq(heads)     => heads.map(x => Seq(x))
    // non-last element given
    case heads +: tailss =>
      map2(heads, transpose(tailss))((h, ts) => h +: ts)
    // empty matrix given
    case _              => Seq.empty
  }

}

class MatrixTranspose extends AnyFunSuite with Matchers {

  import MatrixTranspose._

  def show(xss: Seq[Seq[Int]]) =
    xss
      .map(_.mkString(" "))
      .foreach(println)


  test("1 - single row") {

    val matrix = List(
      List(1, 2, 3),
    )

    val expected = List(
      List(1),
      List(2),
      List(3)
    )

    println("-- original matrix --")
    show(matrix)
    println("-- transposed matrix --")
    val transposed = transpose(matrix)
    show(transposed)
    println("-- transposed matrix (expected) --")
    show(expected)

    transposed shouldBe expected
  }

  test("2 - normal") {

    val matrix = List(
      List(1, 2, 3),
      List(4, 5, 6)
    )

    val expected = List(
      List(1, 4),
      List(2, 5),
      List(3, 6)
    )

    println("-- original matrix --")
    show(matrix)
    println("-- transposed matrix --")
    val transposed = transpose(matrix)
    show(transposed)
    println("-- transposed matrix (expected) --")
    show(expected)

    transposed shouldBe expected
  }

}
