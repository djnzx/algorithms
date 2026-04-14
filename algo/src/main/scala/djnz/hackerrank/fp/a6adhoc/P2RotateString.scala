package djnz.hackerrank.fp.a6adhoc

/** https://www.hackerrank.com/challenges/rotate-string/problem */
object P2RotateString {

  def allPossible(orig: String): String = {
    @scala.annotation.tailrec
    def go(s: String, n: Int, acc: List[String]): List[String] =
      if (n==s.length) acc.reverse
      else { val s2 = s"${s.tail}${s.head}"; go(s2, n+1, s2::acc) }
    go(orig, 0, Nil) mkString " "
  }

  def process(data: List[String]) = {
    data map { allPossible } mkString "\n"
  }

  def body(readLine: => String): Unit = {
    val N: Int = readLine.toInt

    def readOneLine = readLine

    def readNLines(n: Int, acc: List[String]): List[String] = n match {
      case 0 => acc.reverse
      case _ => readNLines(n-1, readOneLine::acc)
    }

    val r = process(readNLines(N, Nil))
    println(r)
  }

  def main(p: Array[String]): Unit = {
    body { scala.io.StdIn.readLine }
//    main_file(p)
  }

  val fname = "src/main/scala/hackerrankfp/d200426_05/allpossiblerotations.txt"
  def main_file(p: Array[String]): Unit = {
    scala.util.Using(
      scala.io.Source.fromFile(new java.io.File(fname))
    ) { src =>
      val it = src.getLines().map(_.trim)
      body { it.next() }
    }
  }


}
