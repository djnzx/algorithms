package djnz.hackerrank.fp.a6adhoc

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import scala.io.StdIn.readLine

/** https://www.hackerrank.com/challenges/puzzle-and-pc/problem = 80 */
object P13PuzzleAndPc {

  def pow(n: Int) = 1 << n

  case class At(x: Int, y: Int) {
    override def toString: String = s"${y + 1} ${x + 1}" // we have 1 based notation
  }

  case class Tromino(a: At, b: At, c: At) {
    override def toString: String = s"$a $b $c"

    override def equals(obj: Any): Boolean = obj match {
      case that: Tromino => Set(this.a, this.b, this.c) == Set(that.a, that.b, that.c)
      case _             => false
    }
  }
  object Tromino {
    def fromSeq(xs: Seq[At]): Tromino =
      xs match {
        case Seq(a, b, c) => Tromino(a, b, c)
        case _            => sys.error("should be seq of 3 elements")
      }
  }

  def mkTromino0(x0: Int, y0: Int, blocked: At): Tromino = {
    val ats = for {
      x <- x0 to x0 + 1
      y <- y0 to y0 + 1
      if !(x == blocked.x && y == blocked.y)
    } yield At(x, y)
    Tromino.fromSeq(ats)
  }

  def solve(x1: Int, y1: Int, x2: Int, y2: Int, blocked: At): List[Tromino] =
    // if 2x2 -> mkTromino0
    // else:
    //  split on 4
    //  put artificial tromino
    //  solve 4
    //  combine the result
    ???

  def main(args: Array[String]): Unit = {
    val m = readLine.toInt
    val Array(y, x) = readLine.split(" ").map(_.toInt)
    val blocked = At(x, y)
    val size = pow(m)

    solve(0, 0, size, size, blocked)
      .foreach(println)
  }

}

class P13PuzzleAndPc extends AnyFunSuite with Matchers {

  import P13PuzzleAndPc._

  test("at") {
    At(1, 2).toString shouldBe "3 2"
  }

  test("tromino") {
    Tromino(At(0, 1), At(0, 0), At(1, 0)).toString shouldBe "2 1 1 1 1 2"
  }

  test("tromino equals") {
    Tromino(At(0, 1), At(0, 0), At(1, 0)) shouldEqual Tromino(At(0, 0), At(0, 1), At(1, 0))
  }

  test("mkTromino0") {
    mkTromino0(6, 10, At(7, 10)) shouldEqual Tromino(At(6, 10), At(6, 11), At(7, 11))
  }

  test("2^n") {
    pow(1) shouldBe 2
    pow(2) shouldBe 4
    pow(3) shouldBe 8
  }

}
