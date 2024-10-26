package djnz.codility

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import scala.annotation.tailrec

/** 2020-09-03
  *
  * Write a function:
  * def solution(a: Array[Int]): Int
  *
  * that, given an array A of N integers,
  * returns the smallest positive integer
  * (greater than 0) that does not occur in A.
  *
  * For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
  * Given A = [1, 2, 3], the function should return 4.
  * Given A = [−1, −3], the function should return 1.
  *
  * N is an integer within the range [1..100,000];
  * each element of array A is an integer within the range [−1,000,000..1,000,000].
  */
object MinNonNegativeOr1 {

  def solve(xs0: Array[Int]): Int = {
    val xs = xs0.filter(_ > 0).sorted.distinct

    @tailrec
    def go(idx: Int, next: Int): Int = () match {
      case _ if idx >= xs.length => next
      case _ if xs(idx) == next  => go(idx + 1, next + 1)
      case _                     => next
    }

    go(0, 1)
  }

}

class MinNonNegativeOr1 extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {
  import MinNonNegativeOr1._

  val testData = Table(
    "in"                    -> "out",
    Array.empty[Int]        -> 1,
    Array(1, 2, 3)          -> 4,
    Array(1, 3, 4)          -> 2,
    Array(-1, -2, -3)       -> 1,
    Array(1, 3, 6, 4, 1, 2) -> 5,
    Array(1, 3, 4, 4, 1, 2) -> 5
  )

  forAll(testData) { (in, exp) =>
    solve(in) shouldBe exp
  }
}
