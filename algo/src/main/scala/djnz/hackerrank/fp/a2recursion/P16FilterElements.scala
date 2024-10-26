package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/filter-elements/problem */
object P16FilterElements {

  implicit class StringToOps(s: String) {
    def splitToInts = s.split(" ").map(_.toInt)
  }

  type TI = (Int, Int)

  def solve1(tc: TestCase) =
    tc.data.groupBy(identity)
      .filter(t => t._2.length >= tc.n)
      .keys.toArray
      .map(x => (x, tc.data.indexOf(x)))
      .sorted((x: TI, y: TI) => x._2 - y._2)
      .map(_._1)

  def solveN(data: List[TestCase]) =
    data
      .map(solve1)
      .map(l => if (l.nonEmpty) l.mkString(" ") else "-1")

  case class TestCase(n: Int, data: Array[Int])

  def next() = scala.io.StdIn.readLine()
  def readOne = TestCase(
    next().splitToInts(1),
    next().splitToInts
  )
  def readAll(n: Int, acc: List[TestCase]): List[TestCase] = n match {
    case 0 => acc.reverse
    case _ => readAll(n - 1, readOne :: acc)
  }

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    val r = solveN(readAll(n, Nil))
    r.foreach(println)
  }

}
