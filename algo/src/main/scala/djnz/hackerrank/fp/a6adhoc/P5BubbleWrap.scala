package djnz.hackerrank.fp.a6adhoc

import scala.io.StdIn.readLine

/** https://www.hackerrank.com/challenges/kundu-and-bubble-wrap/problem */
object P5BubbleWrap {

  // https://en.wikipedia.org/wiki/Expected_value
  def me(n: Int): Double = (1 to n).map(x => 1.0 / x).sum * n

  def main(args: Array[String]): Unit = {
    val n = readLine().split(" ").flatMap(_.trim.toIntOption).product
    println(me(n))
  }

}
