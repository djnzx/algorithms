package djnz.hackerrank.fp.a6adhoc

object P6MissingNumbersV2 {
  implicit class StringToOps(s: String) {
    def splitToInt: Array[Int] = s.split(" ").map(_.toInt)
    def toVectorInt: Vector[Int] = splitToInt.toVector
    def toListInt: List[Int] = splitToInt.toList
    def toTuple2Int: (Int, Int) = { val a = splitToInt; (a(0), a(1)) }
  }

  def toMap(a: Array[Int]) =
    a.groupBy(identity).map(t => t match { case (n, a) => (n, a.length) })

  def process(a1: Array[Int], a2: Array[Int]): List[Int] = {
    val m1: Map[Int, Int] = toMap(a1)
    val m2: Map[Int, Int] = toMap(a2)

    m2.flatMap(t => t match { case (n, c) =>
        val delta = c-m1(n)
        if (delta > 0) Some(n) else None
    }).toVector.sorted.toList
  }

  def body(readLine: => String): Unit = {
    val _ = readLine
    val a1 = readLine.splitToInt
    val _ = readLine
    val a2 = readLine.splitToInt

    val r = process(a1, a2)
    println(r.mkString(" "))
  }

  def main(p: Array[String]): Unit = {
    //  body { scala.io.StdIn.readLine }
    main_file(p)
  }

  val fname = "src/main/scala/hackerrankfp/d200425_04/missing.txt"
  def main_file(p: Array[String]): Unit = {
    scala.util.Using(
      scala.io.Source.fromFile(new java.io.File(fname))
    ) { src =>
      val it = src.getLines().map(_.trim)
      body { it.next() }
    }
  }

}
