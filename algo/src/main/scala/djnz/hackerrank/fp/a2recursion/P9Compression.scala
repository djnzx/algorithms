package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/string-compression/problem */
object P9Compression {

  def solve(s: String): String = {
    type Item = (Char, Int)
    def go(tail: List[Char], curr: Option[Item], acc: List[Item]): List[Item] = (curr, tail) match {
      case (None, Nil)                        => Nil                              // nothing to collect
      case (None, h :: t)                     => go(t, Some(h -> 1), acc)         // start collecting
      case (Some(itm), Nil)                   => (itm :: acc).reverse             // done collecting
      case (Some((c, cnt)), h :: t) if h == c => go(t, Some(c -> (cnt + 1)), acc) // same char
      case (Some(itm), h :: t)                => go(t, Some(h -> 1), itm :: acc)  // new char
    }

    go(s.toList, None, Nil)
      .foldLeft("") {
        case (acc, (a, 1)) => s"$acc$a"
        case (acc, (a, n)) => s"$acc$a$n"
      }
  }

  def main(args: Array[String]): Unit = {
    val s = scala.io.StdIn.readLine()
    val r = solve(s)
    println(r)
  }

}
