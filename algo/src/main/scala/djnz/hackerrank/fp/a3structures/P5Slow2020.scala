package djnz.hackerrank.fp.a3structures

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/prison-transport/problem
  * https://cp-algorithms.com/data_structures/disjoint_set_union.html
  * it works, but fails on 100.000 pairs
  */
object P5Slow {

  def price1g(n: Int): Int = math.ceil(math.sqrt(n)).toInt
  def priceNg(xs: Iterable[Int]): Int = xs.foldLeft(0)((a, x) => a + price1g(x))
  def price(n: Int, xs: Iterable[Int]): Int = n - xs.sum + priceNg(xs)

  sealed trait R
  case object BothNotFound extends R
  case object BothInTheSame extends R
  case class BothAlready(as: Set[Int], bs: Set[Int]) extends R
  case class Aonly(as: Set[Int]) extends R
  case class Bonly(bs: Set[Int]) extends R

  def pull(groups: List[Set[Int]], x: Int): (Option[Set[Int]], List[Set[Int]]) = {

    def go(groups: List[Set[Int]], acc: List[Set[Int]]): (Option[Set[Int]], List[Set[Int]]) =
      groups match {
        case Nil                     => (None, acc) // not found
        case h :: t if h.contains(x) => (Some(h), t ++ acc)
        case h :: t                  => go(t, h :: acc)
      }

    go(groups, Nil)
  }

  def pull(groups: List[Set[Int]], a: Int, b: Int): (List[Set[Int]], R) =
    pull(groups, a) match {
      case (Some(as), residual) =>
        as.contains(b) match {
          case true  => (groups, BothInTheSame) // a, b - same
          case false =>
            pull(residual, b) match {
              case (Some(bs), residual1) => (residual1, BothAlready(as, bs)) // a, b - diff
              case (None, _)             => (residual, Aonly(as))            // a only
            }
        }

      case (None, residual) =>
        pull(groups, b) match {
          case (Some(bs), residual1) => (residual1, Bonly(bs))   // b only
          case (None, _)             => (residual, BothNotFound) // not found
        }
    }

  case class Cnt(groups: List[Set[Int]]) {
    def count(a: Int, b: Int): Cnt =
      pull(groups, a, b) match {
        case (gs, BothNotFound)        => Cnt(Set(a, b) :: gs)
        case (_, BothInTheSame)        => this                  // probably no
        case (gs, BothAlready(as, bs)) => Cnt((as ++ bs) :: gs) //
        case (gs, Aonly(as))           => Cnt((as + b) :: gs)
        case (gs, Bonly(bs))           => Cnt((bs + a) :: gs)
      }
  }

  def solve(pairs: Seq[(Int, Int)]) =
    pairs
      .foldLeft(Cnt(Nil)) { case (acc, (a, b)) => acc.count(a, b) }
      .groups
      .map(_.size)

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    val nPairs = next().toInt
    val pairs = (1 to nPairs)
      .map(_ =>
        next().split(" ").map(_.toInt) match {
          case Array(a, b) => (a, b)
          case _           => ???
        }
      )
    val gs = solve(pairs)
    val x = price(n, gs)
    println(x)
  }

}

class P5Slow extends ASuite {

  import P5Slow._

  test("1") {
    val xs = List(Set(1, 2), Set(3, 4), Set(5, 6))
    pull(xs, 1) shouldBe (Some(Set(1, 2)), List(Set(3, 4), Set(5, 6)))
    pull(xs, 3) shouldBe (Some(Set(3, 4)), List(Set(5, 6), Set(1, 2)))
    pull(xs, 7) shouldBe (None, List(Set(5, 6), Set(3, 4), Set(1, 2)))
  }

  test("2") {
    val xs = List(Set(1, 2), Set(3, 4), Set(5, 6))

    pprint.log(pull(xs, 11, 12)) // BothNotFound
    pprint.log(pull(xs, 1, 2))   // BothInTheSame
    pprint.log(pull(xs, 1, 100)) // Aonly(as = Set(1, 2)
    pprint.log(pull(xs, 100, 1)) // Bonly(bs = Set(1, 2)
    pprint.log(pull(xs, 1, 3))   // BothAlready(as = Set(1, 2), bs = Set(3, 4))
  }

  val xs = "6 11\n9 5\n11 9\n15 9\n13 15\n12 14\n15 16\n1 16"
    .split("\n")
    .map(_.split(" "))
    .map(_.map(_.toInt))
    .map { case Array(a, b) => (a, b) }

  test("3") {
    val x = solve(xs)
    x.foreach(println)
  }

  def mk0(xs: Seq[(Int, Int)]) =
    (xs ++ xs.map { case (a, b) => (b, a) })
      .groupMap(_._1)(_._2)

  test("4") {
    val x = mk0(xs)
    pprint.log(x)
  }

}
