package djnz.hackerrank.fp.a4dp

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/reverse-factorization/problem */
object P5ReverseFactorization {

  def next() = scala.io.StdIn.readLine()

  /** tail recursion */
  def factorsTR(ks: List[Int], n: Int, acc: List[Int]): List[Int] = (ks, n) match {
    case (Nil, _) => List.empty
    case (_, 1)   => 1 :: acc
    case (k :: ks, n) if n % k == 0 => factorsTR(k :: ks, n / k, n :: acc)
    case (_ :: ks, n) => factorsTR(ks, n, acc)
  }

  /** plain recursion, pure math */
  def factors(ks: List[Int], n: Int): List[Int] = (ks, n) match {
    case (Nil, _) => List.empty
    case (_, 1)   => List(1)
    case (k :: ks, n) if n % k == 0 => n :: factors(k :: ks, n / k)
    case (_ :: ks, n) => factors(ks, n)
  }

  def solve(n: Int, ks: Seq[Int]) =
    factors(ks.sorted.reverse.toList, n) match {
      case Nil => List(-1)
      case x   => x
    }

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

}
