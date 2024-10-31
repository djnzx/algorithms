package djnz.hackerrank.fp.a3structures

import djnz.tools.ASuite
import org.scalactic.ColCompatHelper
import org.scalatest.ConfigMap
import scala.collection.immutable.AbstractMap
import scala.collection.immutable.AbstractSet
import scala.collection.immutable.SeqMap
import scala.collection.immutable.SortedMap
import scala.collection.immutable.SortedSet

/** https://www.hackerrank.com/challenges/prison-transport/problem
  * https://cp-algorithms.com/data_structures/disjoint_set_union.html
  */
object P5PrisonTransport {

  def next() = scala.io.StdIn.readLine()

  def price1g(n: Int): Int = math.ceil(math.sqrt(n)).toInt
  def priceNg(xs: Iterable[Int]): Int = xs.foldLeft(0)((a, x) => a + price1g(x))
  def priceTotal(n: Int, xs: Iterable[Int]): Int = n - xs.sum + priceNg(xs)

  def mkLinks(xs: Seq[(Int, Int)]) =
    (xs ++ xs.map { case (x, y) => (y, x) })
      .groupMapReduce(_._1)(x => Set(x._2))(_ ++ _)

  def repack(linked: Map[Int, Set[Int]]): List[Set[Int]] = {

    def extract(linked: Map[Int, Set[Int]], process: Set[Int]): (Map[Int, Set[Int]], Set[Int]) = {

      def go(links: Map[Int, Set[Int]], group0: Set[Int]): (Map[Int, Set[Int]], Set[Int]) = {
        val (linksReduced, groupWider) = group0
          .foldLeft(links -> group0) {
            case (a @ (linked, collected), gi) =>
              linked.get(gi) match {
                case None     => a
                case Some(gj) => (linked - gi) -> (collected ++ gj)
              }
          }
        if (links.size == linksReduced.size) linksReduced -> groupWider
        else go(linksReduced, groupWider)
      }

      go(linked, process)
    }

    def go(linked: Map[Int, Set[Int]], acc: List[Set[Int]]): List[Set[Int]] = linked match {
      case m if m.isEmpty => acc
      case m              =>
        val g0 = m.head match { case (x, xs) => xs + x }
        val (linked2, group) = extract(m.tail, g0)
        go(linked2, group :: acc)
    }

    go(linked, Nil)
  }

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

    val links = mkLinks(pairs)
    val chains = repack(links).map(_.size)
    val total = priceTotal(n, chains)
    println(total)
  }
}

class P5PrisonTransport extends ASuite {
  import P5PrisonTransport._

  val input =
    """
      |6 11
      |9 5
      |11 9
      |15 9
      |13 15
      |12 14
      |15 16
      |1 16
      |""".stripMargin
      .split("\n")
      .filter(_.nonEmpty)
      .map(_.split(" "))
      .map(_.map(_.toInt) match {
        case Array(a, b) => (a, b)
      })

  test("1") {
    val links = mkLinks(input)
    links.foreach { case (a, b) => println(s"$a -> $b") }

    val split = repack(links)

    split.foreach(x => pprint.log(x))
    split.toSet shouldBe Set(
      Set(12, 14),
      Set(5, 1, 6, 9, 13, 16, 11, 15)
    )
  }
}
