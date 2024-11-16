package djnz.hackerrank.fp.a4dp

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/reverse-factorization/problem */
object P5ReverseFactorizationClassicSlow {

  import scala.math.Ordering.Implicits.seqOrdering
  def next() = scala.io.StdIn.readLine()

  def represent(xs: Seq[Int]) =
    xs.foldLeft(List(1)) {
      case (l @ h :: _, x) => (x * h) :: l
      case _               => sys.error("can't be by design")
    }.reverse

  def findDistinctFactors(n: Int, ks: Set[Int]): List[List[Int]] = n match {
    case 1 => List(Nil)
    case n => ks
        .filter(k => n % k == 0)
        .toList
        .flatMap(k => findDistinctFactors(n / k, ks - k).map(sub => k :: sub))
  }

  def findFactors(n: Int, ks: List[Int]): List[List[Int]] = n match {
    case 1 => List(List(1))
    case n => ks
        .filter(k => n % k == 0)
        .flatMap(k => findFactors(n / k, ks).map(sub => k :: sub))
  }

  def solve(n: Int, ks: Seq[Int]): Seq[Int] =
    findFactors(n, ks.filter(k => n % k == 0).toList.sorted(Ordering.Int.reverse))
      .groupBy(_.length)
      .minByOption { case (len, _) => len }
      .map(_._2)
      // now we have all of them of one length
      .map(_.sorted)
      .flatMap(_.minOption)
      .map(represent)
      .getOrElse(List(-1))

  def main(args: Array[String]): Unit = {
    val (n, _) = next().split(" ").map(_.toInt) match { case Array(n, k) => n -> k }
    val ks = next().split(" ").map(_.toInt)

    val r = solve(n, ks).mkString(" ")
    println(r)
  }

}

class P5ReverseFactorizationClassicSlow extends ASuite {
  import P5ReverseFactorizationClassicSlow._

  test("1") {
    val fact = Seq(3, 4, 5)
    val x = represent(fact)
    pprint.log(fact)
    pprint.log(x)
    x shouldBe List(1, 3, 12, 60)
  }

  test("2") {
    val xs = findDistinctFactors(12, Set(2, 3, 4, 5, 6))
    pprint.log(xs)
  }

  test("3") {
    val xs = findFactors(231000000, List(2, 3, 5, 7, 11, 13, 17, 19))
    pprint.log(xs.head)
  }

}
