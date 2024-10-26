package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/string-o-permute/problem */
object P6StringOPermute {

  /** classic recursion, but stack */
  def permute2(s: String): String = {

    def go(s: List[Char]): List[Char] = s match {
      case a :: b :: cs => b :: a :: go(cs)
      case _            => Nil
    }

    go(s.toList).mkString
  }

  /** tail recursion, no stack consumption */
  def permute(s: String): String = {
    val half = s.length / 2

    def go(i: Int, acc: List[Char]): List[Char] = i match {
      case `half` => acc
      case _      => go(i + 1, s(i * 2) :: s(i * 2 + 1) :: acc)
    }

    go(0, Nil).reverse.mkString
  }

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    (1 to n)
      .map(_ => next())
      .map(permute)
      .foreach(println)
  }

}
