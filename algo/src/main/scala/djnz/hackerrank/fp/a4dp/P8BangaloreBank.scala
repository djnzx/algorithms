package djnz.hackerrank.fp.a4dp

import scala.collection.mutable

/** https://www.hackerrank.com/challenges/bangalore-bank/problem */
object P8BangaloreBank {

  def next() = scala.io.StdIn.readLine()

  private val memo = mutable.Map[(Int, Int, Int), Int]()

  def solve(digits: Seq[Int]): Int = {

    /** time to press: 1.move and 2.press */
    def time(current: Int, digit: Int): Int = {
      def adj(v: Int) = if (v == 0) 10 else v
      math.abs(adj(current) - adj(digit)) + 1
    }

    /** this is easy but not functional and immutable
      * TODO: consider creating table like we do in Levinstein
      */
    def go(lf: Int, rf: Int, idx: Int, acc: Int): Int = {
      val k = (lf, rf, idx)
      memo.get(k) match {
        case Some(x)                   => x + acc
        case _ if idx >= digits.length =>
          memo.put(k, 0)
          0 + acc
        case _                         =>
          val digit = digits(idx)
          val x = math.min(
            go(digit, rf, idx + 1, time(lf, digit)),
            go(lf, digit, idx + 1, time(rf, digit))
          )
          memo.put(k, x)
          x + acc
      }
    }

    (0 to 9)
      .map(d => go(digits.head, d, 0, 0))
      .min
  }

  def main(args: Array[String]): Unit = {
    val _ = next()
    val account = next().split(" ").map(_.toInt)
    val r = solve(account)
    println(r)
  }

}
