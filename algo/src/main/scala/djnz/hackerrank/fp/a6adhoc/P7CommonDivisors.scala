package djnz.hackerrank.fp.a6adhoc

/** https://www.hackerrank.com/challenges/common-divisors/problem */
object P7CommonDivisors {

  implicit class StringToOps(s: String) {
    def splitToInt: Array[Int] = s.split(" ").map(_.toInt)
    def toVectorInt: Vector[Int] = splitToInt.toVector
    def toListInt: List[Int] = splitToInt.toList
    def toTuple2Int: (Int, Int) = { val a = splitToInt; (a(0), a(1)) }
  }

  @scala.annotation.tailrec
  def gcd(a: Int, b: Int): Int = {
    val r: Int = a % b
    if (r == 0) b else gcd(b, r)
  }

  def allDivisors(n: Int): Set[Int] =
    (1 to n / 2).foldLeft(Set(n))((set, x) => if (n % x == 0) set + x else set)

  def process1(data: Vector[Int]): Int = {
    val a = data(0)
    val b = data(1)
    if (a == 1 || b == 1) 1 else allDivisors(gcd(a, b)).size
  }

  def process(data: List[Vector[Int]]) =
    data map process1 mkString "\n"

  def body(readLine: => String): Unit = {
    val N: Int = readLine.toInt

    def readOneLine = readLine.toVectorInt.sorted
    // N lines by readline
    def readNLines(n: Int, acc: List[Vector[Int]]): List[Vector[Int]] = n match {
      case 0 => acc.reverse
      case _ => readNLines(n - 1, readOneLine :: acc)
    }

    val r = process(readNLines(N, Nil))
    println(r)
  }

  def main(p: Array[String]): Unit =
    //  body { scala.io.StdIn.readLine }
    main_file(p)

  val fname = "src/main/scala/hackerrankfp/d200426_05/commond.txt"
  def main_file(p: Array[String]): Unit =
    scala.util.Using(
      scala.io.Source.fromFile(new java.io.File(fname))
    ) { src =>
      val it = src.getLines().map(_.trim)
      body(it.next())
    }

}
