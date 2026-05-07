package djnz.hackerrank.fp.a6adhoc

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

/** https://www.hackerrank.com/challenges/puzzle-and-pc/problem = 80 */
object P13PuzzleAndPc {

  import scala.io.StdIn.readLine

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

  def mkTromino(x0: Int, y0: Int, blocked: At): Tromino = {
    val ats = for {
      x <- x0 to x0 + 1
      y <- y0 to y0 + 1
      if !(x == blocked.x && y == blocked.y)
    } yield At(x, y)
    Tromino.fromSeq(ats)
  }

  // a b
  // c d
  sealed trait Q
  case object A extends Q
  case object B extends Q
  case object C extends Q
  case object D extends Q

  def solve(x1: Int, y1: Int, x2: Int, y2: Int, blocked: At): List[Tromino] =
    if (x2 - x1 == 2) List(mkTromino(x1, y1, blocked))
    else {
      // center coordinates
      val xd = (x2 + x1) >> 1
      val yd = (y2 + y1) >> 1

      // artificial Tromino used as blocked for subsolutions
      val artA = At(xd - 1, yd - 1)
      val artB = At(xd, yd - 1)
      val artC = At(xd - 1, yd)
      val artD = At(xd, yd)

      // pack parameters to use them later
      val quads = List(
        A -> ((x1, y1, xd, yd), artA),
        B -> ((xd, y1, x2, yd), artB),
        C -> ((x1, yd, xd, y2), artC),
        D -> ((xd, yd, x2, y2), artD)
      )

      val blockedQuad: Q =
        if (blocked.x < xd && blocked.y < yd) A
        else if (blocked.x >= xd && blocked.y >= yd) D
        else if (blocked.x < xd) C
        else B

      // eliminate blocked & collect only artificial
      val cs = quads.collect { case (q, (_, art)) if q != blockedQuad => art }

      val t0 = Tromino.fromSeq(cs)

      val sols4 = quads
        .flatMap {
          case (q, ((xa, ya, xb, yb), art)) =>
            val hole = if (q == blockedQuad) blocked else art
            solve(xa, ya, xb, yb, hole)
        }

      t0 :: sols4

    }

  def main(args: Array[String]): Unit = {
    val m = readLine.toInt
    val Array(y, x) = readLine.split(" ").map(_.toInt)
    val blocked = At(x - 1, y - 1) // 1 based
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
    mkTromino(6, 10, At(7, 10)) shouldEqual Tromino(At(6, 10), At(6, 11), At(7, 11))
  }

  test("2^n") {
    pow(1) shouldBe 2
    pow(2) shouldBe 4
    pow(3) shouldBe 8
  }

}
