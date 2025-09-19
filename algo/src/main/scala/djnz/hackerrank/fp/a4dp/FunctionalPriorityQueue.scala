package djnz.hackerrank.fp.a4dp

import org.scalatest.funsuite.AnyFunSuite
import scala.annotation.tailrec
import scala.collection.immutable.TreeSet

class FunctionalPriorityQueue extends AnyFunSuite {

  @tailrec
  final def traverse[S, A: Ordering](xs: TreeSet[A], state: S, f: (S, A) => (S, IterableOnce[A])): S =
    xs.headOption match {
      case None    => state
      case Some(a) =>
        val (s2, aa) = f(state, a)
        traverse(xs.tail ++ aa.iterator, s2, f)
    }

}
