package djnz.interviews.agileengine

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

/** Calculate the rightmost index in the given array
  * where the counts of occurrences of `x` and `y` are equal
  * when scanning from the start to that index.
  */
object Task1 {

  case class S(idx: Int, xc: Int, yc: Int, r: Int)
  val s0 = S(0, 0, 0, -1)

  def solution(x: Int, y: Int, aa: Array[Int]): Int =
    aa.foldLeft(s0) { case (S(idx, xc, yc, r), a) =>
      val xc2 = xc + (if (x == a) 1 else 0)
      val yc2 = yc + (if (y == a) 1 else 0)
      val r2 = if (xc2 == yc2) idx else r
      S(idx + 1, xc2, yc2, r2)
    }.r

}

class Task1 extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  test("1") {
    1 shouldBe 1
  }

}
