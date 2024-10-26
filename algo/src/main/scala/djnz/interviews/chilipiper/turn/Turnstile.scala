package djnz.interviews.chilipiper.turn

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.util.chaining.scalaUtilChainingOps

object Turnstile extends App {

  sealed trait Dir
  case object Enter extends Dir
  case object Exit extends Dir
  object Dir {
    def parse(n: Int) = n match {
      case 0 => Enter
      case 1 => Exit
      case _ => sys.error(s"unexpected value given $n")
    }
  }

  def getTimes(times: Array[Int], dirs: Array[Int]) = {

    case class State(time: Int, dir: Option[Dir]) {
      def time(t: Int) = copy(time = t)
      lazy val time1 = time + 1
    }
    object State {
      def apply(t: Int, d: Dir) = new State(t, Some(d))
    }

    /** (Int, Int) means (time, initial position in the array) */
    def go(xs: List[((Int, Dir), Int)], state: State, acc: List[(Int, Int)]): List[(Int, Int)] = xs match {
      /** 1. list is empty - done */
      case Nil => acc

      /** 2. 1st item in the state */
      case ((t, d), i) :: tail if state.dir.contains(d) && state.time >= t - 1 =>
        val tNext = state.time1
        go(tail, state.time(tNext), tNext -> i :: acc)

      /** 3. 2nd item in the state */
      case x :: ((t, d), i) :: tail if state.dir.contains(d) && state.time >= t - 1 =>
        val tNext = state.time1
        go(x :: tail, state.time(tNext), tNext -> i :: acc)

      /** 4. 1st time = 2nd time, don't need to use state, but need to pick priority, one of them is Exit */
      case (a @ ((t1, d1), idx1)) :: (b @ ((t2, d2), idx2)) :: tail if t1 == t2 => (d1, d2) match {
        case (Exit, _) => go(b :: tail, State(t1, Exit), t1 -> idx1 :: acc)
        case (_, Exit) => go(a :: tail, State(t2, Exit), t2 -> idx2 :: acc)
        case _         => go(b :: tail, State(t1, d1), t1 -> idx1 :: acc)
      }

      /** 5. 1+ item */
      case ((t, d), idx) :: tail =>
        val tNext = state.time1 max t
        go(tail, State(tNext, d), tNext -> idx :: acc)

    }

    (times zip dirs.map(Dir.parse) zipWithIndex).toList
      .pipe(go(_, State(Int.MinValue, None), Nil))
      .toArray
      .sortBy(_._2)
      .map(_._1)
  }
}

class TurnstileSpec extends AnyFunSpec with Matchers {

  describe("a") {
    import Turnstile._
    it("1") {
      getTimes(
        Array(0, 0, 1, 5),
        Array(0, 1, 1, 0),
      ) shouldEqual Array(2, 0, 1, 5)
    }

    it("2") {
      getTimes(
        Array(0, 1, 1, 3, 3),
        Array(0, 1, 0, 0, 1),
      ) shouldEqual Array(0, 2, 1, 4, 3)
    }

    it("3") {
      getTimes(
        Array(3, 3, 3, 4, 4, 5, 6, 6, 7, 8),
        Array(1, 1, 0, 1, 0, 0, 0, 1, 0, 0),
      ) shouldEqual Array(3, 4, 7, 5, 8, 9, 10, 6, 11, 12)
      //                  3, 4, 6, 5, 7, 8, 9, 12, 10, 11
    }
  }
}
