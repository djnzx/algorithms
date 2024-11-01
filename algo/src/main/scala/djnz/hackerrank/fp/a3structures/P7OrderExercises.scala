package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/order-exercises/problem */
object P7OrderExercises {
  def next() = scala.io.StdIn.readLine()

  def normalize(xs: Seq[Int]) = {
    val half = Int.MaxValue / 2
    val a0 = List(-half, half)
    xs.foldLeft(a0) { (a, xi) =>
      a match {
        case x :: xs if (x < 0) == (xi < 0) => (x + xi) :: xs
        case _                              => xi :: a
      }
    }
      .dropWhile(_ < 0)
  }

  def solve(seq: List[Int]): List[Int] = {
    import scala.collection.immutable.Queue
    case class State(sum: Int, queue: Queue[Int], outcome: List[Int])

    @scala.annotation.tailrec
    def go(zz: List[Int], s: State): List[Int] = zz match {
      case Nil                            => s.outcome
      case x :: xs if s.queue.isEmpty     => go(xs, State(x, Queue(x), s.outcome))
      case a :: b :: xs if s.sum + a <= 0 =>
        s.queue.dequeue match {
          case (x, q2) if q2.isEmpty => go(xs, State(b, Queue(b), x :: s.outcome))
          case (x, _) if s.sum >= x  => go(xs, State(b, Queue(b), s.sum :: s.outcome))
          case (x, q2)               => go(q2.drop(1).toList ++ zz, State(0, Queue(), x :: s.outcome))
        }
      case a :: b :: xs                   =>
        s.sum + a + b match {
          case aab if aab >= s.queue.head => go(xs, State(aab, Queue(aab), s.outcome))
          case aab                        => go(xs, State(aab, s.queue.enqueue(a).enqueue(b), s.outcome))
        }
      case _                              => ???
    }

    val s0 = State(0, Queue.empty, List.empty)
    go(seq, s0)
  }

  def main(args: Array[String]): Unit = {
    val k = next().split(" ").map(_.toInt).drop(1).head
    val as = next().split(" ").map(_.toInt)
    val xs = normalize(as)
    solve(xs)
      .sorted(Ordering.Int.reverse)
      .take(k)
      .foreach(println)
  }
}
