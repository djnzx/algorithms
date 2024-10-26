package djnz.hackerrank.fp.a2recursion

object P14SumOfPowersCountOnly {

  /** dynamic programming, only ways */
  def numberOfWays(x: Int, n: Int): Int = {
    val max = math.floor(math.pow(x, 1.0 / n)).toInt
    val powers = (1 to max).map(c => math.pow(c, n).toInt).toList
    def count(s: Int, candidates: List[Int]): Int = candidates match {
      case Nil     => 0
      case c :: cs =>
        if (c == s) 1
        else if (c > s) count(s, cs)
        else count(s - c, cs) + count(s, cs)
    }

    count(x, powers)
  }

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val x = next().toInt
    val n = next().toInt
    val r = numberOfWays(x, n)
    println(r)
  }

}
