package djnz.hackerrank.fp.a6adhoc

/** https://www.hackerrank.com/challenges/jumping-bunnies/problem */
object P1JumpingBunnies {
  import scala.io.StdIn.readLine

  def gcd(a: Long, b: Long): Long = if (b == 0) a else gcd(b, a % b)

  def lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

  def lcmList(xs: Seq[Long]): Long = xs.reduce(lcm)

  def main(args: Array[String]): Unit = {
    val _ = readLine()
    val xs = readLine().split(" ").flatMap(_.trim.toLongOption)
    val x = lcmList(xs)
    println(x)
  }

}
