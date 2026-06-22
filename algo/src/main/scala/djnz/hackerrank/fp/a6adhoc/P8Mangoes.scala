package djnz.hackerrank.fp.a6adhoc

/** https://www.hackerrank.com/challenges/mango/problem */
object P8Mangoes {

  import scala.io.StdIn.readLine

  def solve(n: Long, m: BigInt, as: Seq[Int], hs: Seq[Int]): Long = {
    val ah = as zip hs

    def feed(k: Int): Boolean =
      ah.map { case (ai, hi) => ai + BigInt(k - 1) * hi }.sorted.take(k).sum <= m

    def tryMore(l: Long, h: Long): Long =
      if (l > h) h
      else {
        val m = (l + h) >> 1
        if (feed(m.toInt)) tryMore(m + 1, h)
        else tryMore(l, m - 1)
      }

    tryMore(1, n)
  }

  def main(args: Array[String]): Unit = {
    val Array(n, m) = readLine.split(' ').map(_.trim.toLong)
    val as = readLine.split(' ').map(_.trim.toInt)
    val hs = readLine.split(' ').map(_.trim.toInt)
    val solution = solve(n, BigInt(m), as, hs)
    println(solution)
  }

}
