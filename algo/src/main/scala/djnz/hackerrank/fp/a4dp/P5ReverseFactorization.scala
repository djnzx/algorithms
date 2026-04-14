package djnz.hackerrank.fp.a4dp

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/reverse-factorization/problem */
object P5ReverseFactorization {

  def next() = scala.io.StdIn.readLine()

  /** tail recursion */
  def factorsTR(ks: List[Int], n: Int, acc: List[Int]): List[Int] = (ks, n) match {
    case (Nil, _) => List(-1) // impossible
    case (_, 1)   => 1 :: acc
    case (k :: ks, n) if n % k == 0 => factorsTR(k :: ks, n / k, n :: acc)
    case (_ :: ks, n) => factorsTR(ks, n, acc)
  }

  /** plain recursion */
  def factors(ks: List[Int], n: Int): List[Int] = {

    def go(ks: List[Int], n: Int): List[Int] = (ks, n) match {
      case (_, 1)   => List(1)
      case (Nil, _) => List(-1) // impossible
      case (k :: ks, n) if n % k == 0 => n :: go(k :: ks, n / k)
      case (_ :: ks, n) => go(ks, n)
    }

    val nn = go(ks, n)
    if (nn.contains(-1)) List(-1)
    else nn
  }

  def solve(n: Int, ks: Seq[Int]) =
    factors(ks.sorted(Ordering.Int.reverse).toList, n).sorted

  def solveTR(n: Int, ks: Seq[Int]) =
    factorsTR(ks.sorted(Ordering.Int.reverse).toList, n, List.empty).sorted

  def main(args: Array[String]): Unit = {
    val (n, _) = next().split(" ").map(_.toInt) match { case Array(n, k) => n -> k }
    val ks = next().split(" ").map(_.toInt)

    val r = solve(n, ks).mkString(" ")
    println(r)
  }

}

class P5ReverseFactorization extends ASuite {
  import P5ReverseFactorization._

  test("1") {
    val xs = factorsTR(List(2, 3, 5, 7, 11, 13, 17, 19).reverse, 231000000, Nil)
    pprint.log(xs)
  }

  test("2") {
    val xs = factorsTR(List(2, 4, 6, 8, 10).reverse, 64, Nil)
    pprint.log(xs)
  }

  test("3") {
    val xs = factors(List(2, 4, 6, 8, 10).reverse, 64)
    pprint.log(xs)
  }

  test("1a") {
    val xs = solve(15, Seq(2, 10, 6, 9, 11))
    pprint.log(xs)
  }

  test("1 r") {
    val xs = solve(12, Seq(2, 3, 4))
    pprint.log(xs)
  }

  test("1 tr") {
    val xs = solveTR(12, Seq(2, 3, 4))
    pprint.log(xs)
  }

  test("5 tail recursive") {
    val xs = factorsTR(Seq(4, 5, 6, 7, 8, 10, 12, 17, 18, 19).sorted.reverse.toList, 175840877, List.empty)
    pprint.log(xs)
  }

  test("5 recursive") {
    val xs = factors(Seq(4, 5, 6, 7, 8, 10, 12, 17, 18, 19).sorted.reverse.toList, 175840877)
    pprint.log(xs)
  }

  test("2 tail recursive") {
    val xs = solveTR(72, "2 4 6 9 3 7 16 10 5".split("\\s").map(_.toInt).sorted.reverse)
    pprint.log(xs)
  }

  test("2 recursive") {
    val xs = solve(72, "2 4 6 9 3 7 16 10 5".split("\\s").map(_.toInt).sorted.reverse)
    pprint.log(xs)
  }

  // 1, 2, 4, 8, 16, 32, 64, 192, 960, 4800, 24000, 120000, 600000, 3000000, 21000000, 231000000
  test("11 tail recursive") {
    val xs = solveTR(231000000, "2 3 5 7 11 13 17 19".split("\\s").map(_.toInt).sorted.reverse)
    pprint.log(xs)
  }

  test("11 recursive") {
    val xs = solve(231000000, "2 3 5 7 11 13 17 19".split("\\s").map(_.toInt).sorted.reverse)
    pprint.log(xs)
  }

}
