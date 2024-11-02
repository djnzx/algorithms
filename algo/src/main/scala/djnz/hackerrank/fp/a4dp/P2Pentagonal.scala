package djnz.hackerrank.fp.a4dp

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/pentagonal-numbers/problem */
object P2Pentagonal {

  def next() = scala.io.StdIn.readLine()

  def penta(n: Int): Int =
    n match {
      case 1 => 1
      case n => 5 * (n - 1) + penta(n - 1) - (1 + (n - 2) * 2)
    }

  def computeAllByN(max: Int): Map[Int, Long] =
    LazyList.unfold(1 -> 1L) {
      case (n, _) if n > max => None
      case x @ (n, pn)       =>
        val common = 1 + (n - 1) * 2
        val pn1 = 5 * n + pn - common
        val s = (n + 1) -> pn1
        Some(x -> s)
    }
      .toMap

  def solve(xs: Seq[Int]): Seq[Long] = {
    val max = xs.max
    val all = computeAllByN(max)
    xs.map(x => all(x))
  }

  def main(args: Array[String]): Unit = {
    val t = next().toInt
    val ns = (1 to t).map(_ => next().toInt)
    solve(ns)
      .foreach(println)

  }
}

class P2Pentagonal extends ASuite {
  import P2Pentagonal._

  test("1") {
    Seq(1, 2, 3, 4, 5)
      .map(x => x -> penta(x))
      .foreach(x => pprint.log(x))

  }
  test("2") {
    computeAllByN(100000)
      .toList
      .sorted
      .foreach(println)
  }
}
