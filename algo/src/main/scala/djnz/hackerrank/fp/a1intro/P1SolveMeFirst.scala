package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/fp-solve-me-first/problem */
object P1SolveMeFirst {

  def main(args: Array[String]): Unit = {
    val xs = scala.io.Source.stdin.getLines().take(2)
    val sum = xs.map(_.toInt).sum
    println(sum)
  }

}
