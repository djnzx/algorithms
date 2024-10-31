package djnz.hackerrank.fp.a3structures

import djnz.tools.ASuite
import org.scalatest.ConfigMap
import scala.collection.immutable.AbstractMap
import scala.collection.immutable.SeqMap
import scala.collection.immutable.SortedMap

/** https://www.hackerrank.com/challenges/prison-transport/problem
  * https://cp-algorithms.com/data_structures/disjoint_set_union.html
  */
object P5PrisonTransport {

  def next() = scala.io.StdIn.readLine()

  def price1g(n: Int): Int = math.ceil(math.sqrt(n)).toInt
  def priceNg(xs: Iterable[Int]): Int = xs.foldLeft(0)((a, x) => a + price1g(x))
  def price(n: Int, xs: Iterable[Int]): Int = n - xs.sum + priceNg(xs)

  case class Tree(id: Int, links: Set[Int])

  def mkLinks0(xs: Seq[(Int, Int)]) =
    (xs ++ xs.map { case (x, y) => (y, x) })
      .groupMapReduce(_._1)(x => Set(x._2))(_ ++ _)
//      .map { case (k, ls) => k -> Tree(k, ls.toSet) }

  def mkLinks(xs: Seq[(Int, Int)]) =
    (xs ++ xs.map { case (x, y) => (y, x) })
      .groupMap(_._1)(_._2)
      .map { case (k, ls) => k -> Tree(k, ls.toSet) }

  def split(links: Map[Int, Tree]): List[Int] = {

    def without(links: Map[Int, Tree], p: Tree): Map[Int, Tree] = {
      val l0 = links - p.id
      p.links.foldLeft(l0) { (links, id) =>
        links.updated(id, Tree(id, links(id).links - p.id))
      }
    }

    case class State(links: Map[Int, Tree], len: Int)

    def extractChain(links: Map[Int, Tree], p: Tree): State =
      p.links
        .foldLeft(State(without(links, p), 1)) {
          case (acc, id) if acc.links.contains(id) =>
            val next = extractChain(acc.links, acc.links(id))
            State(next.links, acc.len + next.len)
          case (acc, _)                            => acc
        }

    @scala.annotation.tailrec
    def go(links: Map[Int, Tree], lengths: List[Int]): List[Int] = links match {
      case m if m.isEmpty => lengths
      case _              =>
        val p = links.values.head
        val next = extractChain(links, p)
        go(next.links, next.len :: lengths)
    }

    go(links, Nil)
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

    val links: Map[Int, Tree] = mkLinks(pairs)
    val chains: List[Int] = split(links)
    val outcome: Int = price(n, chains)
    println(outcome)
  }
}

class P5PrisonTransport extends ASuite {
  import P5PrisonTransport._
  val xs =
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

  /*

  1 5 6 9 11 13 15 16
  12 14
  1 16

   5  -> Tree(5,Set(9))
   6  -> Tree(6,Set(11))
   9  -> Tree(9,Set(5, 11, 15))
   11 -> Tree(11,Set(9, 6))
   13 -> Tree(13,Set(15))
   15 -> Tree(15,Set(9, 16, 13))
   16 -> Tree(16,Set(15, 1))
   1  -> Tree(1,Set(16))

   14 -> Tree(14,Set(12))
   12 -> Tree(12,Set(14))

   */

  test("1") {

    def repack(linked: Map[Int, Set[Int]]): List[Set[Int]] = {

      def extract(linked: Map[Int, Set[Int]], process: Set[Int]): (Map[Int, Set[Int]], Set[Int]) = {

        def go(linked: Map[Int, Set[Int]], group: Set[Int]): (Map[Int, Set[Int]], Set[Int]) = {
          val (linked2, groupWide) = group.foldLeft(linked -> Set.empty[Int]) { case (a @ (linked, collected), n) =>
            linked.get(n) match {
              case Some(gg) => (linked - n) -> (collected ++ gg)
              case None     => a
            }
          }
          linked2 -> groupWide
        }

        go(linked, process)
      }

      def go(linked: Map[Int, Set[Int]], acc: List[Set[Int]]): List[Set[Int]] = linked match {
        case m if m.isEmpty => acc
        case m              =>
          val (map2, group) = extract(m.tail, m.head match { case (a, b) => b + a })
          go(map2, group :: acc)
      }

      go(linked.tail, Nil)
    }

    val x: Map[Int, Set[Int]] = mkLinks0(xs)
    x.foreach { case (a, b) => println(s"$a -> $b") }
    repack(x)
      .foreach(println)
  }
}
