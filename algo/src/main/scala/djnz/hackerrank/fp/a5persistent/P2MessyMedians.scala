package djnz.hackerrank.fp.a5persistent

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import scala.io.StdIn.readLine

/** https://www.hackerrank.com/challenges/messy-medians/problem */
object P2MessyMedians {

  sealed abstract class Tree(val count: Int)
  case object Empty extends Tree(0)
  case class Node(l: Tree, r: Tree, override val count: Int) extends Tree(count) {
    def withL(t: Tree) = copy(t, r, count + 1)
    def withR(t: Tree) = copy(l, t, count + 1)
  }
  object Node {
    def l(t: Tree) = Node(t, Empty, 1)
    def r(t: Tree) = Node(Empty, t, 1)
    def n(n: Int) = Node(Empty, Empty, n)
  }

  // add element to the tree (by index)
  def add(tree: Tree, lo: Int, hi: Int, idx: Int): Tree =
    if (lo == hi) Node.n(tree.count + 1)
    else {
      val mid = (lo + hi) >> 1
      tree match {
        case Empty if idx <= mid             => Node.l(add(Empty, lo, mid, idx))
        case Empty                           => Node.r(add(Empty, mid + 1, hi, idx))
        case n @ Node(l, _, _) if idx <= mid => n.withL(add(l, lo, mid, idx))
        case n @ Node(_, r, _)               => n.withR(add(r, mid + 1, hi, idx))
      }
    }

  // get the index
  def kth(tree: Tree, lo: Int, hi: Int, k: Int): Int =
    if (lo == hi) lo
    else {
      val mid = (lo + hi) >> 1

      val lCount = tree match {
        case Node(l, _, _) => l.count
        case Empty         => 0
      }

      tree match {
        case Node(l, _, _) if k <= lCount => kth(l, lo, mid, k)
        case Node(_, r, _)                => kth(r, mid + 1, hi, k - lCount)
        case Empty                        => sys.error("we don't query empty tree")
      }
    }

  case class History(private val items: Vector[Tree]) {
    val last = items.last
    def append(t: Tree) = History(items :+ t)
    def lookup(index: Int) = items(index)
  }
  object History {
    def init = History(Vector(Empty))
  }

  def perform(input: InputData)(state: History, q: Int, qIdx: Int): (History, Int) = {
    val tree2 = q match {
      case q if q > 0 => add(state.last, input.min, input.max, input.indexes(q))
      case q if q < 0 => state.lookup(qIdx + 1 + q)
      case _          => sys.error("0 is not allowed as input")
    }

    val idx = kth(tree2, input.min, input.max, (tree2.count + 1) / 2)
    val median = input.all(idx)
    val state2 = state.append(tree2)

    (state2, median)
  }

  case class InputData(all: Vector[Int], indexes: Map[Int, Int]) {
    val min = 0
    val max = all.size - 1
  }

  def pack(queries: Vector[Int]): InputData = {
    val all = queries
      .filter(_ > 0) // add
      .distinct
      .sorted
    InputData(all, all.zipWithIndex.toMap)
  }

  def main(args: Array[String]): Unit = {
    val n = readLine().toInt
    val qs = Vector.fill(n)(readLine().toInt)

    val input = pack(qs)

    val (_, medians) =
      qs.zipWithIndex
        .foldLeft((History.init, List.empty[Int])) {
          case ((state, medians), (q, idx)) =>
            val (state2, median) = perform(input)(state, q, idx)
            (state2, median :: medians)
        }

    medians
      .reverse
      .foreach(println)
  }
}

class P2MessyMediansTest extends AnyFunSuite with Matchers {

  import P2MessyMedians._

}
