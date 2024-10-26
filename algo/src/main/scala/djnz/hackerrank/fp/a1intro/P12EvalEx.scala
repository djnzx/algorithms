package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/eval-ex/problem */
object P12EvalEx {

  def next() = scala.io.StdIn.readLine().trim

  def fact(n: Int): Long = (1 to n).foldLeft(1: Long)((acc, x) => acc * x)
  def pow(x: Double, n: Int): Double = (1 to n).foldLeft(1.0)((acc, _) => acc * x)
  def ex(x: Double): Double = 1 + (1 to 9).foldLeft(0.0)((acc, n) => acc + pow(x, n) / fact(n))

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    (1 to n)
      .foreach { _ =>
        val x = next().toDouble
        val r = ex(x)
        println("%.4f".format(r))
      }
  }

}
