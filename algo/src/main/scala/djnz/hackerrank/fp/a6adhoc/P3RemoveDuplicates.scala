package djnz.hackerrank.fp.a6adhoc

/** https://www.hackerrank.com/challenges/remove-duplicates/problem */
object P3RemoveDuplicates {

  def process(s: String) = {
    @scala.annotation.tailrec
    def go(tail: List[Char], acc: List[Char]): List[Char] = tail match {
      case Nil    => acc.reverse
      case h :: t => go(t, if (acc.contains(h)) acc else h :: acc)
    }
    go(s.toList, Nil).mkString
  }

  def body(readLine: => String): Unit = {
    val r = process(readLine)
    println(r)
  }

  def main(p: Array[String]): Unit =
    body(scala.io.StdIn.readLine)

}
