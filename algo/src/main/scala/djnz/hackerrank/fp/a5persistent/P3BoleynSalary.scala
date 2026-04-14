package djnz.hackerrank.fp.a5persistent

import scala.collection.immutable.TreeMap
import scala.io.StdIn.readLine

/** https://www.hackerrank.com/challenges/boleyn-salary/problem */
object P3BoleynSalary {

  def main(args: Array[String]): Unit = {
    val Array(n, q) = readLine().split(" ").map(_.trim.toInt)

    val hierarchy = (1 until n)
      .map(_ => readLine().split(" ").map(_.trim).map(_.toInt))
      .collect { case Array(a, b) => b -> a }
      .groupMap(_._1)(_._2)

    val sal = readLine().split(" ").map(_.trim.toInt)

    case class Node(idx: Int, count: Int, children: Seq[Node], sal2idx: TreeMap[Int, Int])

    val nodes = Array.ofDim[Node](n)

    def mkTree(id: Int): Node = {

      val children: Seq[Node] = hierarchy.get(id) match {
        case None    => Nil
        case Some(v) => v.map(mkTree)
      }

      val sorted = children.sortBy(_.count)(Ordering.Int.reverse)

      val sal2idx = if (sorted.isEmpty) TreeMap.empty[Int, Int]
      else {
        val h = sorted.head
        val m0 = h.sal2idx + (sal(h.idx - 1) -> h.idx)
        sorted
          .tail
          .foldLeft(m0) {
            (m, node) => m ++ node.sal2idx + (sal(node.idx - 1) -> node.idx)
          }
      }

      val x = Node(id, children.map(_.count).sum, children, sal2idx)

      nodes(id - 1) = x

      x
    }

    mkTree(1)

    val queries = (1 to q)
      .flatMap { _ =>
        readLine().split(" ").map(_.trim.toInt) match {
          case Array(a, b) => Some(a -> b)
          case _           => None
        }
      }

    queries
      .foldLeft(List.empty[Int]) { case (xs, (v, k)) =>
        val prev = xs.headOption.getOrElse(0)
        val id = prev + v
        val n = nodes(id - 1).sal2idx.iterator.drop(k - 1).next()._2
        n :: xs
      }
      .reverse
      .foreach(println)

  }

}
