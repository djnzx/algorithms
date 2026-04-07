package djnz.hackerrank.fp.a5persistent

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import scala.io.StdIn.readLine

/** https://www.hackerrank.com/challenges/messy-medians/problem */
object P2MessyMedians {

  sealed abstract class Tree(val count: Int)
  case object Empty extends Tree(0)
  case class Node(l: Tree, r: Tree, override val count: Int) extends Tree(count)

  def update(tree: Tree, lo: Int, hi: Int, pos: Int): Tree =
    if (lo == hi) Node(Empty, Empty, tree.count + 1)
    else {
      val mid = (lo + hi) >> 1
      tree match {
        case Empty         =>
          if (pos <= mid) Node(update(Empty, lo, mid, pos), Empty, 1)
          else Node(Empty, update(Empty, mid + 1, hi, pos), 1)
        case Node(l, r, c) =>
          if (pos <= mid) Node(update(l, lo, mid, pos), r, c + 1)
          else Node(l, update(r, mid + 1, hi, pos), c + 1)
      }
    }

  def kth(tree: Tree, lo: Int, hi: Int, k: Int): Int =
    if (lo == hi) lo
    else {
      val mid = (lo + hi) >> 1

      val leftCount = tree match {
        case Node(l, _, _) => l.count
        case Empty         => 0
      }

      tree match {
        case Node(left, right, _) =>
          if (k <= leftCount) kth(left, lo, mid, k)
          else kth(right, mid + 1, hi, k - leftCount)
        case Empty                => ???
      }
    }

  case class State(history: Vector[Tree], coords: Vector[Int], rankOf: Map[Int, Int]) {
    def m: Int = coords.size - 1 // last coord elem
  }

  def perform(state: State, q: Int, qIdx: Int): (State, Int) = {
    val tree2 = q match {
      case q if q > 0 => update(state.history.last, 0, state.m, state.rankOf(q))
      case q          => state.history(qIdx + 1 + q)
    }

    val idx = kth(tree2, 0, state.m, (tree2.count + 1) / 2)
    val median = state.coords(idx)
    val state2 = state.copy(history = state.history :+ tree2)

    (state2, median)
  }

  def pack(queries: Vector[Int]): (Vector[Int], Map[Int, Int]) = {
    // all distinc trees
    val all = queries
      .filter(_ > 0) // add
      .distinct
      .sorted
    (all, all.zipWithIndex.toMap)
  }

  def main(args: Array[String]): Unit = {
    val n = readLine().toInt
    val qs = Vector.fill(n)(readLine().toInt)

    val (distinct, distinctIndexed) = pack(qs)
    val state0 = State(Vector(Empty), distinct, distinctIndexed)

    val (_, medians) =
      qs.zipWithIndex
        .foldLeft((state0, List.empty[Int])) {
          case ((state, medians), (q, idx)) =>
            val (state2, median) = perform(state, q, idx)
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
