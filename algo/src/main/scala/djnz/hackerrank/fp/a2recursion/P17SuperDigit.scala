package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/super-digit/problem */
object P17SuperDigit {

  def sum(s: String) = s.foldLeft(0) { case (a, c) => a + (c - '0') }

  def superDigit(n: String): Int = n.length match {
    case 1 => n.toInt
    case _ => superDigit(sum(n).toString)
  }

  def solve(n: String, k: Int) = {
    val sn = superDigit(n)
    val snk = sn * k
    superDigit(snk.toString)
  }

  def next() = scala.io.StdIn.readLine()

  def main(p: Array[String]): Unit = {
    val (n, k) = next().split(" ") match {
      case Array(n, k) => n -> k.toInt
    }
    val r = solve(n, k)
    println(r)
  }

}
