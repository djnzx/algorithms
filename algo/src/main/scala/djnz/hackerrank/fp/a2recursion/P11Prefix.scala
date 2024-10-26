package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/prefix-compression/problem */
object P11Prefix {

  /** classic recursion */
  def findDiff1(s1: String, s2: String): Int = {
    def go(idx: Int): Int =
      if (idx < s1.length && idx < s2.length && s1(idx) == s2(idx)) go(idx + 1)
      else idx
    go(0)
  }

  /** stdlib, declarative, also lazy */
  def findDiff(s1: String, s2: String): Int =
    s1.zip(s2).zipWithIndex
      .find { case ((c1, c2), _) => c1 != c2 }
      .fold(s1.length min s2.length) { case (_, idx) => idx }

  def show(idx: Int, s1: String, s2: String) = List(
    s"$idx ${s1.substring(0, idx)}",
    s"${s1.length - idx} ${s1.substring(idx)}",
    s"${s2.length - idx} ${s2.substring(idx)}",
  ).mkString("\n")

  def next() = scala.io.StdIn.readLine()

  def main(p: Array[String]): Unit = {
    val s1 = next()
    val s2 = next()
    val r = findDiff(s1, s2)
    val s = show(r, s1, s2)
    println(s)
  }
}
