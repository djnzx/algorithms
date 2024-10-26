package djnz.hackerrank.fp.a2recursion

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/functional-programming-warmups-in-recursion---fibonacci-numbers/problem */
object P2Fibonacci {

  /** classic recursion `unfold` based, with state of 2 numbers */
  def f1(n: Int): Int = {

    case class S(n: Int, f1: Int, f2: Int)
    val s0 = S(n, 0, 1)

    LazyList.unfold(s0) {
      case S(0, _, _)   => None
      case S(n, f1, f2) => Some((f1, S(n - 1, f2, f1 + f2)))
    }.last
  }

  /** classic recursion */
  def f(n: Int): Int = {
    assert(n > 0)

    def go(n: Int, f1: Int, f2: Int): Int = n match {
      case 1 => f1
      case n => go(n - 1, f2, f1 + f2)
    }

    go(n, 0, 1)
  }

}

class P2Fibonacci extends ASuite {

  import P2Fibonacci._

  test("1") {
    val testData = Table(
      "in" -> "out",
      1    -> 0,
      2    -> 1,
      3    -> 1,
      4    -> 2,
      5    -> 3,
      6    -> 5,
      7    -> 8,
      8    -> 13,
      9    -> 21,
      10   -> 34,
      11   -> 55,
    )

    forAll(testData) { case (in, out) =>
      f1(in) shouldBe out
      f(in) shouldBe out
    }
  }

}
