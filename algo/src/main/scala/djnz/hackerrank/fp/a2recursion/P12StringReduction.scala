package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/string-reductions/problem */
object P12StringReduction {

  def solve(s: String) = {
    @scala.annotation.tailrec
    def go(tail: List[Char], acc: List[Char]): List[Char] = tail match {
      case Nil                       => acc.reverse
      case h :: t if acc.contains(h) => go(t, acc)
      case h :: t                    => go(t, h :: acc)
    }
    go(s.toList, Nil).mkString
  }

  def next() = scala.io.StdIn.readLine()

  def main(p: Array[String]): Unit = {
    val s = next()
    val r = solve(s)
    println(r)
  }

}
