package djnz.hackerrank.fp.a3structures

import djnz.hackerrank.fp.a3structures.P5Slow.next
import djnz.hackerrank.fp.a3structures.P5Slow.price
import djnz.hackerrank.fp.a3structures.P5Slow.solve

/** https://www.hackerrank.com/challenges/prison-transport/problem doesn't pass N9 Disjoint Set Union
  * https://cp-algorithms.com/data_structures/disjoint_set_union.html needs to be applied
  */
object P5Mutable2020 {
  def priceOneGroup(n: Int): Int = math.ceil(math.sqrt(n)).toInt
  def priceAllGroups(xs: Iterable[Int]): Int = xs.toVector.map(priceOneGroup).sum
  def priceTotal(N: Int, xs: Iterable[Int]): Int = N - xs.sum + priceAllGroups(xs)

  import scala.collection.mutable.ArrayBuffer
  case class Counter(N: Int) {
    private val group = ArrayBuffer.fill[Int](N + 1)(0)           // id    -> group mapping
    private val count = ArrayBuffer.fill[Int](N + 1)(0)           // group -> count
    private val members = ArrayBuffer.fill[List[Int]](N + 1)(Nil) // group -> members

    def groupsGt0 = count.filter(_ > 0)
    def print = {
      println(s"group: ${group zipWithIndex}")
      println(s"count: ${count zipWithIndex}")
      println(s"mmbrs: ${members zipWithIndex}")
    }
    def process(a: Int, b: Int): Unit = (group(a), group(b)) match {
      case (0, 0)               =>
        val gid = a
        group(a) = gid
        group(b) = gid
        count(gid) = 2
        members(gid) = List(a, b)
      case (ga, 0)              =>
        group(b) = ga
        count(ga) += 1
        members(ga) = b :: members(ga)
      case (0, gb)              =>
        group(a) = gb
        count(gb) += 1
        members(gb) = a :: members(gb)
      case (ga, gb) if ga != gb =>
        count(ga) += count(gb)
        count(gb) = 0
        val setb = members(gb)
        setb.foreach(group(_) = ga)
        members(ga) = members(ga) ::: setb
        members(gb) = Nil
      case _                    =>
    }
  }

  def process(n: Int, pairs: Seq[(Int, Int)]) = {
    val c = Counter(n)
    pairs.foreach { case (a, b) => c.process(a, b) }
    priceTotal(n, c.groupsGt0)
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
    val gs = solve(pairs)
    val x = price(n, gs)
    println(x)
  }
}
